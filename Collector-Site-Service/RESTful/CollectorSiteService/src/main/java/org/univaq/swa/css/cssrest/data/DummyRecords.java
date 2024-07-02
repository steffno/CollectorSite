package org.univaq.swa.css.cssrest.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.univaq.swa.css.cssrest.model.Author;
import org.univaq.swa.css.cssrest.model.Record;
import org.univaq.swa.css.cssrest.model.Track;


/**
 *
 * @author Caprielos
 */
public class DummyRecords 
{
    private static Map<Author, List<Record>> records = null;
    
    private static void generateRecords()
    {
        for (Author au : DummyAuthors.getGeneratedAuthors())
        {
            List<Record> rcl = new ArrayList<>();
            int i = Utils.randInt(3) + 5, rci = 0;
            do
            {
                int trackno = Utils.randInt(15);
                int j = trackno, tri = 0;
                
                float recordLength = .0f;
                
                List<Track> tracks = new ArrayList<>();
                do 
                {
                    float trackLength = Utils.randFloat(5.0f);
                    recordLength += trackLength;
                    
                    Track tr = Track.dummy("Track " + Integer.toString(++tri), trackLength);
                    tracks.add(tr);
                    
                } while (j-- > 1);
                                
                Record r = Record.dummy("Record " + Integer.toString(++rci), trackno, au, Utils.getRandomDate(), recordLength, tracks);
                rcl.add(r);
                
            } while (i-- > 0);
            
            records.put(au, rcl);
        }
    }
    
    
    public static Map<Author, List<Record>> getGeneratedRecords()
    {
        if (DummyRecords.records == null)
        {
            DummyRecords.records = new HashMap<>();
            DummyRecords.generateRecords();
        }
        
        return DummyRecords.records;
    }
}
