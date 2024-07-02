package org.univaq.swa.css.cssrest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Caprielos
 */
public class Collection {
    
    private int id;
    private String name;
    private int recordCount;
    private List<Record> records;
    private List<Integer> shared;
    private String type;
    
    private static int genid = 0;
    
    
    public Collection() {
        this.id = Collection.genid++;
        this.name = "";
        this.recordCount= 0;
        this.records = null;
        this.shared = null;
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
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
    
    public boolean pushRecord(Record r)
    {
        return this.records.add(r);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Integer> getShared() {
        return shared;
    }
    
    public void incr()
    {
        this.recordCount = this.getRecords().size();
    }

    public void pushSharedUser(User shared) {
        if (this.shared == null)
            this.shared = new ArrayList<>();
        
        this.shared.add(shared.getId());
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + this.recordCount;
        hash = 59 * hash + Objects.hashCode(this.records);
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
        final Collection other = (Collection) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.recordCount != other.recordCount) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.records, other.records);
    }

    @Override
    public String toString() {
        return "Collection{" + "id=" + id + ", name=" + name + ", recordCount="
                + recordCount + ", records=" + records + '}';
    }

    public static Collection dummy(String name, List<Record> rcl, CollectionType t)
    {
        Collection c = new Collection();
        c.setName(name);
        c.setRecordCount(rcl.size());
        c.setRecords(rcl);
        c.setType(t.toString());
        return c;
    }
}