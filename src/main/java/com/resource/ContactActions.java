package com.resource;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.resource.common.CustomException;
import com.resource.dao.ContactDBActivity;
import com.resource.modals.ContactsModal;
import com.resource.modals.request.AddContactRequestModal;
import com.resource.modals.request.EditContactRequestModal;

public class ContactActions {

	public Boolean validatePhoneNumber(String phoneNumber) {

		Boolean isValid = false;

		// Your input string
		String input = phoneNumber;// Change this to your actual input

		// Regex pattern for exactly 10 digits
		String regexPattern = "\\d{10}";

		// Create a Pattern object
		Pattern pattern = Pattern.compile(regexPattern);

		// Create a Matcher object
		Matcher matcher = pattern.matcher(input);

		// Check if the input string matches the pattern
		if (matcher.matches()) {
			System.out.println("Input string contains exactly 10 digits.");

			isValid = true;

		} else {
			System.out.println("Input string does not contain exactly 10 digits.");

			isValid = false;

		}

		return isValid;

	}

	public Boolean validateName(String name) {

		Boolean isValid = true;

		if (name == null || name.trim().isEmpty()) {
			isValid = false;
		}

		// Check if the string has at least 5 characters
		if (isValid && name.length() < 5) {
			isValid = false;
		}

		return isValid;

	}

	public Boolean checkDuplicateContact(String phoneNumber, String name, String skipId)
			throws SQLException, CustomException {

		Boolean isDuplicate = false;

		ContactDBActivity dbActivity = new ContactDBActivity();

		List<ContactsModal> contactList = dbActivity.getContactFromPhoneNumberAndName(phoneNumber, name, skipId);

		System.out.println("contactList..." + contactList);

		if (contactList == null) {
			throw new CustomException("DB_CONN_ERROR");
		} else if (!contactList.isEmpty()) {

			isDuplicate = true;

		}

		return isDuplicate;

	}

	public boolean checkDuplicateContact(String phoneNumber, String name) throws SQLException, CustomException {

		return checkDuplicateContact(phoneNumber, name, "");

	}

	public Boolean addContact(AddContactRequestModal request) throws SQLException, CustomException {

		Boolean isContactAdded = false;

		ContactDBActivity dbActivity = new ContactDBActivity();

		isContactAdded = dbActivity.addContact(request);

		if (isContactAdded == null) {
			throw new CustomException("DB_CONN_ERROR");
		}

		return isContactAdded;

	}

	public List<ContactsModal> findContactUsingId(String id) throws SQLException, CustomException {

		ContactDBActivity dbActivity = new ContactDBActivity();

		List<ContactsModal> contactList = dbActivity.getContactFromId(id);

		System.out.println("contactList..." + contactList);

		if (contactList == null) {
			throw new CustomException("DB_CONN_ERROR");
		}

		return contactList;

	}

	public Boolean deleteContactUsingId(String id) throws SQLException, CustomException {

		Boolean isDeleted = false;

		ContactDBActivity dbActivity = new ContactDBActivity();

		isDeleted = dbActivity.deleteContactUsingId(id);

		if (isDeleted == null) {
			throw new CustomException("DB_CONN_ERROR");
		}

		return isDeleted;

	}

	public Boolean updateContactUsingId(EditContactRequestModal request) throws SQLException, CustomException {

		Boolean isUpdated = false;

		ContactDBActivity dbActivity = new ContactDBActivity();

		isUpdated = dbActivity.editContact(request);

		if (isUpdated == null) {
			throw new CustomException("DB_CONN_ERROR");
		}

		return isUpdated;

	}
	
	
	public List<ContactsModal> findContactUsingGroupId(String id) throws SQLException, CustomException {

		ContactDBActivity dbActivity = new ContactDBActivity();

		List<ContactsModal> contactList = dbActivity.getContactUsingGroupId(id);

		System.out.println("contactList..." + contactList);

		if (contactList == null) {
			throw new CustomException("DB_CONN_ERROR");
		}

		return contactList;

	}
	
	
	

}
