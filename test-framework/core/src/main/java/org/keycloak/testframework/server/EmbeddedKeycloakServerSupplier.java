package org.keycloak.testframework.server;

import org.jboss.logging.Logger;

public class EmbeddedKeycloakServerSupplier extends AbstractKeycloakServerSupplier {

    private static final Logger LOGGER = Logger.getLogger(EmbeddedKeycloakServerSupplier.class);

    @Override
    public KeycloakServer getServer(String serverKeystore) {
        return new EmbeddedKeycloakServer(serverKeystore);
    }

    @Override
    public boolean requiresDatabase() {
        return true;
    }

    @Override
    public String getAlias() {
        return "embedded";
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }
}
