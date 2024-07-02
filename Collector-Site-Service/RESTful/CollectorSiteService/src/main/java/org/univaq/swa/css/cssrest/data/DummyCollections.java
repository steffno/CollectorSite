package org.univaq.swa.css.cssrest.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.univaq.swa.css.cssrest.model.Collection;
import org.univaq.swa.css.cssrest.model.User;
import org.univaq.swa.css.cssrest.model.Author;
import org.univaq.swa.css.cssrest.model.CollectionType;
import org.univaq.swa.css.cssrest.model.Record;

/**
 *
 * @author Kama
 */
public class DummyCollections 
{
    
    private static Map<Integer, List<Collection>> collections = null;
    
    private static void generateCollections()
    {
        for (User u : DummyUsers.getGeneratedUsers()) { 
            List<Collection> coll = new ArrayList<>();
            
            List<Record> rcl1 = new ArrayList<>();
            List<Record> rcl2 = new ArrayList<>();
            
            for (Author au : DummyAuthors.getGeneratedAuthors())
            {
                List<Record> rcl = DummyRecords.getGeneratedRecords().get(au);
                
                int randRecForAuthor = Utils.randInt(3);
                
                do
                {
                    int randrec = Utils.randInt(4);
                    rcl1.add(rcl.get(randrec));
                    
                    randrec = Utils.randInt(4);
                    rcl2.add(rcl.get(randrec));
                    
                } while(randRecForAuthor-- > 0);
            }
            
            coll.add(Collection.dummy("Collezione " + u.getId()+1, rcl1, CollectionType.Public));
            coll.add(Collection.dummy("Collezione " + u.getId()+2, rcl2, CollectionType.Private));
            
            collections.put(u.getId(), coll);
        }
        
        List<Record> sh1 = DummyRecords.getGeneratedRecords().get(DummyAuthors.getGeneratedAuthors().get(0));
        List<Record> sh2 = DummyRecords.getGeneratedRecords().get(DummyAuthors.getGeneratedAuthors().get(1));

        Collection shared_mastro_kama    = Collection.dummy("CollezioneXY", sh1, CollectionType.Shared);
        Collection shared_kama_caprielos = Collection.dummy("CollezioneYZ", sh2, CollectionType.Shared);
        
        shared_mastro_kama.pushSharedUser(DummyUsers.getGeneratedUsers().get(1));
        shared_mastro_kama.pushSharedUser(DummyUsers.getGeneratedUsers().get(0));
        shared_kama_caprielos.pushSharedUser(DummyUsers.getGeneratedUsers().get(2));
        shared_kama_caprielos.pushSharedUser(DummyUsers.getGeneratedUsers().get(1));
        
        List<Collection> coll;
        coll = collections.get(DummyUsers.getGeneratedUsers().get(0).getId());
        coll.add(shared_mastro_kama);
        collections.put(DummyUsers.getGeneratedUsers().get(0).getId(), coll);
        
        coll = collections.get(DummyUsers.getGeneratedUsers().get(1).getId());
        coll.add(shared_mastro_kama);
        coll.add(shared_kama_caprielos);
        collections.put(DummyUsers.getGeneratedUsers().get(1).getId(), coll);
        
        coll = collections.get(DummyUsers.getGeneratedUsers().get(2).getId());
        coll.add(shared_kama_caprielos);
        collections.put(DummyUsers.getGeneratedUsers().get(2).getId(), coll);
       
    }
    
    
    public static Map<Integer, List<Collection>> getGeneratedCollections()
    {
        if (DummyCollections.collections == null)
        {
            DummyCollections.collections = new HashMap<>();
            DummyCollections.generateCollections();
        }
        
        return DummyCollections.collections;
    }
    
    
    public static void pushRecordInto(int iduser, int idcoll, Record r)
    {
        if (DummyCollections.collections == null)
        {
            DummyCollections.collections = new HashMap<>();
            DummyCollections.generateCollections();
        }
        
        ListIterator<Collection> itr1 = DummyCollections.collections.get(iduser).listIterator();
        
        while (itr1.hasNext())
        {
            Collection curr = itr1.next();
            if (curr.getId() == idcoll)
            {
                if (itr1.hasPrevious()) itr1.previous();
                else itr1 = DummyCollections.collections.get(iduser).listIterator();
                
                ListIterator<Record> itr2 = itr1.next().getRecords().listIterator();
                
                itr2.add(r);
            }
        }
        
        List<Collection> colls = DummyCollections.collections.get(iduser);
        for (Collection c : colls)
            if (c.getId() == idcoll)
            { c.incr(); break; }
        
    }
    
    public static void updateRecord(int iduser, int idcoll, Record r)
    {
        if (DummyCollections.collections == null)
        {
            DummyCollections.collections = new HashMap<>();
            DummyCollections.generateCollections();
        }
        
        ListIterator<Collection> itr1 = DummyCollections.collections.get(iduser).listIterator();
        
        while (itr1.hasNext())
        {
            Collection curr = itr1.next();
            if (curr.getId() == idcoll)
            {
                if (itr1.hasPrevious()) itr1.previous();
                else itr1 = DummyCollections.collections.get(iduser).listIterator();
                
                ListIterator<Record> itr2 = itr1.next().getRecords().listIterator();
                
                while (itr2.hasNext())
                    if (itr2.next().getId() == r.getId())
                    {
                        itr2.remove();
                        itr2.add(r);
                    }
            }
        }
    }
}
