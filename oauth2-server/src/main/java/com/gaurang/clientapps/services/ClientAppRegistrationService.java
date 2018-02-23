package com.gaurang.clientapps.services;

import com.gaurang.oauth2.OAuthDataProviderImpl;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.cxf.rs.security.oauth2.common.Client;

@Path("/")
public class ClientAppRegistrationService {
	
	private OAuthDataProviderImpl dataProvider;
	
	public void setDataProvider(OAuthDataProviderImpl dataProvider) {
		this.dataProvider = dataProvider;
	}

	
	/*@GET
	@POST
    @Path("/{input}")
    @Produces("text/html")
    public String displayValues(@PathParam("input") String input,@QueryParam("code") String code) {
    	System.out.println("displayValues API parameters:"+input);
    	System.out.println("displayValues API code parameters:"+code);
        return code;
    }*/
	
	@GET
    @Path("/client/{clientId}")
    @Produces("application/json")
	public Client getClient(@PathParam("clientId")String clientId){
		return dataProvider.getClient(clientId);
	}
	
	@GET
    @Path("/client")
    @Produces("application/json")
	public List<Client> getClients(){
		return dataProvider.getClients(null);
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/registerClientApp")
	public void registerClientApps(MultipartBody body) {
		
		String appName = body.getAttachmentObject("appName", String.class);
	    String appURI = body.getAttachmentObject("appURI", String.class);
	    String appRedirectURI = body.getAttachmentObject("appRedirectURI", String.class);
	    String appDesc = body.getAttachmentObject("appDescription", String.class);
		
	    registerClientApp(appName, appURI, appRedirectURI, appDesc);
		
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/registerClientApp")
	public void registerForm(@FormParam("appName") String appName,@FormParam("appDesc") String appDesc,
			@FormParam("appURI") String appURI,
			@FormParam("appRedirectURI") String appRedirectURI) {
	    
		registerClientApp(appName, appURI, appRedirectURI, appDesc);
	}
	
	
	private void registerClientApp(String appName,String appURI,String appRedirectURI,String appDesc) {
	    String clientSecret = appName;//UUID.randomUUID().toString().replaceAll("-", "");
	    
		Client client = new Client();
		client.setClientId(appName);
		client.setClientSecret(clientSecret);
		client.setConfidential(true);
		client.setApplicationName(appName);
		client.setApplicationDescription(appDesc);
		
		client.setApplicationWebUri(appURI);
		client.setRedirectUris(Collections.singletonList(appRedirectURI));
		
		
		dataProvider.setClient(client);
	}
}
