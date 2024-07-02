package org.univaq.swa.css.cssrest.resources;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import org.univaq.swa.css.cssrest.RESTWebApplicationException;
import org.univaq.swa.css.cssrest.data.DummyCollections;
import org.univaq.swa.css.cssrest.model.Record;
import org.univaq.swa.css.cssrest.model.User;
import org.univaq.swa.css.cssrest.security.BearerAccess;

/**
 *
 * @author MastroGibbs, Kama, Caprielos
 */
public class RecordResource {
    
    private int id;
    
    public RecordResource(int _id)
    {
        this.id = _id;
    }
    
    
    /**
     * [6.Inserimento di un nuovo disco in una delle collezioni dell’utente]
     * 
     * @param request
     * @param r
     * @return 
     */
    @BearerAccess
    @Path("newrecord")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response addCollectionRecord(@Context HttpServletRequest request, Record r) 
            throws RESTWebApplicationException
    {
        String token = (String) request.getAttribute("token");
        User u = UserByToken.getUserByToken(token);
        
        if (u != null)
        {
            DummyCollections.pushRecordInto(u.getId(), this.id, r);
            return Response.status(Response.Status.CREATED).build();
        }
            
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    
    /**
     * [10. Aggiornamento di un disco in una collezione personale dell’utente]
     * 
     * @param request
     * @param r
     * @return 
     */
    @BearerAccess
    @Path("modifyrecord")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateCollectionRecord(@Context HttpServletRequest request, Record r) 
            throws RESTWebApplicationException
    {
        String token = (String) request.getAttribute("token");
        User u = UserByToken.getUserByToken(token);
        
        if (u != null)
        {
            DummyCollections.updateRecord(u.getId(), this.id, r);
            return Response.status(Response.Status.OK).build();
        }
            
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
