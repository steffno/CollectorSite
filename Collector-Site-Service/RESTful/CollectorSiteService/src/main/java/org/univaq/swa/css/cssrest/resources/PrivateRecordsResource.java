package org.univaq.swa.css.cssrest.resources;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
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
import org.univaq.swa.css.cssrest.model.Record;
import org.univaq.swa.css.cssrest.security.BearerAccess;

/**
 *
 * @author MastroGibbs, Kama, Caprielos
 */
public class PrivateRecordsResource {
 
    private Integer id;
    
    public PrivateRecordsResource()
    {
        this.id = null;
    }
    
    public PrivateRecordsResource(int _id)
    {
        this.id = _id;
    }
    
    /**
     * [7.3.Ricerca di un disco tra le collezioni private]
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

            Set<Record> allrec = new HashSet<>();

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
    
    
    /**
     * [4. Elenco dei dischi in una collezione ]
     * 
     * @param request
     * @param cid
     * @param from
     * @param to
     * @return 
     */
    @Path("records")
    @BearerAccess
    @GET
    @Produces("application/json")
    public Response getCollectionRecords(@Context HttpServletRequest request,
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

            Set<Map<String, Object>> res = new HashSet<>();

            int lfrom = from;
            int count = 0;
            for (Collection c : l)
            {
                if (this.id == c.getId())
                {
                    for (Record r: c.getRecords())
                    {
                        if (lfrom > c.getRecords().size()) break;
                        if (lfrom > ++count ) continue;

                        Map<String, Object> m = new HashMap<>();
                        m.put("id", r.getId());
                        m.put("title", r.getTitle());
                        m.put("trackCount", r.getTrackCount());
                        m.put("author", r.getAuthor());
                        m.put("date", r.getDate());
                        m.put("length", r.getLength());
                        m.put("tracks", r.getTracks());
                        res.add(m);

                        if (lfrom++ == to) break;
                    }
                    break;
                }
            }

            return Response.ok(res).build();
        }
        else return Response.status(404, "No record found").build();
    }
    
     
    
    /**
     * [5. Dettagli singolo disco contenuto in una collezione]
     * 
     * @param request
     * @param cid
     * @return 
     */
    @BearerAccess
    @Path("records/{id: [0-9]+}")
    @GET
    @Produces("application/json")
    public Response getCollectionRecordInfo(@Context HttpServletRequest request,
            @PathParam("id") Integer cid) 
            throws RESTWebApplicationException
    {
               
        String token = (String) request.getAttribute("token");
        User u = UserByToken.getUserByToken(token);
        
        if (u != null)
        {
            Map<Integer, List<Collection>> data = DummyCollections.getGeneratedCollections();
            List<Collection> l = data.get(u.getId());

            Map<String, Object> res = new HashMap<>();

            for (Collection c : l)
                if (this.id == c.getId())
                    for (Record r: c.getRecords())
                        if (r.getId() == cid)
                        {
                            res.put("id", r.getId());
                            res.put("title", r.getTitle());
                            res.put("trackCount", r.getTrackCount());
                            res.put("author", r.getAuthor());
                            res.put("date", r.getDate());
                            res.put("length", r.getLength());
                            res.put("tracks", r.getTracks());
                            return Response.ok(res).build();
                        }
            return Response.status(404, "No record found").build();
        }
        else return Response.status(404, "No record found").build();
    }
    
    
    /**
     * [9. Elenco di tutti i dischi di un certo autore presenti nelle collezioni pubbliche]
     * 
     * @param request
     * @param auName
     * @param from
     * @param to
     * @return 
     */
    @Path("{name: [a-zA-Z0-9]+}")
    @GET
    @Produces("application/json")
    public Response getAuthorRecords(@Context HttpServletRequest request,
            @PathParam("name") String auName,
            @QueryParam("from") Integer from,
            @QueryParam("to") Integer to) 
            throws RESTWebApplicationException
    {
        
        if (from == null) 
            from = 1;
        
        if (to == null) 
            to = 10; 
        
        if (from > to) { from = 1; to = 10; } 
        
        
            Map<Integer, List<Collection>> data = DummyCollections.getGeneratedCollections();
            
            Set<Record> res = new HashSet<>();

            for (Map.Entry<Integer, List<Collection>> entryset : data.entrySet())
                for (Collection c : entryset.getValue())
                    if (c.getType().equals(CollectionType.Public.toString()))  // cerco solo in collezioni pubbliche
                        for (Record r : c.getRecords())
                            if (r.getAuthor().getName().equals(auName))
                                res.add(r);

        if (res.isEmpty())
            return Response.status(404, "No Records found").build();
        else 
            return Response.ok(res).build();
        
    }
}
