package org.univaq.swa.css.cssrest.resources;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.ArrayList;
import org.univaq.swa.css.cssrest.RESTWebApplicationException;
import org.univaq.swa.css.cssrest.data.DummyAuthors;
import org.univaq.swa.css.cssrest.model.Author;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author MastroGibbs
 */

@Path("authors")
public class AuthorsResource 
{
    /**
     *  [8. Elenco di tutti gli autori presenti nel sistema]
     * 
     * @param uriinfo
     * @param from
     * @param to
     * @return 
     * @throws RESTWebApplicationException 
     */
    @GET
    @Produces("application/json")
    public Response getAuthors(
            @Context UriInfo uriinfo,
            @QueryParam("from") Integer from,
            @QueryParam("to") Integer to) throws RESTWebApplicationException {
        
        
        if (from == null) 
            from = 1;
        
        if (to == null) 
            to = 10; 
        
        if (from > to) { from = 1; to = 10; } 
        
        ArrayList<Map<String, Object>> res = new ArrayList<>();
        
        List<Author> authors = DummyAuthors.getGeneratedAuthors();
        
        int lfrom = from;
        int count = 0;
        for (Author au : authors)
        {
            if (lfrom > authors.size()) break;
            if (lfrom > ++count ) continue;
                
            Map<String, Object> m = new HashMap<>();
            m.put("name", au.getName());
            m.put("recordNo", au.getRecordNo());
            m.put("isband", au.isBand());
            m.put("bio", au.getBio());
            m.put("genreType", au.getGtype());
            res.add(m);
            
            if (lfrom++ == to) break;
        }
        
        if (res.isEmpty()) 
             return Response.status(404, "No authors found").build();
        
        return Response.ok(res).build();
    }
}
