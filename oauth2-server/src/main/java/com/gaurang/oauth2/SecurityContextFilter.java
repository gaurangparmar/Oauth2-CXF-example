package ca.sgicanada.oauth2;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.cxf.common.security.SimplePrincipal;
import org.apache.cxf.common.util.Base64Exception;
import org.apache.cxf.common.util.Base64Utility;
import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.rs.security.oauth2.common.Client;
import org.apache.cxf.security.SecurityContext;

import ca.sgicanada.clientapps.services.ClientAppRegistrationService;
import ca.sgicanada.user.data.User;
import ca.sgicanada.user.services.UserRegistrationService;

public class SecurityContextFilter implements ContainerRequestFilter {

	@Context
	private HttpHeaders headers;
	private UserRegistrationService userRegistrationService;	
	private ClientAppRegistrationService clientAppRegistrationService;
	private Map<String,String> userAccounts;
	private List<String> noCheckURLS;

	public void setUserRegistrationService(UserRegistrationService userRegistrationService) {
		this.userRegistrationService = userRegistrationService;
	}
	
	public void setClientAppRegistrationService(ClientAppRegistrationService clientAppRegistrationService) {
		this.clientAppRegistrationService = clientAppRegistrationService;
	}
	
	public void setUserAccounts(Map<String,String> userAccounts) {
		this.userAccounts = userAccounts;
	}
	
	public void setNoCheckURLS(List<String> noCheckURLS) {
		this.noCheckURLS = noCheckURLS;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		System.out.println("Oauth2 URL: "+requestContext.getUriInfo().getPath());
		Message message = JAXRSUtils.getCurrentMessage();
		String account = null;
		SecurityContext sc = message.get(SecurityContext.class);
		
		if(noCheckURLS.contains(requestContext.getUriInfo().getPath())) {
			setNewSecurityContext(message, "valid");
			return;
		}
		
		
		if (sc != null) {
			Principal principal = sc.getUserPrincipal();
			if (principal != null) {
				String accountName = principal.getName();
				System.out.println("accountName:"+accountName);
				account = userAccounts.get(accountName);				
				if (account == null && !checkAuthenticity(accountName,null)) {					
					requestContext.abortWith(createFaultResponse());
				} else {
					setNewSecurityContext(message, accountName);
				}
				return;
			}
		}

		List<String> authValues = headers.getRequestHeader("Authorization");
		if (authValues == null || authValues.size() != 1) {
			requestContext.abortWith(createFaultResponse());
			return;
		}
		System.out.println("Oauth2 authValues:"+authValues);
		String[] values = authValues.get(0).split(" ");
		if (values.length != 2) {
			requestContext.abortWith(createFaultResponse());
			return;
		} else if("Bearer".equals(values[0])) {
			return;
		} else if(!"Basic".equals(values[0])) {
			requestContext.abortWith(createFaultResponse());
			return;
		}

		String decodedValue = null;
		try {
			decodedValue = new String(Base64Utility.decode(values[1]));
		} catch (Base64Exception ex) {
			requestContext.abortWith(createFaultResponse());
			return;
		}
		String[] namePassword = decodedValue.split(":");
		if (namePassword.length != 2) {
			requestContext.abortWith(createFaultResponse());
			return;
		}
		
		if (!checkAuthenticity(namePassword[0],namePassword[1])) {
			requestContext.abortWith(createFaultResponse());
			return;
		}else {
			account = namePassword[0];
		}

		setNewSecurityContext(message, account);
	}

	private boolean checkAuthenticity(String userId,String password) {
		User user = userRegistrationService.getUser(userId);
		Client client = clientAppRegistrationService.getClient(userId);
		if ((user==null && client == null)) {
			return false;
		} else if(password!=null && (user!=null&&!user.getPassword().equals(password)) || (client!=null&&!client.getClientSecret().equals(password))) {
			return false;
		} else {
			return true;
		}
	}
	
	private void setNewSecurityContext(Message message, final String user) {
		final SecurityContext newSc = new SecurityContext() {

			public Principal getUserPrincipal() {
				return new SimplePrincipal(user);
			}

			public boolean isUserInRole(String arg0) {
				return false;
			}

		};
		message.put(SecurityContext.class, newSc);
	}

	private Response createFaultResponse() {
		return Response.status(401).header("WWW-Authenticate", "Basic realm=\"oauth2.sgicanada.com\"").build();
	}

}
