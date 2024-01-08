package com.resource.common;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.resource.modals.ContactsModal;

public class Helper<T> {
	
	
	private final ObjectMapper objectMapper = new ObjectMapper();

//	 public ContactsModal parseJsonRequest(String json) {
//	        try {
//	            // Use Jackson's ObjectMapper to parse JSON into a RequestBodyModel object
//	            return objectMapper.readValue(json, ContactsModal.class);
//	        } catch (IOException e) {
//	            // Handle JSON parsing exception
//	            e.printStackTrace();
//	            return null;
//	        }
//	    }
	 
	 public String dataToJson(T dataList) {
	        try {
	            // Use Jackson's ObjectMapper to convert List<Data> to JSON
	            return objectMapper.writeValueAsString(dataList);
	        } catch (JsonProcessingException e) {
	            // Handle the exception appropriately (log or throw a custom exception)
	            e.printStackTrace();
	            return ""; // or throw a custom exception
	        }
	    }
	 
	 
	 

}
