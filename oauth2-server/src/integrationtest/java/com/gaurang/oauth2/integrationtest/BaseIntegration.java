package com.gaurang.oauth2.integrationtest;

import javax.ws.rs.core.Response;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class BaseIntegration {

	public final static String ENDPOINT_ADDRESS = "http://localhost:8081/";	
	public final static String WADL_ADDRESS = ENDPOINT_ADDRESS + "?_wadl";
	private static Server server;
	
	@BeforeAll
	public static void initialize() throws Exception {
	     startServer();
	     waitForWADL();
	}
	 
	protected static void startServer() throws Exception {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"beans.xml"});
		JAXRSServerFactoryBean sf = (JAXRSServerFactoryBean) ctx.getBean("oauthServer");
		/*sf.setResourceClasses(restResource);
		List<Object> providers = new ArrayList<Object>();
		// add custom providers if any
		sf.setProviders(providers);
		sf.setResourceProvider(restResource, new SingletonResourceProvider(restResource.newInstance(), true));*/
		sf.setAddress(ENDPOINT_ADDRESS);
		server = sf.create();
	}
	 
	// Optional step - may be needed to ensure that by the time individual
	// tests start running the endpoint has been fully initialized
	private static void waitForWADL() throws Exception {
	    WebClient client = WebClient.create(WADL_ADDRESS);
	    // wait for 20 secs or so
	    for (int i = 0; i < 20; i++) {
	        Thread.currentThread().sleep(1000);
	        Response response = client.get();
	        if (response.getStatus() == 200) {
	            break;
	        }
	    }
	    // no WADL is available yet - throw an exception or give tests a chance to run anyway
	}
	
	@AfterAll
	public static void destroy() throws Exception {
	   server.stop();
	   server.destroy();
	}

}
