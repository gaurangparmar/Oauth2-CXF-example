package com.clientapp;
import java.net.URI;
import java.util.Calendar;

import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.rs.security.oauth2.client.ClientTokenContext;
import org.apache.cxf.rs.security.oauth2.client.Consumer;
import org.apache.cxf.rs.security.oauth2.client.OAuthClientUtils;
import org.apache.cxf.rs.security.oauth2.common.ClientAccessToken;
import org.apache.cxf.rs.security.oauth2.grants.code.AuthorizationCodeGrant;
import org.apache.cxf.rs.security.oauth2.grants.refresh.RefreshTokenGrant;

@Path("/")
public class QuoteService {
	
	@Context
	private SecurityContext sc;
	@Context
	private UriInfo ui;

	private WebClient apiService;
	private ClientOAuth2Manager manager;
	
	@GET
    @Path("view-quote")
    @Produces("text/html")
    public Response viewQuoteAuto(@Context ClientTokenContext context) {
		
		
        // Check if token is available
        if (context.getToken() == null) {
           return redirectToFailureHandler("No Code grant is available.");
        } else {
        	System.out.println("Bearer Token:"+context.getToken());
        }
        
        // Prepare Authorization header for apiService
        apiService.authorization(context.getToken());
         
        String quote = apiService.path("/", "test").get(String.class);        
        if (quote == null) {
            return redirectToFailureHandler("Not able to get quote.");
        }
       
        return Response.ok("View Quote:"+quote).build();
    }
	
	@GET
    @Path("handle-redirect")
    @Produces("text/html")
    public Response handleRedirect(@QueryParam("code") String code) {
		System.out.println("code received"+code);
		
		return Response.ok(code).build();
		
	}
	
	/*public Response viewQuote(@QueryParam("code") String code) {
		
		
		AuthorizationCodeGrant codeGrant = new AuthorizationCodeGrant(code, ui.getBaseUriBuilder().path("viewquote").build());
		
		ClientAccessToken accessToken = manager.getAccessToken(codeGrant);
		if (accessToken == null) {
		    return redirectToFailureHandler("No Access Token available.");
		}
		String authHeader = manager.createAuthorizationHeader(accessToken);
		apiService.replaceHeader(HttpHeaders.AUTHORIZATION, authHeader);
		 
		try {
		   return apiService.get();
		} catch (NotAuthorizedException ex) {
		    String refreshToken = accessToken.getRefreshToken();
		    if (refreshToken != null) {
		        // retry once
		 
		        // refresh the token
		        accessToken = manager.getRefreshedAccessToken();
		 
		        authHeader = manager.createAuthorizationHeader(accessToken);
				apiService.replaceHeader(HttpHeaders.AUTHORIZATION, authHeader);
		 
		        // try to access the end user resource again
				return apiService.get();
		         
		    } else {
		        throw ex;
		    }
		 
		}
       
    }*/
	
	
	@GET
	@POST
    @Path("/{input}")
    @Produces("text/html")
    public String displayValues(@PathParam("input") String input,@QueryParam("code") String code) {
    	System.out.println("Quote displayValues API parameters:"+input);
    	System.out.println("Quote displayValues API code parameters:"+code);
        return code;
    }
	
	private Response redirectToFailureHandler(String code) {
	    URI handlerUri = ui.getBaseUriBuilder().path("quote").path("failure").queryParam("code", code).build();
	    return Response.seeOther(handlerUri).build();
	}
	
	public void setManager(ClientOAuth2Manager manager) {
		this.manager = manager;
	}

	public void setApiService(WebClient apiService) {
		this.apiService = apiService;
	}
	
	
}

