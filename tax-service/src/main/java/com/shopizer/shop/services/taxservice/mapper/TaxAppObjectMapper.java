package com.shopizer.shop.services.taxservice.mapper;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class TaxAppObjectMapper {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public Object convertJsonToObject(String jsonInput, Class<?> classType) {
        Object output = null;
        try {
            output = objectMapper.readValue(jsonInput, classType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
        return output;
    }

	public String convertObjectToString(Object input) {
        String output = null;
        try {
            output = objectMapper.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return (output == null ? "": output);
    }
}
