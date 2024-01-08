package com.resource.modals.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddContactRequestModal {
	
	
	
	public HashMap<String, String> contactInfo = new HashMap<String, String>();
	
	
	public AddContactRequestModal(){
		
		
		
	}


	public HashMap<String, String> getContactInfo() {
		return contactInfo;
	}


	public void setContactInfo(HashMap<String, String> contactInfo) {
		this.contactInfo = contactInfo;
	}

}
