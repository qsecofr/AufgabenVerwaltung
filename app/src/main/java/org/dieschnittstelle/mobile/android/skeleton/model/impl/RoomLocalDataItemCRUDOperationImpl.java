package org.dieschnittstelle.mobile.android.skeleton.model.impl;


import android.content.Context;
import android.provider.ContactsContract;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import org.dieschnittstelle.mobile.android.skeleton.model.DataItem;
import org.dieschnittstelle.mobile.android.skeleton.model.IDataItemCRUDOperations;

import java.util.List;

public class RoomLocalDataItemCRUDOperationImpl implements IDataItemCRUDOperations {

    @Database(entities = {DataItem.class}, version=1)
    public static abstract class RoomDataItemsDatabase extends RoomDatabase{
        public abstract RoomDataItemCRUDAccess getDao();
    }


    @Dao
    public static interface RoomDataItemCRUDAccess{

        @Insert
        public long createItem(DataItem item);

        @Query("select * from dataitem")
        public List<DataItem> readAllDataItems();

        @Query("select * from dataitem where id = (:id)")
        public DataItem readItem(long id);

        @Update
        public int update(DataItem item);
    }

    private RoomDataItemCRUDAccess roomAccessor;

    public RoomLocalDataItemCRUDOperationImpl(Context databaseOwner) {
        RoomDataItemsDatabase db = Room
                .databaseBuilder(databaseOwner
                        .getApplicationContext()
                            , RoomDataItemsDatabase.class
                            , "dataItems-db-AufgabenVerwaltung21"
                ).build();
        this.roomAccessor =db.getDao();
    }

    @Override
    public DataItem createDataItem(DataItem item) {
        long newID = roomAccessor.createItem(item);
        item.setId(newID);
        return item;
    }

    @Override
    public List<DataItem> readAllDataItems() {
        return roomAccessor.readAllDataItems();
    }

    @Override
    public DataItem readDataItem(long id) {
        return null;
    }

    @Override
    public DataItem updateDataItem(DataItem item) {
        roomAccessor.update(item);
        return item;
    }

    @Override
    public boolean deleteDataItem(long id) {
        return false;
    }
}
