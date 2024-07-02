package org.univaq.swa.css.cssrest.resources;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.univaq.swa.css.cssrest.RESTWebApplicationException;
import org.univaq.swa.css.cssrest.data.DummyCollections;
import org.univaq.swa.css.cssrest.model.Collection;
import org.univaq.swa.css.cssrest.model.CollectionType;
import org.univaq.swa.css.cssrest.model.User;
import org.univaq.swa.css.cssrest.security.BearerAccess;

/**
 *
 * @author MastroGibbs
 */
public class SharedRecordsResource {
    
    
    /**
     * [7.2.Ricerca di un disco tra le collezioni condivise]
     * 
     * @param request
     * @param recordName
     * @param date
     * @param authorName
     * @return 
     */
    @BearerAccess
    @GET
    @Produces("application/json")
    public Response getRecord(@Context HttpServletRequest request,
            @QueryParam("nameQParam") String recordName,
            @QueryParam("dateQParam") String date,
            @QueryParam("authorQParam") String authorName)
            throws RESTWebApplicationException
    {
        if (recordName == null && date == null && authorName == null)
            throw new RESTWebApplicationException("No params passed");
        
        String token = (String) request.getAttribute("token");
        User u = UserByToken.getUserByToken(token);
        
        if (u != null)
        {
            Map<Integer, List<Collection>> data = DummyCollections.getGeneratedCollections();

            Set<org.univaq.swa.css.cssrest.model.Record> allrec = new HashSet<>();

            for (Map.Entry<Integer, List<Collection>> entryset : data.entrySet())
                for (Collection c : entryset.getValue())
                    if (c.getType().equals(CollectionType.Public.toString()))
                        for (org.univaq.swa.css.cssrest.model.Record r: c.getRecords())
                            allrec.add(r);


            Iterator<org.univaq.swa.css.cssrest.model.Record> itr = allrec.iterator();

            if (recordName != null)
                while (itr.hasNext())
                {
                    org.univaq.swa.css.cssrest.model.Record curr = itr.next();
                    if (!curr.getTitle().equalsIgnoreCase(recordName))
                        itr.remove();
                }


            itr = allrec.iterator();

            if (date != null)
                while (itr.hasNext())
                {
                    org.univaq.swa.css.cssrest.model.Record curr = itr.next();
                    if (!curr.getDate().toString().equalsIgnoreCase(recordName))
                        itr.remove();
                }

            itr = allrec.iterator();

            if (authorName != null)
                while (itr.hasNext())
                {
                    org.univaq.swa.css.cssrest.model.Record curr = itr.next();
                    if (!curr.getAuthor().getName().equalsIgnoreCase(authorName))
                        itr.remove();
                }       
            return Response.ok(allrec).build();
        }
        return Response.status(404, "No record found").build();
    }
}
