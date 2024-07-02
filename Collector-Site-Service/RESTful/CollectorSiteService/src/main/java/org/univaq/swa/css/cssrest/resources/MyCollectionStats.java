package org.univaq.swa.css.cssrest.resources;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.univaq.swa.css.cssrest.RESTWebApplicationException;
import org.univaq.swa.css.cssrest.data.DummyCollections;
import org.univaq.swa.css.cssrest.model.Collection;
import org.univaq.swa.css.cssrest.model.User;
import org.univaq.swa.css.cssrest.model.Record;
import org.univaq.swa.css.cssrest.model.Author;
import org.univaq.swa.css.cssrest.security.BearerAccess;

/**
 *
 * @author MastroGibbs
 */
public class MyCollectionStats {
    
    
    private int id;
    
    public MyCollectionStats(int _id)
    {
        this.id = _id;
    }
    
    
    /**
     * [11. Estrazione di alcune statistiche]
     * 
     * @param request
     * @return 
     */
    @BearerAccess
    @GET
    @Produces("application/json")
    public Response getCollectionStats(@Context HttpServletRequest request) 
            throws RESTWebApplicationException
    {
               
        String token = (String) request.getAttribute("token");
        User u = UserByToken.getUserByToken(token);
        
        if (u != null)
        {
            Map<Integer, List<Collection>> data = DummyCollections.getGeneratedCollections();
            List<Collection> l = data.get(u.getId());

            Map<String, Object> res = new HashMap<>();

            int recordno = 0;
            
            for (Collection c : l)
                if (this.id == c.getId())
                {
                    Set<Author> aul = new HashSet<>();
                    Map<String, Integer> genrecount = new HashMap<>();
                    
                    for (Record r: c.getRecords())
                    {
                        recordno++;
                        aul.add(r.getAuthor());
                        genrecount.put(r.getAuthor().getGtype(), genrecount.get(r.getAuthor().getGtype()) == null ? 1 : genrecount.get(r.getAuthor().getGtype()) +1);
                    }
                    res.put("authors", aul);
                    res.put("totalRecords", recordno);
                    res.put("genreCount", genrecount);
                    return Response.ok(res).build();
                }
        }
        return Response.status(404, "No record found").build();
    }
}
