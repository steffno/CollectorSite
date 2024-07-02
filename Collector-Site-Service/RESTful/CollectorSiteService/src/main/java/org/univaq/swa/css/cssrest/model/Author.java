package org.univaq.swa.css.cssrest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;

/**
 *
 * @author MastroGibbs
 */


public class Author
{
    private int id;
    private String name;
    private int recordNo;
    private boolean isband;
    private String bio;
    private GenreType genreType;
    private static int genid = 0;

    public Author() 
    {
        this.id = Author.genid++;
        this.name = "";
        this.recordNo = 0;
        this.isband = false;
        this.bio = "";
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

    public String getName() 
    {
        return name;
    }

    public int getRecordNo() 
    {
        return recordNo;
    }

    public boolean isBand() 
    {
        return isband;
    }

    public String getBio() 
    {
        return bio;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public void setRecordNo(int recordNo)
    {
        this.recordNo = recordNo;
    }

    public void setBand(boolean isBand)
    {
        this.isband = isBand;
    }

    public void setBio(String bio) 
    {
        this.bio = bio;
    }

    public String getGtype() {
        return genreType.toString();
    }

    public void setGtype(GenreType gtype) {
        this.genreType = gtype;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + this.recordNo;
        hash = 47 * hash + (this.isband ? 1 : 0);
        hash = 47 * hash + Objects.hashCode(this.bio);
        hash = 47 * hash + Objects.hashCode(this.genreType);
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
        final Author other = (Author) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.recordNo != other.recordNo) {
            return false;
        }
        if (this.isband != other.isband) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.bio, other.bio)) {
            return false;
        }
        return this.genreType == other.genreType;
    }

    @Override
    public String toString() {
        return "Author{" + "Id=" + id + ", Name=" + name + ", recordNo=" 
                + recordNo + ", IsBand=" + isband + ", Bio=" + bio + ", GenreType=" 
                + genreType + '}';
    }
        
    
    public static Author dummy(String name, int rno, boolean isband, String bio, GenreType gt)
    {
        Author a = new Author();
        a.setName(name);
        a.setRecordNo(rno);
        a.setBand(isband);
        a.setBio(bio);
        a.setGtype(gt);
        return a;
    }
    
    
}
