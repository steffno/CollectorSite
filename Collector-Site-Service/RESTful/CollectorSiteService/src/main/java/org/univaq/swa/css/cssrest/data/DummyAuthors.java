package org.univaq.swa.css.cssrest.data;


import java.util.ArrayList;
import org.univaq.swa.css.cssrest.model.Author;
import java.util.List;
import org.univaq.swa.css.cssrest.model.GenreType;

/**
 *
 * @author Kama
 */
public class DummyAuthors {
    
    private static List<Author> authors = null;
    
    private static void generateAuthors()
    {
        authors.add(Author.dummy("Guns 'n' roses", Utils.randInt(15), true, "Rock rules", GenreType.Rock));
        authors.add(Author.dummy("AvengedSevenfold", Utils.randInt(15), true, "Metal rules", GenreType.Metal));
        authors.add(Author.dummy("Jeamie Hendrickx", Utils.randInt(15), false, "Rock rules", GenreType.Rock));
    }
    
    
    public static List<Author> getGeneratedAuthors()
    {
        if (DummyAuthors.authors == null)
        {
            DummyAuthors.authors = new ArrayList<>();
            DummyAuthors.generateAuthors();
        }
        
        return DummyAuthors.authors;
    }
    
}
