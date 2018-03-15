package com.clientapp;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.common.security.SimplePrincipal;
import org.apache.cxf.common.util.Base64Exception;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.security.SecurityContext;

@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class SecurityContextFilter implements ContainerRequestFilter {

	@Context
	private HttpHeaders headers;
	
	
	private Map<String, String> users;
	private String realm;
	public void setUsers(Map<String, String> users) {
		this.users = users;
	} 
	
	
	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		System.out.println("Broker URL: "+requestContext.getUriInfo().getPath());
	    Message message = JAXRSUtils.getCurrentMessage();
		
		SecurityContext sc = message.get(SecurityContext.class);
		if (sc != null) {
		    Principal principal  = sc.getUserPrincipal();
		    if (principal != null && users.containsKey(principal.getName())) {
			    return;
		    }
		}
		
		List<String> authValues = headers.getRequestHeader("Authorization");
		if (authValues == null || authValues.size() != 1) {
			requestContext.abortWith(createFaultResponse());
			return;
		}
		System.out.println("Broker authValues:"+authValues);
		String[] values = authValues.get(0).split(" ");
		if (values.length != 2 && (!"Basic".equals(values[0]) || !"Bearer".equals(values[0]))) {
			requestContext.abortWith(createFaultResponse());
			return;
		}
		
		
		final String decodedValue;
		try {
			decodedValue = new String(Base64Utility.decode(values[1]));
		} catch (Base64Exception ex) {
			requestContext.abortWith(createFaultResponse());
			return;
		}
		
		final SecurityContext newSc = new SecurityContext() {

			public Principal getUserPrincipal() {
				return new SimplePrincipal(decodedValue);
			}

			public boolean isUserInRole(String arg0) {
				return false;
			}
			
		};
		message.put(SecurityContext.class, newSc);
	}

	private Response createFaultResponse() {
		return Response.status(401).header("WWW-Authenticate", "Basic realm=\"" + getRealm() + "\"").build();
	}


	public String getRealm() {
		return realm;
	}


	public void setRealm(String realm) {
		this.realm = realm;
	}
}
