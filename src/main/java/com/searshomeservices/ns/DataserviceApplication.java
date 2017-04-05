package com.searshomeservices.ns;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataserviceApplication {
	private static final Logger logger = LoggerFactory.getLogger(DataserviceApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(DataserviceApplication.class, args);
		logger.info("shs-ns-data-service started successfully");
	}
	
	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.registerModule(new Jackson2HalModule());

		//consuming hateoas resources
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
		converter.setObjectMapper(mapper);

		return mapper;
	}
	
	@Bean
	@Primary
	public RestTemplate restTemplate() {
		//ignores certificate checking
		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		return new RestTemplate( new HttpComponentsClientHttpRequestFactory(httpClient));
	}
}
