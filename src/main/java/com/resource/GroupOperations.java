package com.resource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import com.resource.common.CustomException;
import com.resource.common.Helper;
import com.resource.common.ResponseHelper;
import com.resource.dao.GroupDBActivity;
import com.resource.modals.ContactsModal;
import com.resource.modals.GroupModal;
import com.resource.modals.request.AddGroupRequestModal;
import com.resource.modals.request.EditGroupRequestModal;
import com.resource.modals.response.AddGroupResponseModal;
import com.resource.modals.response.AllGroupResponseModal;
import com.resource.modals.response.DeleteGroupResponseModal;
import com.resource.modals.response.EditGroupResponseModal;
import com.resource.modals.response.ResponseModal;

public class GroupOperations {

	public Response getAllGroupsFromDB() {

		String jsonData = "";

		Boolean status = true;

		ResponseHelper reshelper = new ResponseHelper();

		ResponseModal<AllGroupResponseModal> tmpResponse = new ResponseModal<AllGroupResponseModal>();

		Helper<ResponseModal<AllGroupResponseModal>> helper = new Helper<ResponseModal<AllGroupResponseModal>>();

		try {

			GroupDBActivity dbActivity = new GroupDBActivity();

			List<GroupModal> contactList = dbActivity.getAllGroups();

			if (contactList == null) {
				throw new CustomException("DB_CONN_ERROR");
			}

			AllGroupResponseModal responseBody = new AllGroupResponseModal(contactList);

			tmpResponse.setResponseBody(responseBody);

		} catch (SQLException | CustomException e) {

			reshelper.exceptionHandler(e);

			status = reshelper.getStatus();
			tmpResponse.setError(reshelper.getError());
			tmpResponse.setErrorCode(reshelper.getErrorCode());

		}

		jsonData = helper.dataToJson(tmpResponse);

		return reshelper.sendResponse(jsonData, status);

	}

	public Response addGroupToDB(AddGroupRequestModal request) {

		String jsonData = "";

		Boolean status = true;

		ResponseHelper reshelper = new ResponseHelper();

		ResponseModal<AddGroupResponseModal> tmpResponse = new ResponseModal<AddGroupResponseModal>();

		Helper<ResponseModal<AddGroupResponseModal>> helper = new Helper<ResponseModal<AddGroupResponseModal>>();

		try {
			
			String name = "";

			Map<String, String> groupInfo = new HashMap<String, String>();

			groupInfo = request.getGroupInfo();

			for (Map.Entry<String, String> entry : groupInfo.entrySet()) {
			

				if (entry.getKey() == "name") {
					name = entry.getValue();
				}
			}

			GroupActions groupActions = new GroupActions();

//			if (!contactActions.validatePhoneNumber(phoneNumber)) {
//
//				throw new CustomException("FIELD_VALIDATION_ERROR", "FIELD_VALIDATION_ERR",
//						"Phonenumber should be 10 degits");
//
//			}

			if (!groupActions.validateName(name)) {

				throw new CustomException("FIELD_VALIDATION_ERROR", "FIELD_VALIDATION_ERR",
						"Name should contains atleast 5 character");

			}

			if (groupActions.checkDuplicateGroup(name)) {

				throw new CustomException("GLOBAL_ERROR", "GLOBAL_ERROR", "Duplicate Contact");

			}

			groupActions.addGroup(request);

			AddGroupResponseModal responseBody = new AddGroupResponseModal();

			responseBody.setStatus("Group Added Successfully");

			tmpResponse.setResponseBody(responseBody);

		} catch (SQLException | CustomException e) {

			reshelper.exceptionHandler(e);

			status = reshelper.getStatus();
			tmpResponse.setError(reshelper.getError());
			tmpResponse.setErrorCode(reshelper.getErrorCode());

		}

		jsonData = helper.dataToJson(tmpResponse);

		return reshelper.sendResponse(jsonData, status);

	}

	public Response deleteGroup(String id) {

		String jsonData = "";

		Boolean status = true;

		ResponseHelper reshelper = new ResponseHelper();

		ResponseModal<DeleteGroupResponseModal> tmpResponse = new ResponseModal<DeleteGroupResponseModal>();

		Helper<ResponseModal<DeleteGroupResponseModal>> helper = new Helper<ResponseModal<DeleteGroupResponseModal>>();

		try {

			String groupId = id;
			
			ContactActions contactActions = new ContactActions();
			
			List<ContactsModal> contactListUsingGroup = contactActions.findContactUsingGroupId(groupId);
			
			
			if (!contactListUsingGroup.isEmpty()) {

				throw new CustomException("GLOBAL_ERROR", "GLOBAL_ERROR", "Group used in contacts");

			}
			

			GroupActions groupActions = new GroupActions();

			List<GroupModal> findContact = groupActions.findGroupUsingId(groupId);

			if (findContact.isEmpty()) {

				throw new CustomException("GLOBAL_ERROR", "GLOBAL_ERROR", "No Group Found");

			}

			groupActions.deleteGroupUsingId(groupId);

			DeleteGroupResponseModal responseBody = new DeleteGroupResponseModal();

			responseBody.setStatus("Group Deleted Successfully");

			tmpResponse.setResponseBody(responseBody);

		} catch (SQLException | CustomException e) {

			reshelper.exceptionHandler(e);

			status = reshelper.getStatus();
			tmpResponse.setError(reshelper.getError());
			tmpResponse.setErrorCode(reshelper.getErrorCode());

		}

		jsonData = helper.dataToJson(tmpResponse);

		return reshelper.sendResponse(jsonData, status);

	}
	
	
	public Response editGroupToDB(EditGroupRequestModal request) {

		String jsonData = "";

		Boolean status = true;

		ResponseHelper reshelper = new ResponseHelper();

		ResponseModal<EditGroupResponseModal> tmpResponse = new ResponseModal<EditGroupResponseModal>();

		Helper<ResponseModal<EditGroupResponseModal>> helper = new Helper<ResponseModal<EditGroupResponseModal>>();

		try {

			String name = "";
			String id = "";

			Map<String, String> groupInfo = new HashMap<String, String>();

			groupInfo = request.getGroupInfo();

			for (Map.Entry<String, String> entry : groupInfo.entrySet()) {

				if (entry.getKey() == "name") {
					name = entry.getValue();
				}
				
				if (entry.getKey() == "id") {
					id = entry.getValue();
				}
			}

			GroupActions groupActions = new GroupActions();

			List<GroupModal> findGroup = groupActions.findGroupUsingId(id);
			
			System.out.println("findGroup...."+findGroup);

			if (findGroup.isEmpty()) {

				throw new CustomException("GLOBAL_ERROR", "GLOBAL_ERROR", "No Contact Found");

			}		
			
			
			

			if (!groupActions.validateName(name)) {

				throw new CustomException("FIELD_VALIDATION_ERROR", "FIELD_VALIDATION_ERR",
						"Name should contains atleast 5 character");

			}
			

			String skipId = id ;
			
			if (groupActions.checkDuplicateGroup(name,skipId)) {

				throw new CustomException("GLOBAL_ERROR", "GLOBAL_ERROR", "Duplicate Group");

			}

			groupActions.updateGroupUsingId(request);

			EditGroupResponseModal responseBody = new EditGroupResponseModal();

			responseBody.setStatus("Group Updated Successfully");
	
			tmpResponse.setResponseBody(responseBody);

		} catch (SQLException | CustomException e) {

			reshelper.exceptionHandler(e);

			status = reshelper.getStatus();
			tmpResponse.setError(reshelper.getError());
			tmpResponse.setErrorCode(reshelper.getErrorCode());

		}

		jsonData = helper.dataToJson(tmpResponse);

		return reshelper.sendResponse(jsonData, status);

	}
	
	
	
	

}
