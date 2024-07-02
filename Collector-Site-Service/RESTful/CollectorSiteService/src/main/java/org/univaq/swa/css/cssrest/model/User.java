package org.univaq.swa.css.cssrest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/**
 *
 * @author Kama
 */
public class User 
{
    private int id;
    private String nome;
    private String cognome;
    private String email;
    private String username;
    private String password;
    private static int genid = 0;

    public User() 
    {
        id = User.genid++;
        nome = "";
        cognome = "";
        email = "";
        username = "";
        password = "";
    }
    
    @JsonIgnore
    public int getId() 
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNome() 
    {
        return nome;
    }

    @JsonProperty
    public void setNome(String nome) 
    {
        this.nome = nome;
    }

    public String getCognome() 
    {
        return cognome;
    }

    @JsonProperty
    public void setCognome(String cognome) 
    {
        this.cognome = cognome;
    }

    public String getEmail() 
    {
        return email;
    }

    @JsonProperty
    public void setEmail(String email) 
    {
        this.email = email;
    }

    @JsonIgnore
    public String getUsername() 
    {
        return username;
    }

    @JsonProperty
    public void setUsername(String username) 
    {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() 
    {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) 
    {
        this.password = password;
    }
    

    @Override
    public int hashCode() 
    {
        int hash = 3;
        hash = 83 * hash + this.id;
        hash = 83 * hash + Objects.hashCode(this.nome);
        hash = 83 * hash + Objects.hashCode(this.cognome);
        hash = 83 * hash + Objects.hashCode(this.email);
        hash = 83 * hash + Objects.hashCode(this.username);
        hash = 83 * hash + Objects.hashCode(this.password);
        return hash;
    }
    

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) 
            return true;
        
        if (obj == null) 
            return false;
        
        if (getClass() != obj.getClass()) 
            return false;
        
        final User other = (User) obj;
        if (this.id != other.getId()) 
            return false;
        
        if (!Objects.equals(this.nome, other.nome)) 
            return false;
        
        if (!Objects.equals(this.cognome, other.cognome)) 
            return false;
        
        if (!Objects.equals(this.email, other.email)) 
            return false;
        
        if (!Objects.equals(this.username, other.username)) 
            return false;
        
        return Objects.equals(this.password, other.password);
    }
    
    
    @Override
    public String toString() 
    {
        return "Utente{" + "id=" + id + ", nome=" + nome + ", cognome=" + cognome + '}';
    }
    
    
    public static User dummy (String nome, String cognome, 
            String email, String username, String password) 
    {
        User utente = new User();
        
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setEmail(email);
        utente.setUsername(username);
        utente.setPassword(password);
        
        return utente;
    }
}