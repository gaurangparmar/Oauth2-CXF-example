package com.clientapp;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;
import org.apache.cxf.rs.security.oauth2.client.ClientTokenContext;
import org.apache.cxf.rs.security.oauth2.client.ClientTokenContextProvider;
import org.apache.cxf.rs.security.oauth2.common.ClientAccessToken;
 
public class CustomClientTokenContextProvider extends ClientTokenContextProvider implements ContextProvider<ClientTokenContext> {
	
    @Override
    public ClientTokenContext createContext(Message m) {
    	return new WrapClientTokenContext(super.createContext(m));        
    }
    
    private static class WrapClientTokenContext implements ClientTokenContext {
        private ClientTokenContext ctx;
        public WrapClientTokenContext(ClientTokenContext ctx) { 
            this.ctx = ctx;
        }
        @Override
        public MultivaluedMap<String, String> getState() {
            return ctx.getState();
        }
        @Override
        public <T> T getState(Class<T> cls) {           
            return ctx.getState(cls);
        }
        @Override
        public ClientAccessToken getToken() {
            return ctx.getToken();
        }
    }
}
