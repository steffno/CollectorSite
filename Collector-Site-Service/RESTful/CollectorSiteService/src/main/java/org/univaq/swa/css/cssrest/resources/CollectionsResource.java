package org.univaq.swa.css.cssrest.resources;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import org.univaq.swa.css.cssrest.security.BearerAccess;


/**
 *
 * @author MastroGibbs, Kama, Caprielos
 */


@Path("collections")
public class CollectionsResource 
{

    
    /**
     * @param request
     * @param type
     * @return 
     */
    @BearerAccess
    @Path("{collectiontype: (mycollection|sharedcollection|publiccollection)}")
    public Object switchPath(@Context HttpServletRequest request,
            @PathParam("collectiontype") String type)
    {
        
        switch (type) {
            case "mycollection" -> {
                return new PrivateCollectionResource();
            }
            case "sharedcollection" -> {
                return new SharedCollectionResource();
            }
            case "publiccollection" -> {
                return new PublicCollectionResource();
            }
        }
            
        return Response.status(404, "Wrong path").build();
    }
    
    
    @Path("{id: [0-9]+}")
    public RecordResource switchPath(
            @PathParam("id") Integer id
    ) {
        
        return new RecordResource(id);
    }
        
    
}