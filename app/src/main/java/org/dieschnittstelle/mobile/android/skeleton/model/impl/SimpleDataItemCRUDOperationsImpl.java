package org.dieschnittstelle.mobile.android.skeleton.model.impl;

import org.dieschnittstelle.mobile.android.skeleton.model.DataItem;
import org.dieschnittstelle.mobile.android.skeleton.model.IDataItemCRUDOperations;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleDataItemCRUDOperationsImpl implements IDataItemCRUDOperations {
    @Override
    public DataItem createDataItem(DataItem item) {
        item.setId(DataItem.nextId());
        return item;
    }

    @Override
    public List<DataItem> readAllDataItems() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<DataItem> items = Stream.of("lorem", "ipsum", "dolor", "sit", "amet", "olor", "adipiscing", "elit", "laren")
                .map(itemstr -> {
                    DataItem itemobj = new DataItem(itemstr);
                    itemobj.setId(DataItem.nextId());
                    return itemobj;
                }).collect(Collectors.toList());
        return items;
    }

    @Override
    public DataItem readDataItem(long id) {
        return null;
    }

    @Override
    public DataItem updateDataItem(DataItem item) {
        return item;
    }

    @Override
    public boolean deleteDataItem(long id) {
        return false;
    }
}
