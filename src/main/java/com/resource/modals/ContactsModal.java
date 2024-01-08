package com.resource.modals;

import java.util.HashMap;
import java.util.Map;

public class ContactsModal {
	
		protected String id;
		protected String name;
		protected String phoneNumber;
		protected String groupName;
		protected String groupId;

		public ContactsModal() {
		}

		public ContactsModal(Map<String, Object> params) {
			super();
			// Access params by key
			this.id = (String) params.get("id");
			this.name = (String) params.get("name");
			this.phoneNumber = (String) params.get("phoneNumber");
			this.groupName = (String) params.get("groupName");
			this.groupId = (String) params.get("groupId");

		}
		
		 public String getGroupId() {
			return groupId;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}

		public ContactsModal(String id, String name, String phoneNumber,String groupName) {
		        this.id = id;
		        this.name = name;
		        this.phoneNumber = phoneNumber;
		        this.groupName = groupName;
		    }
		
		public ContactsModal(String id, String name, String phoneNumber,String groupName,String groupId) {
	        this.id = id;
	        this.name = name;
	        this.phoneNumber = phoneNumber;
	        this.groupName = groupName;
	        this.groupId = groupId;
	    }
		 
		 public ContactsModal(String id, String name, String phoneNumber) {
		        this.id = id;
		        this.name = name;
		        this.phoneNumber = phoneNumber;
		    }

//		public Map<String, Object> getContactList() {
//			Map<String, Object> params = new HashMap<>();
//			params.put("id", this.id);
//			params.put("name", this.name);
//			params.put("phoneNumber", this.phoneNumber);
//			params.put("groupName", this.groupName);
//
//			return params;
//		}
		
		
		public String getPhoneNumber() {
		    return this.phoneNumber;
		  }
		
		public void setId(String id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}

		public String getId() {
		    return this.id;
		  }
		
		public String getName() {
		    return this.name;
		  }
		
		public String getGroupName() {
		    return this.groupName;
		  }

}
