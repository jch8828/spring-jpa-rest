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
public class ServiceOrderRepositoryTest extends TestBase {
    private String entityName = "order";

    @Value("${spring.data.rest.base-path}")
    private String basePath;

    private String baseUrl;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceOrderRepository tested;

    @Before
    public void setUp() throws Exception {
        baseUrl = basePath + "/" + entityName;
    }

    @Test
    public void shouldReturnRepositoryIndex() throws Exception {
        mockMvc.perform(get(basePath)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$._links." + entityName).exists());
    }

    @Test
    public void testCrud() throws Exception {
        //create
        String payload = "{\"canceled\": true,\n" +
                "                \"closed\": false,\n" +
                "                \"created\": \"2016-11-18T19:39:16.427+0000\",\n" +
                "                \"id\": {\n" +
                "                    \"serviceOrderNumber\": \"12345\",\n" +
                "                    \"serviceUnitNumber\": \"67890\"\n" +
                "                },\n" +
                "                \"modified\": \"2016-11-18T19:39:16.427+0000\",\n" +
                "                \"serviceEta\": \"10:42\",\n" +
                "                \"techArrived\": false,\n" +
                "                \"techEnRoute\": true }";
        MvcResult mvcResult = mockMvc.perform(post(baseUrl).content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString(entityName + "/"))).andReturn();

        //retrieve
        String location = mvcResult.getResponse().getHeader("Location");
        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id.serviceOrderNumber").value("12345"))
                .andExpect(jsonPath("$.canceled").value(true))
                .andExpect(jsonPath("$.techArrived").value(false))
                .andExpect(jsonPath("$.serviceEta").value("10:42"));

        //update
        String payloadNew = "{\"canceled\": true,\n" +
                "                \"closed\": true,\n" +
                "                \"created\": \"2016-11-18T19:39:16.427+0000\",\n" +
                "                \"id\": {\n" +
                "                    \"serviceOrderNumber\": \"12345\",\n" +
                "                    \"serviceUnitNumber\": \"67890\"\n" +
                "                },\n" +
                "                \"modified\": \"2016-11-18T19:39:16.427+0000\",\n" +
                "                \"serviceEta\": \"09:23\",\n" +
                "                \"techArrived\": true,\n" +
                "                \"techEnRoute\": false }";
        mockMvc.perform(put(location).content(payloadNew))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceEta").value("09:23"))
                .andExpect(jsonPath("$.techArrived").value(true))
                .andExpect(jsonPath("$.techEnRoute").value(false));

        //partial update
        mockMvc.perform(patch(location).content("{\"techArrived\": false}"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(location)).andExpect(status().isOk())
                .andExpect(jsonPath("$.serviceEta").value("09:23"))
                .andExpect(jsonPath("$.techArrived").value(false))
                .andExpect(jsonPath("$.techEnRoute").value(false));

        //delete
        mockMvc.perform(delete(location)).andExpect(status().isNoContent());
        mockMvc.perform(get(location)).andExpect(status().isNotFound());
    }

}