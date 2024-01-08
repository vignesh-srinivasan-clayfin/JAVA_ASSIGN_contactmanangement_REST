package com.resource.modals.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.resource.modals.ContactsModal;

public class AllContactsResponseModal {

	public List<HashMap<String, String>> contactList = new ArrayList<>();

	public AllContactsResponseModal(List<ContactsModal> contacts) {

		for (ContactsModal a : contacts) {

			System.out.println("Print Contacts..." + a.getPhoneNumber());

			HashMap<String, String> spObj = new HashMap<String, String>();

			spObj.put("phoneNumber", a.getPhoneNumber());
			spObj.put("id", a.getId());
			spObj.put("name", a.getName());
			spObj.put("groupName", a.getGroupName());

			this.contactList.add(spObj);

		}

	}

}
