package org.keycloak.testframework.https;

import org.keycloak.common.crypto.CryptoProvider;
import org.keycloak.testframework.injection.InstanceContext;
import org.keycloak.testframework.injection.LifeCycle;
import org.keycloak.testframework.injection.RequestedInstance;
import org.keycloak.testframework.injection.Supplier;
import org.keycloak.testframework.injection.SupplierOrder;

import java.util.ServiceLoader;

public class TlsSupplier  implements Supplier<ManagedTls, InjectTls> {

    @Override
    public ManagedTls getValue(InstanceContext<ManagedTls, InjectTls> instanceContext) {
        ServiceLoader<CryptoProvider> serviceLoaded = ServiceLoader.load(CryptoProvider.class);
        CryptoProvider cryptoProvider = serviceLoaded.iterator().next(); // TODO suboptimal
        return new ManagedTls(cryptoProvider);
    }

    @Override
    public LifeCycle getDefaultLifecycle() {
        return LifeCycle.GLOBAL;
    }

    @Override
    public boolean compatible(InstanceContext<ManagedTls, InjectTls> a, RequestedInstance<ManagedTls, InjectTls> b) {
        return true;
    }

    @Override
    public void close(InstanceContext<ManagedTls, InjectTls> instanceContext) {
        // todo KeyStore is going to be stored somewhere, so it should be deleted
    }

    @Override
    public int order() {
        return SupplierOrder.BEFORE_KEYCLOAK_SERVER;
    }
}
