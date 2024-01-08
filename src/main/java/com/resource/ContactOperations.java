package com.resource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import com.resource.common.CustomException;
import com.resource.common.Helper;
import com.resource.common.ResponseHelper;
import com.resource.dao.ContactDBActivity;
import com.resource.modals.ContactsModal;
import com.resource.modals.GroupModal;
import com.resource.modals.request.AddContactRequestModal;
import com.resource.modals.request.EditContactRequestModal;
import com.resource.modals.response.AddContactResponseModal;
import com.resource.modals.response.AllContactsResponseModal;
import com.resource.modals.response.DeleteContactResponseModal;
import com.resource.modals.response.EditContactResponseModal;
import com.resource.modals.response.GetContactDetailResponseModal;
import com.resource.modals.response.ResponseModal;

public class ContactOperations {

	public Response getAllContactsFromDB() {

		String jsonData = "";

		Boolean status = true;

		ResponseHelper reshelper = new ResponseHelper();

		ResponseModal<AllContactsResponseModal> tmpResponse = new ResponseModal<AllContactsResponseModal>();

		Helper<ResponseModal<AllContactsResponseModal>> helper = new Helper<ResponseModal<AllContactsResponseModal>>();

		try {

			ContactDBActivity dbActivity = new ContactDBActivity();

			List<ContactsModal> contactList = dbActivity.getAllContacts();

			if (contactList == null) {
				throw new CustomException("DB_CONN_ERROR");
			}

			AllContactsResponseModal contactRes = new AllContactsResponseModal(contactList);

			tmpResponse.setResponseBody(contactRes);

		} catch (SQLException | CustomException e) {
			
			e.printStackTrace();

			reshelper.exceptionHandler(e);

			status = reshelper.getStatus();
			tmpResponse.setError(reshelper.getError());
			tmpResponse.setErrorCode(reshelper.getErrorCode());

		}

		jsonData = helper.dataToJson(tmpResponse);

		return reshelper.sendResponse(jsonData, status);

	}

	public Response addContactToDB(AddContactRequestModal request) {

		String jsonData = "";

		Boolean status = true;

		ResponseHelper reshelper = new ResponseHelper();

		ResponseModal<AddContactResponseModal> tmpResponse = new ResponseModal<AddContactResponseModal>();

		Helper<ResponseModal<AddContactResponseModal>> helper = new Helper<ResponseModal<AddContactResponseModal>>();

		try {

			String phoneNumber = "";
			String name = "";
			String groupId = "";

			Map<String, String> contactInfo = new HashMap<String, String>();

			contactInfo = request.getContactInfo();

			for (Map.Entry<String, String> entry : contactInfo.entrySet()) {
				if (entry.getKey() == "phoneNumber") {
					phoneNumber = entry.getValue();
				}

				if (entry.getKey() == "name") {
					name = entry.getValue();
				}
				
				if (entry.getKey() == "groupId") {
					groupId = entry.getValue();
				}
			}

			ContactActions contactActions = new ContactActions();

			if (!contactActions.validatePhoneNumber(phoneNumber)) {

				throw new CustomException("FIELD_VALIDATION_ERROR", "FIELD_VALIDATION_ERR",
						"Phonenumber should be 10 degits");

			}

			if (!contactActions.validateName(name)) {

				throw new CustomException("FIELD_VALIDATION_ERROR", "FIELD_VALIDATION_ERR",
						"Name should contains atleast 5 character");

			}

			if (contactActions.checkDuplicateContact(phoneNumber,name)) {

				throw new CustomException("GLOBAL_ERROR", "GLOBAL_ERROR", "Duplicate Contact");

			}

			contactActions.addContact(request);

			AddContactResponseModal addContactRes = new AddContactResponseModal();

			addContactRes.setStatus("Contact Added Successfully");

			tmpResponse.setResponseBody(addContactRes);

		} catch (SQLException | CustomException e) {

			reshelper.exceptionHandler(e);

			status = reshelper.getStatus();
			tmpResponse.setError(reshelper.getError());
			tmpResponse.setErrorCode(reshelper.getErrorCode());

		}

		jsonData = helper.dataToJson(tmpResponse);

		return reshelper.sendResponse(jsonData, status);

	}

	public Response deleteContact(String id) {

		String jsonData = "";

		Boolean status = true;

		ResponseHelper reshelper = new ResponseHelper();

		ResponseModal<DeleteContactResponseModal> tmpResponse = new ResponseModal<DeleteContactResponseModal>();

		Helper<ResponseModal<DeleteContactResponseModal>> helper = new Helper<ResponseModal<DeleteContactResponseModal>>();

		try {

			String contactId = id;

			ContactActions contactActions = new ContactActions();

			List<ContactsModal> findContact = contactActions.findContactUsingId(contactId);

			if (findContact.isEmpty()) {

				throw new CustomException("GLOBAL_ERROR", "GLOBAL_ERROR", "No Contact Found");

			}

			contactActions.deleteContactUsingId(contactId);

			DeleteContactResponseModal response = new DeleteContactResponseModal();

			response.setStatus("Contact Deleted Successfully");

			tmpResponse.setResponseBody(response);

		} catch (SQLException | CustomException e) {

			reshelper.exceptionHandler(e);

			status = reshelper.getStatus();
			tmpResponse.setError(reshelper.getError());
			tmpResponse.setErrorCode(reshelper.getErrorCode());

		}

		jsonData = helper.dataToJson(tmpResponse);

		return reshelper.sendResponse(jsonData, status);

	}
	
	
	public Response editContactToDB(EditContactRequestModal request) {

		String jsonData = "";

		Boolean status = true;

		ResponseHelper reshelper = new ResponseHelper();

		ResponseModal<EditContactResponseModal> tmpResponse = new ResponseModal<EditContactResponseModal>();

		Helper<ResponseModal<EditContactResponseModal>> helper = new Helper<ResponseModal<EditContactResponseModal>>();

		try {

			String phoneNumber = "";
			String name = "";
			String id = "";
			String groupId = "";

			Map<String, String> contactInfo = new HashMap<String, String>();

			contactInfo = request.getContactInfo();

			for (Map.Entry<String, String> entry : contactInfo.entrySet()) {
				if (entry.getKey() == "phoneNumber") {
					phoneNumber = entry.getValue();
				}

				if (entry.getKey() == "name") {
					name = entry.getValue();
				}
				
				if (entry.getKey() == "id") {
					id = entry.getValue();
				}
				
				if (entry.getKey() == "groupId") {
					groupId = entry.getValue();
				}
				
				
				
			}
			
			GroupActions groupActions = new GroupActions();
			
			List<GroupModal> findGroup = groupActions.findGroupUsingId(groupId);
			
			if (findGroup.isEmpty()) {

				throw new CustomException("GLOBAL_ERROR", "GLOBAL_ERROR", "GROUP ID invalid");

			}
			

			ContactActions contactActions = new ContactActions();

			List<ContactsModal> findContact = contactActions.findContactUsingId(id);
			
			System.out.println("findContact...."+findContact);

			if (findContact.isEmpty()) {

				throw new CustomException("GLOBAL_ERROR", "GLOBAL_ERROR", "No Contact Found");

			}		
			
			
			if(findContact.get(0).getName().contentEquals(name)
					&& findContact.get(0).getPhoneNumber().contentEquals(phoneNumber)
					&& findContact.get(0).getGroupId().contentEquals(groupId)) {
				
				throw new CustomException("GLOBAL_ERROR", "GLOBAL_ERROR", "Contact details is similar to old record");
				
			}
			

			if (!contactActions.validateName(name)) {

				throw new CustomException("FIELD_VALIDATION_ERROR", "FIELD_VALIDATION_ERR",
						"Name should contains atleast 5 character");

			}
			
			if (!contactActions.validatePhoneNumber(phoneNumber)) {

				throw new CustomException("FIELD_VALIDATION_ERROR", "FIELD_VALIDATION_ERR",
						"Phonenumber should be 10 degits");

			}	
			

			String skipId = id ;
			
			if (contactActions.checkDuplicateContact(phoneNumber,name,skipId)) {

				throw new CustomException("GLOBAL_ERROR", "GLOBAL_ERROR", "Duplicate Contact");

			}

			contactActions.updateContactUsingId(request);

			EditContactResponseModal response = new EditContactResponseModal();

			response.setStatus("Contact Updated Successfully");

			tmpResponse.setResponseBody(response);

		} catch (SQLException | CustomException e) {
			
			e.printStackTrace();

			reshelper.exceptionHandler(e);

			status = reshelper.getStatus();
			tmpResponse.setError(reshelper.getError());
			tmpResponse.setErrorCode(reshelper.getErrorCode());

		}

		jsonData = helper.dataToJson(tmpResponse);

		return reshelper.sendResponse(jsonData, status);

	}
	
	
	
	public Response getContactDetailFromDB(String id) {

		String jsonData = "";

		Boolean status = true;

		ResponseHelper reshelper = new ResponseHelper();

		ResponseModal<GetContactDetailResponseModal> tmpResponse = new ResponseModal<GetContactDetailResponseModal>();

		Helper<ResponseModal<GetContactDetailResponseModal>> helper = new Helper<ResponseModal<GetContactDetailResponseModal>>();

		try {

			String contactId = id;

			ContactActions contactActions = new ContactActions();

			List<ContactsModal> findContact = contactActions.findContactUsingId(contactId);

			if (findContact.isEmpty()) {

				throw new CustomException("GLOBAL_ERROR", "GLOBAL_ERROR", "No Contact Found");

			}

			GetContactDetailResponseModal response = new GetContactDetailResponseModal();
			
			ContactsModal contactDetails = new ContactsModal();
			
			contactDetails.setId(findContact.get(0).getId());
			contactDetails.setName(findContact.get(0).getName());
			contactDetails.setPhoneNumber(findContact.get(0).getPhoneNumber());
			contactDetails.setGroupName(findContact.get(0).getGroupName());

			response.setContactDetail(contactDetails);

			tmpResponse.setResponseBody(response);

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
