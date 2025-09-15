package org.keycloak.testframework.https;

import org.keycloak.common.crypto.CryptoProvider;
import org.keycloak.common.util.KeystoreUtil;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchProviderException;

public class ManagedTls {

    private final CryptoProvider cryptoProvider;

    public ManagedTls(CryptoProvider cryptoProvider) {
        this.cryptoProvider = cryptoProvider;
    }

    // TODO

    /*
    - createKeycloakServerCert()
    - getTruststoreWithKeycloakServerCert()
    - createClientCert
     */

    public void createKeyStore() throws KeyStoreException, NoSuchProviderException {
        KeyStore keyStore = cryptoProvider.getKeyStore(KeystoreUtil.KeystoreFormat.JKS);
    }

    // TODO example; get rid of it
    public String getKeyStore() {
        return "/server.keystore";
    }
}
