package com.resource.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.resource.modals.ContactsModal;
import com.resource.modals.request.AddContactRequestModal;
import com.resource.modals.request.EditContactRequestModal;

public class ContactDBActivity {

	DatabaseConnection dbconn = new DatabaseConnection();

	private static final String SELECT_ALL_CONTACTS = "SELECT CONTACTS.*,CONTACT_GROUPS.name as groupName from CONTACTS LEFT JOIN CONTACT_GROUPS on CONTACTS.GROUPID = CONTACT_GROUPS.ID";

	public List<ContactsModal> getAllContacts() throws SQLException {

		List<ContactsModal> contacts = new ArrayList<>();

		try {

			Connection connection = dbconn.getDBConnection();

			if (dbconn == null) {

				return null;

			}

			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CONTACTS);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				Map<String, Object> contactListHashMap = new HashMap<>();

				contactListHashMap.put("id", rs.getString("id"));
				contactListHashMap.put("name", rs.getString("name"));
				contactListHashMap.put("phoneNumber", rs.getString("phoneNumber"));
				
				System.out.println("groupName..."+rs.getString("groupName"));
				
				contacts.add(new ContactsModal(rs.getString("id"), rs.getString("name"), rs.getString("phoneNumber"),
						rs.getString("groupName")));
			}

			return contacts;

		} catch (SQLException error) {

			throw error;

		}

	}

	public Boolean addContact(AddContactRequestModal request) throws SQLException {

		Boolean status = true;

		String column = "(name,phoneNumber,groupid)";
		String values = "VALUES (?, ?, ?)";

		String query = "INSERT INTO contacts" + column + " " + values;

		AddContactRequestModal requestData = new AddContactRequestModal();

		requestData.setContactInfo(request.getContactInfo());

		try {

			Connection connection = dbconn.getDBConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, (String) requestData.getContactInfo().get("name"));

			preparedStatement.setString(2, (String) requestData.getContactInfo().get("phoneNumber"));
			
			preparedStatement.setString(3, (String) requestData.getContactInfo().get("groupId"));

			preparedStatement.executeUpdate();

			return status;

		} catch (SQLException error) {

			throw error;
		}

	}

	public List<ContactsModal> getContactFromPhoneNumberAndName(String phoneNumber, String name, String skipId)
			throws SQLException {

		Boolean addSkipId = !skipId.isEmpty();

		String query = "SELECT * FROM contacts WHERE phoneNumber = ? AND name = ?";

		if (addSkipId) {
			query = query + "AND id!= ?";
		}

		List<ContactsModal> contacts = new ArrayList<>();

		try {

			Connection connection = dbconn.getDBConnection();

			if (dbconn == null) {

				return null;

			}

			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, (String) phoneNumber);

			preparedStatement.setString(2, (String) name);

			if (addSkipId) {
				preparedStatement.setString(3, (String) skipId);
			}

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				Map<String, Object> contactListHashMap = new HashMap<>();

				contactListHashMap.put("id", rs.getString("id"));
				contactListHashMap.put("name", rs.getString("name"));
				contactListHashMap.put("phoneNumber", rs.getString("phoneNumber"));
				contacts.add(new ContactsModal(rs.getString("id"), rs.getString("name"), rs.getString("phoneNumber")));
			}

			return contacts;

		} catch (SQLException error) {
			error.printStackTrace();

			throw error;

		}

	}

	public List<ContactsModal> getContactFromId(String id) throws SQLException {

		String query = "SELECT CONTACTS.*,CONTACT_GROUPS.name as groupName from CONTACTS LEFT JOIN CONTACT_GROUPS on CONTACTS.GROUPID = CONTACT_GROUPS.ID WHERE CONTACTS.ID = ? ";

		List<ContactsModal> contacts = new ArrayList<>();

		try {

			Connection connection = dbconn.getDBConnection();

			if (dbconn == null) {

				return null;

			}

			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, (String) id);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				Map<String, Object> contactListHashMap = new HashMap<>();

				contactListHashMap.put("id", rs.getString("id"));
				contactListHashMap.put("name", rs.getString("name"));
				contactListHashMap.put("phoneNumber", rs.getString("phoneNumber"));
				contactListHashMap.put("groupName", rs.getString("groupName"));
				contactListHashMap.put("groupId", rs.getString("groupId"));
				contacts.add(new ContactsModal(rs.getString("id"), rs.getString("name"), rs.getString("phoneNumber"), rs.getString("groupName"),  rs.getString("groupId")));
			}

			return contacts;

		} catch (SQLException error) {
			error.printStackTrace();

			throw error;

		}

	}

	public Boolean deleteContactUsingId(String id) throws SQLException {

		Boolean status = true;

		String query = "DELETE FROM CONTACTS WHERE id=?";

		try {

			Connection connection = dbconn.getDBConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, (String) id);

			preparedStatement.executeUpdate();

			return status;

		} catch (SQLException error) {

			throw error;
		}

	}

	public Boolean editContact(EditContactRequestModal request) throws SQLException {

		Boolean status = true;

		String query = "UPDATE CONTACTS SET name = ?, phoneNumber = ?, groupId = ? WHERE ID = ?";

		EditContactRequestModal requestData = new EditContactRequestModal();

		requestData.setContactInfo(request.getContactInfo());

		try {

			Connection connection = dbconn.getDBConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, (String) requestData.getContactInfo().get("name"));

			preparedStatement.setString(2, (String) requestData.getContactInfo().get("phoneNumber"));
			
			preparedStatement.setString(3, (String) requestData.getContactInfo().get("groupId"));

			preparedStatement.setString(4, (String) requestData.getContactInfo().get("id"));

			preparedStatement.executeUpdate();

			return status;

		} catch (SQLException error) {

			throw error;
		}

	}
	
	
	public List<ContactsModal> getContactUsingGroupId(String id) throws SQLException {

		String query = "SELECT * FROM contacts WHERE groupId = ? ";

		List<ContactsModal> contacts = new ArrayList<>();

		try {

			Connection connection = dbconn.getDBConnection();

			if (dbconn == null) {

				return null;

			}

			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, (String) id);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				Map<String, Object> contactListHashMap = new HashMap<>();

				contactListHashMap.put("id", rs.getString("id"));
				contactListHashMap.put("name", rs.getString("name"));
				contactListHashMap.put("phoneNumber", rs.getString("phoneNumber"));
				contacts.add(new ContactsModal(rs.getString("id"), rs.getString("name"), rs.getString("phoneNumber")));
			}

			return contacts;

		} catch (SQLException error) {
			error.printStackTrace();

			throw error;

		}

	}

}
