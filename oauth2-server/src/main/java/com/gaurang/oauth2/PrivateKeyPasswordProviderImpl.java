package com.gaurang.oauth2;

import java.util.Properties;

import org.apache.cxf.rs.security.jose.common.PrivateKeyPasswordProvider;

public class PrivateKeyPasswordProviderImpl implements PrivateKeyPasswordProvider {

    private String password = "password";

    @Override
    public char[] getPassword(Properties props) {
        return password.toCharArray();
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
