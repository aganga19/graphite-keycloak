package org.keycloak.test.examples;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.keycloak.testframework.annotations.InjectHttpClient;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.https.InjectTls;
import org.keycloak.testframework.https.ManagedTls;
import org.keycloak.testframework.oauth.OAuthClient;
import org.keycloak.testframework.oauth.annotations.InjectOAuthClient;
import org.keycloak.testframework.server.KeycloakServerConfig;
import org.keycloak.testframework.server.KeycloakServerConfigBuilder;

import java.io.IOException;

@KeycloakIntegrationTest(config = TlsEnabledTest.TlsEnabledServerConfig.class)
public class TlsEnabledTest {

    @InjectHttpClient
    HttpClient httpClient;

    @InjectOAuthClient
    OAuthClient oAuthClient;

    @InjectTls
    ManagedTls managedTls;

    @Test
    public void testCertSupplier() {
        Assertions.assertNotNull(managedTls);
        Assertions.assertEquals("/server.keystore", managedTls.getKeyStore());
    }

    @Test
    public void testHttpClient() {
        HttpGet req = new HttpGet("https://localhost:8443");
        try {
            HttpResponse resp = httpClient.execute(req);
            Assertions.assertEquals(200, resp.getStatusLine().getStatusCode());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testOAuthClient() {
        oAuthClient.doWellKnownRequest();
    }


    public static class TlsEnabledServerConfig implements KeycloakServerConfig {

        @Override
        public KeycloakServerConfigBuilder configure(KeycloakServerConfigBuilder config) {
            return config.tlsEnabled(true);
        }
    }
}
