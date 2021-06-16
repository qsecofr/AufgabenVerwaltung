package org.dieschnittstelle.mobile.android.skeleton.model;

import android.provider.ContactsContract;

import androidx.recyclerview.widget.LinearSmoothScroller;

import org.dieschnittstelle.mobile.android.skeleton.model.DataItem;

import java.util.List;
import java.util.function.Consumer;

public interface IDataItemCRUDOperations {

    public DataItem createDataItem(DataItem item);

    public List<DataItem> readAllDataItems();

    public DataItem readDataItem(long id);

    public DataItem updateDataItem(DataItem item);

    public boolean deleteDataItem(long id);

}
