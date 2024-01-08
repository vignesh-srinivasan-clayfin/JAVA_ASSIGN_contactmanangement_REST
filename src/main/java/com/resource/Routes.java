package com.resource;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.resource.modals.request.AddContactRequestModal;
import com.resource.modals.request.AddGroupRequestModal;
import com.resource.modals.request.EditContactRequestModal;
import com.resource.modals.request.EditGroupRequestModal;

@Path("/")
public class Routes {

	private ContactOperations contactOp = new ContactOperations();
	
	
	private GroupOperations groupOp = new GroupOperations();
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response getIndex() {

		return Response.ok("Test").header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
				.header("Access-Control-Allow-Headers", "Content-Type").build();
	}

    @GET
	@Path("/getAllContacts")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllContacts() {

		return contactOp.getAllContactsFromDB();

	}

	@POST
	@Path("/addContact")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addContact(AddContactRequestModal request) {

		return contactOp.addContactToDB(request);

	}
	
	@DELETE
	@Path("/deleteContact/{id}")
	public Response deleteContact(@PathParam("id") String id) {
		
		return contactOp.deleteContact(id);
		
		
		
	}
	
	@POST
	@Path("/editContact")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editContact(EditContactRequestModal request) {

		return contactOp.editContactToDB(request);

	}
	
	
	@GET
	@Path("/getAllGroups")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllGroups() {

		return groupOp.getAllGroupsFromDB();

	}
	
	
	@POST
	@Path("/addGroup")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addGroup(AddGroupRequestModal request) {

		return groupOp.addGroupToDB(request);

	}
	
	
	@DELETE
	@Path("/deleteGroup/{id}")
	public Response deleteGroup(@PathParam("id") String id) {
		
		return groupOp.deleteGroup(id);
		
		
		
	}
	
	
	@POST
	@Path("/editGroup")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editGroup(EditGroupRequestModal request) {

		return groupOp.editGroupToDB(request);

	}
	
	@GET
	@Path("/getContactDetail/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getContactDetail(@PathParam("id") String id) {

		return contactOp.getContactDetailFromDB(id);

	}
	
	

}
