package org.keycloak.testframework.https;

import org.keycloak.testframework.injection.InstanceContext;
import org.keycloak.testframework.injection.LifeCycle;
import org.keycloak.testframework.injection.RequestedInstance;
import org.keycloak.testframework.injection.Supplier;
import org.keycloak.testframework.injection.SupplierOrder;

public class TlsSupplier  implements Supplier<ManagedTls, InjectTls> {

    @Override
    public ManagedTls getValue(InstanceContext<ManagedTls, InjectTls> instanceContext) {
        return new ManagedTls();
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
        // todo Do I need to let others know the certs are going to die, or is it managed with dependencies?
    }

    @Override
    public int order() {
        return SupplierOrder.BEFORE_KEYCLOAK_SERVER;
    }
}
