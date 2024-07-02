package org.univaq.swa.css.cssrest.resources;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

/**
 *
 * @author MastroGibbs
 */
public class PublicCollectionResource {
    
    
    @Path("{id: [0-9]+}")
    public PublicRecordsResource switchPath(
            @PathParam("id") Integer id
    ) {
        
        return new PublicRecordsResource(id);
    }
    
    
    @Path("records")
    public PublicRecordsResource switchPath2() 
    {
        
        return new PublicRecordsResource();
    }
}
