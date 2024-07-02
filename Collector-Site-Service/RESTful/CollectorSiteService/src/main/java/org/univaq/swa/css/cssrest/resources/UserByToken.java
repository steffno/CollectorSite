package org.univaq.swa.css.cssrest.resources;

import org.univaq.swa.css.cssrest.data.DummyUsers;
import org.univaq.swa.css.cssrest.model.User;
import org.univaq.swa.css.cssrest.security.AuthSystemResource;

/**
 *
 * @author MastroGibbs
 */
public class UserByToken 
{
    public static User getUserByToken(String token)
    {
        User user = null;
        if (token != null) 
            for(String tk : AuthSystemResource.tokenWarehouse())
                if (tk.equals(token)) 
                    for (User u : DummyUsers.getGeneratedUsers()) 
                        if (u.getId() == Integer.parseInt(token.substring(36)))
                        { user = u; break; }
        return user;
    }
}