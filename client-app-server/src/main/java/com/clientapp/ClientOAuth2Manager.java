package com.clientapp;

import java.net.URI;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.rs.security.oauth2.client.Consumer;
import org.apache.cxf.rs.security.oauth2.client.OAuthClientUtils;
import org.apache.cxf.rs.security.oauth2.common.ClientAccessToken;
import org.apache.cxf.rs.security.oauth2.grants.code.AuthorizationCodeGrant;
import org.apache.cxf.rs.security.oauth2.grants.refresh.RefreshTokenGrant;
import org.apache.cxf.rs.security.oauth2.provider.OAuthServiceException;

public class ClientOAuth2Manager {

	private WebClient accessTokenService;
    private String authorizationServiceURI;    
    private Consumer consumer = new Consumer("ClientApp", "ClientApp");
	private ClientAccessToken clientAccessToken;
	public ClientOAuth2Manager() { }
	
	
	public URI getAuthorizationServiceURI(URI redirectUri, String reservationRequestKey) {
		String scope = "apiAccess"+"+refreshToken";
		return OAuthClientUtils.getAuthorizationURI(authorizationServiceURI, consumer.getClientId(), 
					redirectUri.toString(),reservationRequestKey, scope);
	}

	public ClientAccessToken getAccessToken(AuthorizationCodeGrant codeGrant) {
		try {
			clientAccessToken = OAuthClientUtils.getAccessToken(accessTokenService, consumer, codeGrant); 
			return clientAccessToken;
		} catch (OAuthServiceException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public ClientAccessToken getRefreshedAccessToken() {
		try {
			if(clientAccessToken == null) throw new OAuthServiceException("Access token has not received.");
			clientAccessToken = OAuthClientUtils.refreshAccessToken(accessTokenService,consumer,clientAccessToken);
			return clientAccessToken;
		} catch (OAuthServiceException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public String createAuthorizationHeader(ClientAccessToken token) {
		return OAuthClientUtils.createAuthorizationHeader(token);
	}

	public void setAccessTokenService(WebClient accessTokenService) {
		WebClient.getConfig(accessTokenService).getHttpConduit().getClient().setReceiveTimeout(1000000);
		this.accessTokenService = accessTokenService;
	}

	public void setAuthorizationServiceURI(String authorizationServiceURI) {
		this.authorizationServiceURI = authorizationServiceURI;
	}
	
	

}
