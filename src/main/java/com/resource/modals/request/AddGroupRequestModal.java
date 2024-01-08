package com.resource.modals.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddGroupRequestModal {
	
	
	
	public HashMap<String, String> groupInfo = new HashMap<String, String>();
	
	
	public AddGroupRequestModal(){
		
		
		
	}


	public HashMap<String, String> getGroupInfo() {
		return groupInfo;
	}


	public void setContactInfo(HashMap<String, String> groupInfo) {
		this.groupInfo = groupInfo;
	}

}
