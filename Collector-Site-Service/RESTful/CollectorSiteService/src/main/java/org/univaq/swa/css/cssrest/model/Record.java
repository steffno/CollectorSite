package org.univaq.swa.css.cssrest.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Kama
 */


public class Record 
{
    private int id;
    private String title;
    private int trackCount;
    private Author author;
    private LocalDate date;
    private float length;
    private List<Track> tracks;
    private static int genid = 0;
    
    
    /**
     * Super Dummy
     * 
     */
    
    public Record() 
    {
        this.id = Record.genid++;
        this.title = "";
        this.trackCount = 0;
        this.author = null;
        this.date = null;
        this.length = 0.f;
        this.tracks = new ArrayList<>();
    }
    
    
    
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

    public int getTrackCount() 
    {
        return trackCount;
    }

    public Author getAuthor() 
    {
        return author;
    }

    public LocalDate getDate() 
    {
        return date;
    }

    public float getLength() 
    {
        return length;
    }

    public List<Track> getTracks() 
    {
        return tracks;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public void setTrackCount(int trackCount) 
    {
        this.trackCount = trackCount;
    }

    public void setAuthor(Author author) 
    {
        this.author = author;
    }

    public void setDate(LocalDate date) 
    {
        this.date = date;
    }

    public void setLength(float length) 
    {
        this.length = length;
    }

    public void setTracks(List<Track> tracks) 
    {
        this.tracks = tracks;
    }
    
    public boolean pushTrack(Track newTrack)
    {
        if (!this.tracks.contains(newTrack))
            return this.tracks.add(newTrack);
        
        return false;
    }

    @Override
    public int hashCode() 
    {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.title);
        hash = 97 * hash + this.trackCount;
        hash = 97 * hash + Objects.hashCode(this.author);
        hash = 97 * hash + Objects.hashCode(this.date);
        hash = 97 * hash + Float.floatToIntBits(this.length);
        hash = 97 * hash + Objects.hashCode(this.tracks);
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
        
        final Record other = (Record) obj;
        
        if (this.trackCount != other.trackCount) 
            return false;
        
        if (Float.floatToIntBits(this.length) != Float.floatToIntBits(other.length))
            return false;
        
        if (!Objects.equals(this.title, other.title)) 
            return false;
        
        if (!Objects.equals(this.author, other.author)) 
            return false;
        
        if (!Objects.equals(this.date, other.date)) 
            return false;
        
        return Objects.equals(this.tracks, other.tracks);
    }

    @Override
    public String toString() 
    {
        return "Record{" + "title=" + title + ", trackCount=" + trackCount + 
                ", author=" + author + ", date=" + date + ", length=" 
                + length + ", tracks=" + tracks + '}';
    }
    
    public static Record dummy(String title, int trackno, Author author, LocalDate date, float lenght, List<Track> tl)
    {
        Record r = new Record();
        r.setTitle(title);
        r.setTrackCount(trackno);
        r.setAuthor(author);
        r.setDate(date);
        r.setLength(lenght);
        r.setTracks(tl);
        
        return r;
    }
        
    
}