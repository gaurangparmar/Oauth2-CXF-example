package ca.sgicanada.oauth2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.cxf.rs.security.oauth2.common.Client;
import org.apache.cxf.rs.security.oauth2.common.UserSubject;
import org.apache.cxf.rs.security.oauth2.grants.code.DefaultEHCacheCodeDataProvider;
import org.apache.cxf.rs.security.oauth2.provider.OAuthServiceException;
import org.apache.cxf.rs.security.oauth2.utils.OAuthConstants;

public class SGIOAuthDataProviderImpl extends DefaultEHCacheCodeDataProvider {
	private static final Set<String> NON_REDIRECTION_FLOWS = new HashSet<>(Arrays.asList(OAuthConstants.CLIENT_CREDENTIALS_GRANT, OAuthConstants.RESOURCE_OWNER_GRANT));

	@Override
	protected void checkRequestedScopes(Client client, List<String> requestedScopes) {
		String grantType = super.getCurrentRequestedGrantType();
		if (grantType != null && !NON_REDIRECTION_FLOWS.contains(grantType)
				&& !requestedScopes.contains("apiAccess")) {
			throw new OAuthServiceException("Required scopes are missing");
		}
	}
	
	/*@Override
	public Client getClient(String clientId) {
		
		UserSubject userSubject = new UserSubject();
		userSubject.setId(clientId);
		
		List<String> uris = new ArrayList<>();
		uris.add("http://localhost:8080/api/");
		uris.add("https://www.google.ca/?q=\"");
		
		Client registeredClient = new Client();
		registeredClient.setClientId(clientId);
		registeredClient.setClientSecret(clientId);
		registeredClient.setSubject(userSubject);
		registeredClient.setRedirectUris(uris);
		registeredClient.setConfidential(true);
		registeredClient.setApplicationName("Broker Application");
		registeredClient.setApplicationWebUri("http://broker.com");
		
		return registeredClient;
	}*/
}
