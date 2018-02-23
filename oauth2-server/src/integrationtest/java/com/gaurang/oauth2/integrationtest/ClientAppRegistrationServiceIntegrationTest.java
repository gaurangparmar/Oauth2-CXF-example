package com.gaurang.oauth2.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Form;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.cxf.rs.security.oauth2.common.Client;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.apache.cxf.transport.local.LocalConduit;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import com.gaurang.clientapps.services.ClientAppRegistrationService;


public class ClientAppRegistrationServiceIntegrationTest extends BaseIntegration {
	
	
	@Test
	void registerClient(){
		final JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();
		WebClient client = WebClient.create(ENDPOINT_ADDRESS,Collections.singletonList(jacksonJaxbJsonProvider));
		client.type(MediaType.APPLICATION_FORM_URLENCODED);//.accept(MediaType.APPLICATION_XML);
		client.path("registerClientApp");
		client.authorization("Basic Z2F1cmFuZzpnYXVyYW5n");
		Form registrationForm = new Form();
		registrationForm.param("appName","ClientApp");
		registrationForm.param("appDesc","ClientApp");
		registrationForm.param("appURI","test");
		registrationForm.param("appRedirectURI","test");
		Response response = client.post(registrationForm);
		assertEquals(204,response.getStatus());
	}
	
	@Test
	void getClients(){
		final JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();
		WebClient client = WebClient.create(ENDPOINT_ADDRESS,Collections.singletonList(jacksonJaxbJsonProvider));
		client.accept("application/json");
		client.path("client");
		client.authorization("Basic Z2F1cmFuZzpnYXVyYW5n");
		List<Client> clientList = client.get(ArrayList.class);
		assertEquals(false,clientList.isEmpty());
	}

	@Test
	void getClientDetail(){
		final JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();
		WebClient client = WebClient.create(ENDPOINT_ADDRESS,Collections.singletonList(jacksonJaxbJsonProvider));
		client.accept("application/json");
		client.path("client/ClientApp");		
		client.authorization("Basic Z2F1cmFuZzpnYXVyYW5n");
		Client clientApp = client.get(Client.class);
		assertEquals(false,clientApp==null);
	}

}
