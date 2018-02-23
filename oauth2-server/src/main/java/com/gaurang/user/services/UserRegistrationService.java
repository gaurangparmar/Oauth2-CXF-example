package com.gaurang.user.services;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;

import com.gaurang.user.data.User;

@Path("/")
public class UserRegistrationService {

	private Map<String,User> users = new HashMap<>();
	
	public void setUsers(Map<String, String> users) {
		for(Map.Entry<String, String> user:users.entrySet()) {
			User regUser = new User(user.getKey(),user.getValue());
			this.users.put(user.getKey(),regUser);
		}
	}
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/registerUser")
	public void registerClientApps(MultipartBody body) {
		
		String username = body.getAttachmentObject("username", String.class);
	    String password = body.getAttachmentObject("password", String.class);
	    
	    if(username == null || password == null) {
			throw new IllegalArgumentException("User details are required.");
		}
		
		if(users.containsKey(username)) {
			throw new IllegalArgumentException("User already registered.");
		}
		
		users.put(username, new User(username,password));
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/registerUser")
	public void registerUser(@FormParam("username")String username,@FormParam("password")String password) {
		
		if(username == null || password == null) {
			throw new IllegalArgumentException("User details are required.");
		}
		
		if(users.containsKey(username)) {
			throw new IllegalArgumentException("User already registered.");
		}
		
		users.put(username, new User(username,password));
	}
	
	public User getUser(String username) {
		return users.get(username);
	}
	
}
