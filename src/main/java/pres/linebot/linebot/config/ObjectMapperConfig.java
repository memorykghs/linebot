package pres.linebot.linebot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ObjectMapperConfig {

	@Bean
	public ObjectMapper objectMapperBuilder() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		return objectMapper;
	}
}
