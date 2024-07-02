package org.univaq.swa.css.cssrest.security;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import static jakarta.ws.rs.core.HttpHeaders.AUTHORIZATION;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import static jakarta.ws.rs.core.Response.Status.UNAUTHORIZED;
import jakarta.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.univaq.swa.css.cssrest.data.DummyUsers;
import org.univaq.swa.css.cssrest.model.User;

/**
 *
 * @author didattica
 *
 */
@Path("auth")
public class AuthSystemResource {

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response doLogin(@Context UriInfo uriinfo,
            @FormParam("username") String username,
            @FormParam("password") String password) 
    {
        Integer id = authenticate(username, password);

        if (id != null) 
        {
            String tk = issueToken(uriinfo, id);
            
            return Response.ok(tk).header(AUTHORIZATION, "Bearer " + tk).build();
        }
            
        else 
            return Response.status(UNAUTHORIZED).build();
    }

    
    @BearerAccess
    @DELETE
    @Path("/logout")
    public Response doLogout(@Context HttpServletRequest request) {
        
        try {
            String token = (String) request.getAttribute("token");
            
            if (token != null) 
                revokeToken(token);
            
            return Response.ok("sloggato").build();
        } 
        catch (Exception e) 
        {
            return Response.serverError().build();
        }
    }

    private Integer authenticate(String username, String password) 
    {
        for (User u : DummyUsers.getGeneratedUsers()) { 
            if (username.equals(u.getUsername()) && password.equals(u.getPassword()))
                return u.getId();
        }
        
        return null;
    }

    private String issueToken(UriInfo context, Integer id)
    {
        String token = UUID.randomUUID().toString() + id;
        TokenWerehouse.tokens.add(token);
        return token;
    }

    private void revokeToken(String token) 
    {
        Iterator<String> itr = TokenWerehouse.tokens.iterator();
        
        while (itr.hasNext())
            if (itr.next().equals(token)) { itr.remove(); break; }
        
    }
    
    
    public static List<String> tokenWarehouse()
    {
        return TokenWerehouse.tokens;
    }
}


class TokenWerehouse
{
    public static List<String> tokens = new ArrayList<>();
}