package org.keycloak.models.workflow;

import java.util.List;

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import static org.keycloak.models.workflow.Step.AFTER_KEY;

public class AddRequiredStepProvider implements WorkflowStepProvider {

    public static String REQUIRED_ACTION_KEY = "action";

    private final KeycloakSession session;
    private final ComponentModel stepModel;
    private final Logger log = Logger.getLogger(AddRequiredStepProvider.class);

    public AddRequiredStepProvider(KeycloakSession session, ComponentModel model) {
        this.session = session;
        this.stepModel = model;
    }

    @Override
    public void run(List<String> userIds) {
        RealmModel realm = session.getContext().getRealm();

        for (String id : userIds) {
            UserModel user = session.users().getUserById(realm, id);

            if (user != null) {
                try {
                    UserModel.RequiredAction action = UserModel.RequiredAction.valueOf(stepModel.getConfig().getFirst(REQUIRED_ACTION_KEY));
                    log.debugv("Adding required action {0} to user {1})", action, user.getId());
                    user.addRequiredAction(action);
                } catch (IllegalArgumentException e) {
                    log.warnv("Invalid required action {0} configured in AddRequiredActionProvider", stepModel.getConfig().getFirst(REQUIRED_ACTION_KEY));
                }
            }
        }
    }

    @Override
    public boolean isRunnable() {
        return stepModel.get(AFTER_KEY) != null;
    }

    @Override
    public void close() {
    }
}
