package com.resource;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.resource.common.CustomException;
import com.resource.dao.ContactDBActivity;
import com.resource.dao.GroupDBActivity;
import com.resource.modals.GroupModal;
import com.resource.modals.request.AddGroupRequestModal;
import com.resource.modals.request.EditGroupRequestModal;

public class GroupActions {

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

	public Boolean checkDuplicateGroup(String name, String skipId)
			throws SQLException, CustomException {

		Boolean isDuplicate = false;

		GroupDBActivity dbActivity = new GroupDBActivity();

		List<GroupModal> groupList = dbActivity.getGroupFromName(name, skipId);

		if (groupList == null) {
			throw new CustomException("DB_CONN_ERROR");
		} else if (!groupList.isEmpty()) {

			isDuplicate = true;

		}

		return isDuplicate;

	}

	public boolean checkDuplicateGroup(String name) throws SQLException, CustomException {

		return checkDuplicateGroup(name, "");

	}

	public Boolean addGroup(AddGroupRequestModal request) throws SQLException, CustomException {

		Boolean isGroupAdded = false;

		GroupDBActivity dbActivity = new GroupDBActivity();

		isGroupAdded = dbActivity.addGroup(request);

		if (isGroupAdded == null) {
			throw new CustomException("DB_CONN_ERROR");
		}

		return isGroupAdded;

	}

	public List<GroupModal> findGroupUsingId(String id) throws SQLException, CustomException {

		GroupDBActivity dbActivity = new GroupDBActivity();

		List<GroupModal> contactList = dbActivity.getGroupFromId(id);

		System.out.println("contactList..." + contactList);

		if (contactList == null) {
			throw new CustomException("DB_CONN_ERROR");
		}

		return contactList;

	}

	public Boolean deleteGroupUsingId(String id) throws SQLException, CustomException {

		Boolean isDeleted = false;

		GroupDBActivity dbActivity = new GroupDBActivity();

		isDeleted = dbActivity.deleteGroupUsingId(id);

		if (isDeleted == null) {
			throw new CustomException("DB_CONN_ERROR");
		}

		return isDeleted;

	}

	public Boolean updateGroupUsingId(EditGroupRequestModal request) throws SQLException, CustomException {

		Boolean isUpdated = false;

		GroupDBActivity dbActivity = new GroupDBActivity();

		isUpdated = dbActivity.editGroup(request);

		if (isUpdated == null) {
			throw new CustomException("DB_CONN_ERROR");
		}

		return isUpdated;

	}

}
