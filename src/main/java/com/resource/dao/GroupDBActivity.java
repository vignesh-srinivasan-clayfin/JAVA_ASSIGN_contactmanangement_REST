package com.resource.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.resource.modals.GroupModal;
import com.resource.modals.request.AddGroupRequestModal;
import com.resource.modals.request.EditContactRequestModal;
import com.resource.modals.request.EditGroupRequestModal;

public class GroupDBActivity {

	DatabaseConnection dbconn = new DatabaseConnection();

	private static final String SELECT_ALL_CONTACTS = "SELECT * FROM contact_groups";

	public List<GroupModal> getAllGroups() throws SQLException {

		List<GroupModal> groups = new ArrayList<>();

		try {

			Connection connection = dbconn.getDBConnection();

			if (dbconn == null) {

				return null;

			}

			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CONTACTS);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				Map<String, Object> groupsListHashMap = new HashMap<>();

				groupsListHashMap.put("id", rs.getString("id"));
				groupsListHashMap.put("name", rs.getString("name"));
				groups.add(new GroupModal(rs.getString("id"), rs.getString("name")));
			}

			return groups;

		} catch (SQLException error) {

			throw error;

		}

	}

	public Boolean addGroup(AddGroupRequestModal request) throws SQLException {

		Boolean status = true;

		String column = "(name)";
		String values = "VALUES (?)";

		String query = "INSERT INTO contact_groups" + column + " " + values;

		AddGroupRequestModal requestData = new AddGroupRequestModal();

		requestData.setContactInfo(request.getGroupInfo());

		try {

			Connection connection = dbconn.getDBConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, (String) requestData.getGroupInfo().get("name"));

			preparedStatement.executeUpdate();

			return status;

		} catch (SQLException error) {

			throw error;
		}

	}

	public List<GroupModal> getGroupFromName(String name, String skipId)
			throws SQLException {

		Boolean addSkipId = !skipId.isEmpty();

		String query = "SELECT * FROM contacts WHERE name = ?";

		if (addSkipId) {
			query = query + "AND id!= ?";
		}

		List<GroupModal> groups = new ArrayList<>();

		try {

			Connection connection = dbconn.getDBConnection();

			if (dbconn == null) {

				return null;

			}

			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, (String) name);

			if (addSkipId) {
				preparedStatement.setString(2, (String) skipId);
			}

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				Map<String, Object> groupListHashMap = new HashMap<>();

				groupListHashMap.put("id", rs.getString("id"));
				groupListHashMap.put("name", rs.getString("name"));
				groups.add(new GroupModal(rs.getString("id"), rs.getString("name")));
			}

			return groups;

		} catch (SQLException error) {
			error.printStackTrace();

			throw error;

		}

	}

	public List<GroupModal> getGroupFromId(String id) throws SQLException {

		String query = "SELECT * FROM CONTACT_GROUPS WHERE id = ? ";

		List<GroupModal> groups = new ArrayList<>();

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
				groups.add(new GroupModal(rs.getString("id"), rs.getString("name")));
			}

			return groups;

		} catch (SQLException error) {
			error.printStackTrace();

			throw error;

		}

	}

	public Boolean deleteGroupUsingId(String id) throws SQLException {

		Boolean status = true;

		String query = "DELETE FROM CONTACT_GROUPS WHERE id=?";

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

	public Boolean editGroup(EditGroupRequestModal request) throws SQLException {

		Boolean status = true;

		String query = "UPDATE CONTACT_GROUPS SET name = ? WHERE ID = ?";

		EditGroupRequestModal requestData = new EditGroupRequestModal();

		requestData.setGroupInfo(request.getGroupInfo());

		try {

			Connection connection = dbconn.getDBConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, (String) requestData.getGroupInfo().get("name"));

			preparedStatement.setString(2, (String) requestData.getGroupInfo().get("id"));

			preparedStatement.executeUpdate();

			return status;

		} catch (SQLException error) {

			throw error;
		}

	}

}
