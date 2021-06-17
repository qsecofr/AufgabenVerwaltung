package org.dieschnittstelle.mobile.android.skeleton.model;

import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.dieschnittstelle.mobile.android.skeleton.MainActivity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
public class DataItem implements Serializable {

    //Static
    protected static long ID_GENERATOR = 0;


    public static long nextId() {
        return ID_GENERATOR++;
//        return UUID.randomUUID(System.currentTimeMillis());
    }

    private static final String logtag = DataItem.class.getSimpleName();

//Variables
    @PrimaryKey(autoGenerate = true)
    private long  id;

    private String name;
    private String description;

    @SerializedName("done")
    private boolean completed;


    //TODO expiry, datatype?
//    @SerializedName("expiry")
//    private Date completedUntil;

    //TODO favourite
    @SerializedName("favourite")
    private boolean favourite;

    //TODO responsible, datatype?
    @SerializedName("contacts")
    private boolean responsible;

    //TODO location, datatype?
    @SerializedName("location")
    private boolean location;


    public DataItem() {
        Log.i(logtag, " Created, empty");

    }

    public DataItem(String name) {
        Log.i(logtag, " Created with Name");
        this.name = name;
    }

    public void setName(String name) {
        Log.i(logtag, " setName(" + name +")");
        this.name = name;
    }
    public String getName() {
        Log.i(logtag, " getName()");
        return name;
    }

    public void setDescription(String description) {
        Log.i(logtag, " setDescription(" + description + ")");
        this.description = description;
    }
    public String getDescription() {
        Log.i(logtag, " getDescription()");
        return description;
    }

    public void setCompleted(boolean completed) {
        Log.i(logtag, " setCompleted(" + completed + ")");
        this.completed = completed;
    }
    public boolean isCompleted() {
        Log.i(logtag, " isCompleted()");
        return completed;
    }

//    public void setCompletedUntil(Date completedUntil) {
//        Log.i(logtag, " setCompleted_until(" + completedUntil + ")");
//        this.completedUntil = completedUntil;
//    }
//    public Date getCompletedUntil() {
//        Log.i(logtag, " getCompletedUntil()");
//        return completedUntil;
//    }

    public void setFavourite(boolean favourite) {
        Log.i(logtag, " setFavourite(" + favourite + ")");
        this.favourite = favourite;
    }
    public boolean isFavourite() {
        Log.i(logtag, " isFavourite()");
        return favourite;
    }

    public void setResponsible(boolean responsible) {
        Log.i(logtag, " setResponsible(" + responsible + ")");
        this.responsible = responsible;
    }
    public boolean isResponsible() {
        Log.i(logtag, " isResponsible()");
        return responsible;
    }

    public void setLocation(boolean location) {
        Log.i(logtag, " setLocation(" + location + ")");
        this.location = location;
    }
    public boolean isLocation() {
        Log.i(logtag, " isLocation()");
        return location;
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
//                ", completed_until=" + completed_until +
                ", favourite=" + favourite +
                ", responsible=" + responsible +
                ", location=" + location +
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
