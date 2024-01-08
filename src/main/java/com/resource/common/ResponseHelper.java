package com.resource.common;

import java.sql.SQLException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ResponseHelper {
	
	
	public Boolean status;
	
	public String error;
	
	public String errorCode;
	

	
	public Response sendResponse (String response,Boolean status) {
		
		Status apiStatus;
		
		String tmpResponse = "";
				
		if(response != null) {
			tmpResponse = response;
		}
		
		if(status) {
			apiStatus = Response.Status.OK;
		}else {
			apiStatus = Response.Status.BAD_REQUEST;
		}
		
		return Response
				.ok(tmpResponse)
				.status(apiStatus)
				.header("Access-Control-Allow-Origin", "*")
		.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
		.header("Access-Control-Allow-Headers", "Content-Type").build();
	}
	
	
	public void exceptionHandler (Exception e) {
		
		
		String error = "";
		String errorCode = "";

		if (e.getMessage() == "DB_CONN_ERROR") {

			error = "DB Erro";
			errorCode = "DB_ERROR";

		}else if (e.getMessage() == "FIELD_VALIDATION_ERROR") {		
			
			
			error = ((CustomException) e).getErrorCode();
			errorCode = ((CustomException) e).getErrorMessage();
			
			
		}else if (e.getMessage() == "GLOBAL_ERROR") {		
			
			
			error = ((CustomException) e).getErrorCode();
			errorCode = ((CustomException) e).getErrorMessage();
			
			
		} else {

			error = "SQL Exception Error";
			errorCode = "DB_ERROR_SQLEXCEPTION";

		}
		
		this.status = false;
		this.error = error;
		this.errorCode = errorCode;
				
		
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}


	public String getError() {
		return error;
	}


	public void setError(String error) {
		this.error = error;
	}


	public String getErrorCode() {
		return errorCode;
	}


	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
