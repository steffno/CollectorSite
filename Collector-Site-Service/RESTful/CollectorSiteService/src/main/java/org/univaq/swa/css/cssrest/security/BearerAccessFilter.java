package org.univaq.swa.css.cssrest.security;

import java.io.IOException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;

/**
 *
 * @author didattica
 */
@Provider
@BearerAccess
@Priority(Priorities.AUTHENTICATION)
public class BearerAccessFilter implements ContainerRequestFilter {

    @Context
    UriInfo uriInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {        
        
        String token = null;
        
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring("Bearer".length()).trim();
        }
       
        if (token != null && !token.isEmpty()) {
            try {
                final Integer id = validateToken(token);
                if (id != null) {
                    requestContext.setProperty("token", token);
                    requestContext.setProperty("id", id);
                } else {
                    
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                }
            } catch (Exception e) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        } else {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }

    private Integer validateToken(String token) 
    {
        for(String tk : AuthSystemResource.tokenWarehouse())
            if (tk.equals(token)) 
                return Integer.parseInt(token.substring(36));
        
        return null;
    }
    
   
}