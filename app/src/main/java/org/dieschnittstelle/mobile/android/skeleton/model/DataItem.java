package org.dieschnittstelle.mobile.android.skeleton.model;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.dieschnittstelle.mobile.android.skeleton.MainActivity;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class DataItem implements Serializable {

    protected static long ID_GENERATOR = 0;

    public static long nextId() {
        return ID_GENERATOR++;
//        return UUID.randomUUID(System.currentTimeMillis());
    }

    private static final String logtag = DataItem.class.getSimpleName();

    //variables
    @PrimaryKey(autoGenerate = true)
    private long  id;

    private String name;
    private String description;
    private boolean completed;


    public DataItem() {
        Log.i(logtag, " Created, empty");

    }

    public DataItem(String name) {
        Log.i(logtag, " Created with Name");
        this.name = name;
    }

    public String getName() {
        Log.i(logtag, " getName()");
        return name;
    }

    public String getDescription() {
        Log.i(logtag, " getDescription()");
        return description;
    }

    public boolean isCompleted() {
        Log.i(logtag, " isCompleted()");
        return completed;
    }

    public void setName(String name) {
        Log.i(logtag, " setName(" + name +")");
        this.name = name;
    }

    public void setDescription(String description) {
        Log.i(logtag, " setDescription(" + description + ")");
        this.description = description;
    }

    public void setCompleted(boolean completed) {
        Log.i(logtag, " setCompleted(" + completed + ")");
        this.completed = completed;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "DataItem{" +
//                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataItem dataItem = (DataItem) o;
        return id == dataItem.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
