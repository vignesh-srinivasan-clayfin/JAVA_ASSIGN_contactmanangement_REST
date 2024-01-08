package com.resource.modals;

import java.util.HashMap;
import java.util.Map;

public class GroupModal {
	
	
		protected String id;
		protected String name;

		public GroupModal() {
		}

		public GroupModal(Map<String, Object> params) {
			super();
			// Access params by key
			this.id = (String) params.get("id");
			this.name = (String) params.get("name");
		}
		
		 public GroupModal(String id, String name) {
		        this.id = id;
		        this.name = name;
		    }

		public Map<String, Object> getContactList() {
			Map<String, Object> params = new HashMap<>();
			params.put("id", this.id);
			params.put("name", this.name);

			return params;
		}
		
		public String getId() {
		    return this.id;
		  }
		
		public String getName() {
		    return this.name;
		  }

}
