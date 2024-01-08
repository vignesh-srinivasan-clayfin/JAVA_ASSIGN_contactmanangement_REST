package com.resource.modals.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.resource.modals.GroupModal;

public class AllGroupResponseModal {

	public List<HashMap<String, String>> groupList = new ArrayList<>();

	public AllGroupResponseModal(List<GroupModal> groups) {

		for (GroupModal a : groups) {

			HashMap<String, String> spObj = new HashMap<String, String>();

			spObj.put("id", a.getId());
			spObj.put("name", a.getName());

			groupList.add(spObj);

		}

	}

}
