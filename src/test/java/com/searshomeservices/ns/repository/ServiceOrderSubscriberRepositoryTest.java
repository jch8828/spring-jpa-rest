package com.searshomeservices.ns.repository;

import com.searshomeservices.ns.TestBase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by jchen9 on 11/23/16.
 */
@AutoConfigureMockMvc
public class ServiceOrderSubscriberRepositoryTest extends TestBase {
    private String entityName = "orderSubscriber";

    @Value("${spring.data.rest.base-path}")
    private String basePath;

    private String baseUrl;

    private String orderLocation;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceOrderSubscriberRepository tested;

    @Before
    public void setUp() throws Exception {
        baseUrl = basePath + "/" + entityName;
        String payload = "{\"canceled\": true,\n" +
                "                \"closed\": false,\n" +
                "                \"created\": \"2016-11-18T19:39:16.427+0000\",\n" +
                "                \"id\": {\n" +
                "                    \"serviceOrderNumber\": \"testSon1234\",\n" +
                "                    \"serviceUnitNumber\": \"testSun1234\"\n" +
                "                },\n" +
                "                \"modified\": \"2016-11-18T19:39:16.427+0000\",\n" +
                "                \"serviceEta\": \"10:42\",\n" +
                "                \"techArrived\": false,\n" +
                "                \"techEnRoute\": true }";
        MvcResult mvcResult = mockMvc.perform(post(basePath + "/order").content(payload))
                .andExpect(status().isCreated()).andReturn();
        orderLocation = mvcResult.getResponse().getHeader("Location");
    }

    @Test
    public void shouldReturnRepositoryIndex() throws Exception {
        mockMvc.perform(get(basePath)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$._links." + entityName).exists());
    }

    @Test
    public void testCrud() throws Exception {
        //create
        String payload = "{\n" +
                "                \"confirmationNumber\": \"4508f678-4f7d-41bc-8fb1-47a82496ed2b\",\n" +
                "                \"id\": \"testId1234\",\n" +
                "                \"label\": \"\",\n" +
                "                \"notificationId\": \"+12062901009\",\n" +
                "                \"notificationType\": \"SMS\",\n" +
                "                \"created\": \"2016-11-18T19:39:16.427+0000\",\n" +
                "                \"modified\": \"2016-11-18T19:39:16.427+0000\",\n" +
                "                \"serviceOrderNumber\": \"testSon1234\",\n" +
                "                \"serviceUnitNumber\": \"testSun1234\"\n" +
                "            }";
        MvcResult mvcResult = mockMvc.perform(post(baseUrl).content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString(entityName + "/"))).andReturn();

        //retrieve
        String location = mvcResult.getResponse().getHeader("Location");
        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("testId1234"))
                .andExpect(jsonPath("$.label").value(""))
                .andExpect(jsonPath("$.notificationType").value("SMS"))
                .andExpect(jsonPath("$.serviceOrderNumber").value("testSon1234"));

        //update
        String payloadNew = "{\n" +
                "                \"confirmationNumber\": \"testConfirmationNumber\",\n" +
                "                \"id\": \"testId1234\",\n" +
                "                \"label\": \"testLabel\",\n" +
                "                \"notificationId\": \"+12062901009\",\n" +
                "                \"notificationType\": \"SMS\",\n" +
                "                \"created\": \"2016-11-18T19:39:16.427+0000\",\n" +
                "                \"modified\": \"2016-11-18T19:39:16.427+0000\",\n" +
                "                \"serviceOrderNumber\": \"testSon1234\",\n" +
                "                \"serviceUnitNumber\": \"testSun1234\"\n" +
                "            }";
        mockMvc.perform(put(location).content(payloadNew))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.confirmationNumber").value("testConfirmationNumber"))
                .andExpect(jsonPath("$.label").value("testLabel"))
                .andExpect(jsonPath("$.serviceOrderNumber").value("testSon1234"));

        //partial update
        mockMvc.perform(patch(location).content("{\"label\": \"newLabel\"}"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.label").value("newLabel"))
                .andExpect(jsonPath("$.serviceUnitNumber").value("testSun1234"));

        //delete
        mockMvc.perform(delete(location)).andExpect(status().isNoContent());
        mockMvc.perform(get(location)).andExpect(status().isNotFound());
    }

    @Test
    public void findByNotificationIdIn() throws Exception {
        String testUrl = baseUrl + "/search/byNotificationIds?notificationIds=+12062901009,1234@test.com";
        String payload = "{\n" +
                "                \"confirmationNumber\": \"4508f678-4f7d-41bc-8fb1-47a82496ed2b\",\n" +
                "                \"id\": \"testId1234\",\n" +
                "                \"label\": \"\",\n" +
                "                \"notificationId\": \"+12062901009\",\n" +
                "                \"notificationType\": \"SMS\",\n" +
                "                \"created\": \"2016-11-18T19:39:16.427+0000\",\n" +
                "                \"modified\": \"2016-11-18T19:39:16.427+0000\",\n" +
                "                \"serviceOrderNumber\": \"testSon1234\",\n" +
                "                \"serviceUnitNumber\": \"testSun1234\"\n" +
                "            }";
        MvcResult mvcResult = mockMvc.perform(post(baseUrl).content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString(entityName + "/"))).andReturn();

        mockMvc.perform(get(testUrl))
                .andExpect(jsonPath("$._embedded.orderSubscriber[0].id").isNotEmpty())
                .andExpect(jsonPath("$._embedded.orderSubscriber[0].order").doesNotExist())
                .andReturn();

        //test projection orderDetail
        mockMvc.perform(get(testUrl + "&projection=orderDetail"))
                        .andExpect(jsonPath("$._embedded.orderSubscriber[0]").isNotEmpty())
                        .andExpect(jsonPath("$._embedded.orderSubscriber[0].order").exists())
                        .andReturn();
    }
}