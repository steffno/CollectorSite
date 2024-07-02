package org.univaq.swa.css.cssrest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;

/**
 *
 * @author MastroGibbs
 */
public class Track 
{
    
    private int id;
    private String title;
    private float length;
    private static int genid = 0;

    public Track() 
    {
        this.id = Track.genid++;
        this.title = "";
        this.length = 0.f;
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

    public String getTitle() 
    {
        return title;
    }

    public float getLength() 
    {
        return length;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public void setLength(float length) 
    {
        this.length = length;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.id;
        hash = 41 * hash + Objects.hashCode(this.title);
        hash = 41 * hash + Float.floatToIntBits(this.length);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Track other = (Track) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Float.floatToIntBits(this.length) != Float.floatToIntBits(other.length)) {
            return false;
        }
        return Objects.equals(this.title, other.title);
    }

    @Override
    public String toString() {
        return "Track{" + "id=" + id + ", title=" + title + ", length=" + length + '}';
    }

    public static Track dummy(String title, float length)
    {
        Track t = new Track();
        t.setTitle(title);
        t.setLength(length);
        return t;
    }
    
    
    
}
