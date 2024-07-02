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
import org.univaq.swa.css.cssrest.model.Record;

/**
 *
 * @author MastroGibbs, Kama, Caprielos
 */
public class PublicRecordsResource {
    
    private Integer id;
    
    public PublicRecordsResource()
    {
        this.id = null;
    }
    
    public PublicRecordsResource(int _id)
    {
        this.id = _id;
    }
    
    
    
    
    /**
     * [7.1.Ricerca di un disco tra quelle pubbliche]
     * 
     * @param request
     * @param recordName
     * @param date
     * @param authorName
     * @return 
     */
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
        
        Map<Integer, List<Collection>> data = DummyCollections.getGeneratedCollections();

        Set<Record> allrec = new HashSet<>();

        for (Map.Entry<Integer, List<Collection>> entryset : data.entrySet())
            for (Collection c : entryset.getValue())
                if (c.getType().equals(CollectionType.Public.toString()))
                    for (Record r: c.getRecords())
                        allrec.add(r);
                        
                        
        Iterator<Record> itr = allrec.iterator();
        
        if (recordName != null)
            while (itr.hasNext())
            {
                Record curr = itr.next();
                if (!curr.getTitle().equalsIgnoreCase(recordName))
                    itr.remove();
            }
        
        
        itr = allrec.iterator();
        
        if (date != null)
            while (itr.hasNext())
            {
                Record curr = itr.next();
                if (!curr.getDate().toString().equalsIgnoreCase(recordName))
                    itr.remove();
            }
        
        itr = allrec.iterator();
        
        if (authorName != null)
            while (itr.hasNext())
            {
                Record curr = itr.next();
                if (!curr.getAuthor().getName().equalsIgnoreCase(authorName))
                    itr.remove();
            }       
        
        if (allrec.isEmpty())
            return Response.status(404, "No record found").build();
        else
            return Response.ok(allrec).build();
    }
    
    /**
     * [4.1.Elenco dei dischi in una collezione ]
     * 
     * @param request
     * @param id
     * @param from
     * @param to
     * @return 
     */
    @Path("records")
    @GET
    @Produces("application/json")
    public Response getCollectionRecords(@Context HttpServletRequest request,
            @PathParam("id") Integer id,
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

        Set<Map<String, Object>> res = new HashSet<>();
        
        
        for (Map.Entry<Integer, List<Collection>> entryset : data.entrySet())
        {
            int lfrom = from;
            int count = 0;
            for (Collection c : entryset.getValue())
            {
                if (this.id == c.getId() && c.getType().equals(CollectionType.Public.toString()))
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
        }

        

        if (res.isEmpty())
            return Response.status(404, "No record found").build();
        else
            return Response.ok(res).build();
        
        
    }
    
    
    
    /**
     * [5.1. Dettagli singolo disco contenuto in una collezione]
     * 
     * @param request
     * @param cid
     * @return 
     */
    @Path("records/{id: [0-9]+}")
    @GET
    @Produces("application/json")
    public Response getCollectionRecordInfo(@Context HttpServletRequest request,
            @PathParam("id") Integer cid) 
            throws RESTWebApplicationException
    {
        Map<Integer, List<Collection>> data = DummyCollections.getGeneratedCollections();

        Map<String, Object> res = new HashMap<>();

        for (Map.Entry<Integer, List<Collection>> entryset : data.entrySet())
        {
            for (Collection c : entryset.getValue())
                if (this.id == c.getId() && c.getType().equals(CollectionType.Public.toString()))
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
        }
        return Response.status(404, "No record found").build();
    }
    
    
    /**
     * [9.Elenco di tutti i dischi di un certo autore presenti nelle collezioni pubbliche]
     * 
     * @param request
     * @param name
     * @param from
     * @param to
     * @return 
     */
    @Path("{name: [a-zA-Z0-9]+}")
    @GET
    @Produces("application/json")
    public Response getAuthorRecords(@Context HttpServletRequest request,
            @PathParam("name") String name,
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

        Set<Map<String, Object>> res = new HashSet<>();

        for (Map.Entry<Integer, List<Collection>> entryset : data.entrySet())
        {
            int lfrom = from;
            int count = 0;
            for (Collection c : entryset.getValue())
                if (c.getType().equals(CollectionType.Public.toString()))
                    for (Record r: c.getRecords())
                        if (r.getAuthor().getName().equals(name))
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
            
        }
        if (res.isEmpty())
            return Response.status(404, "No record found").build();
        else
            return Response.ok(res).build();
    }
}
