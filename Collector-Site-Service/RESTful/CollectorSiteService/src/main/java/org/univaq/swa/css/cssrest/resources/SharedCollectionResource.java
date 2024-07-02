package org.univaq.swa.css.cssrest.resources;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.univaq.swa.css.cssrest.RESTWebApplicationException;
import org.univaq.swa.css.cssrest.data.DummyCollections;
import org.univaq.swa.css.cssrest.model.Collection;
import org.univaq.swa.css.cssrest.model.CollectionType;
import org.univaq.swa.css.cssrest.model.User;
import org.univaq.swa.css.cssrest.security.BearerAccess;

/**
 *
 * @author MastroGibbs, Kama, Caprielos
 */
public class SharedCollectionResource {
    
    /**
     *  [3.Elenco collezioni condivise con un utente]
     * 
     * @param request
     * @param cid
     * @param from
     * @param to
     * @return 
     */
    @BearerAccess
    @Path("{id: [0-9]+}")
    @GET
    @Produces("application/json")
    public Response getSharedCollections(@Context HttpServletRequest request,
            @PathParam("id") Integer cid,
            @QueryParam("from") Integer from,
            @QueryParam("to") Integer to) 
            throws RESTWebApplicationException
    {
        
        if (from == null) 
            from = 1;
        
        if (to == null) 
            to = 10; 
        
        if (from > to) { from = 1; to = 10; } 
        
        String token = (String) request.getAttribute("token");
        User u = UserByToken.getUserByToken(token);
        
        if (u != null)
        {
            Map<Integer, List<Collection>> data = DummyCollections.getGeneratedCollections();
            List<Collection> l = data.get(u.getId()); 

            List<Map<String, Object>> res = new ArrayList<>();

            int lfrom = from;
            int count = 0;
            for (Collection c : l)
            {
                if (c.getType().equals(CollectionType.Shared.toString()) && c.getShared().contains(cid))
                {
                    if (lfrom > l.size()) break;
                    if (lfrom > ++count ) continue;

                    Map<String, Object> m = new HashMap<>();
                    m.put("id", c.getId());
                    m.put("name", c.getName());
                    m.put("recordCount", c.getRecordCount());
                    m.put("type", c.getType());
                    res.add(m);

                    if (lfrom++ == to) break;
                }
                
            }

            return Response.ok(res).build();
        }
        else return Response.status(404, "No collection found").build();
    }
    
    
    @Path("records")
    public SharedRecordsResource switchPath() 
    {
        return new SharedRecordsResource();
    }
    
    
}
