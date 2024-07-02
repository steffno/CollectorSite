package org.univaq.swa.css.cssrest.data;

import java.util.ArrayList;
import java.util.List;
import org.univaq.swa.css.cssrest.model.User;

/**
 *
 * @author MastroGibbs
 */
public class DummyUsers 
{
    
    private static List<User> users = null;
    
    private static void generateUsers()
    {
        users.add(User.dummy("Stefano", "Fattore", "mastro@gmail.com", "mastro", "1234"));
        users.add(User.dummy("Stefano", "Di Diosinsio", "kama@gmail.com", "kama", "1234"));
        users.add(User.dummy("Gabriele", "Di Egidio", "caprielos@gmail.com", "caprielos", "1234"));
    }
    
    
    public static List<User> getGeneratedUsers()
    {
        if (DummyUsers.users == null)
        {
            DummyUsers.users = new ArrayList<>();
            DummyUsers.generateUsers();
        }
        
        return DummyUsers.users;
    }
}
