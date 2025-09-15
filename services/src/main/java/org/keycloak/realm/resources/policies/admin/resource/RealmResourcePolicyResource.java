package org.keycloak.realm.resources.policies.admin.resource;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.keycloak.models.ModelException;
import org.keycloak.models.policy.ResourcePolicy;
import org.keycloak.models.policy.ResourcePolicyManager;
import org.keycloak.models.policy.ResourceType;
import org.keycloak.representations.resources.policies.ResourcePolicyRepresentation;
import org.keycloak.services.ErrorResponse;

class RealmResourcePolicyResource {

    private final ResourcePolicyManager manager;
    private final ResourcePolicy policy;

    public RealmResourcePolicyResource(ResourcePolicyManager manager, ResourcePolicy policy) {
        this.manager = manager;
        this.policy = policy;
    }

    @DELETE
    public void delete() {
        try {
            manager.removePolicy(policy.getId());
        } catch (ModelException me) {
            throw ErrorResponse.error(me.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    @PUT
    public void update(ResourcePolicyRepresentation rep) {
        try {
            manager.updatePolicy(policy, rep.getConfig());
        } catch (ModelException me) {
            throw ErrorResponse.error(me.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    @GET
    @Produces(APPLICATION_JSON)
    public ResourcePolicyRepresentation toRepresentation() {
        return manager.toRepresentation(policy);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("bind/{type}/{resourceId}")
    public void bind(@PathParam("type") ResourceType type, @PathParam("resourceId") String resourceId, Long notBefore) {
        Object resource = manager.resolveResource(type, resourceId);

        if (resource == null) {
            throw new BadRequestException("Resource with id " + resourceId + " not found");
        }

        if (notBefore != null) {
            policy.setNotBefore(notBefore);
        }

        manager.bind(policy, type, resourceId);
    }
}
