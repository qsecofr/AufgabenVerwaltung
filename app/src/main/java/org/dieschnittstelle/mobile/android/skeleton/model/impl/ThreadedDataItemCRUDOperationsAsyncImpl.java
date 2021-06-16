package org.dieschnittstelle.mobile.android.skeleton.model.impl;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;

import org.dieschnittstelle.mobile.android.skeleton.model.DataItem;
import org.dieschnittstelle.mobile.android.skeleton.model.IDataItemCRUDOperations;
import org.dieschnittstelle.mobile.android.skeleton.model.IDataItemCRUDOperationsAsync;

import java.util.List;
import java.util.function.Consumer;

public class ThreadedDataItemCRUDOperationsAsyncImpl implements IDataItemCRUDOperationsAsync {

    private IDataItemCRUDOperations crudExecutor;
    private Activity uiThreadProvider;
    private ProgressBar progressBar;

    @Override
    public void createDataItem(DataItem item, Consumer<DataItem> onCreated) {
        this.progressBar.setVisibility(View.VISIBLE);
        new Thread(() -> {
            DataItem created = this.crudExecutor.createDataItem(item);
            this.progressBar.setVisibility(View.GONE);
            this.uiThreadProvider.runOnUiThread(() -> onCreated.accept(created));
        }).start();


    }

    public ThreadedDataItemCRUDOperationsAsyncImpl(IDataItemCRUDOperations crudExecutor, Activity uiThreadProvider, ProgressBar progressBar) {
        this.crudExecutor = crudExecutor;
        this.uiThreadProvider = uiThreadProvider;
        this.progressBar = progressBar;
    }

    @Override
    public void readAllDataItems(Consumer<List<DataItem>> onRead) {
        this.progressBar.setVisibility(View.VISIBLE);
        new Thread(() -> {

            List<DataItem> items = crudExecutor.readAllDataItems();

            uiThreadProvider.runOnUiThread(() -> {
                this.progressBar.setVisibility(View.GONE);
                onRead.accept(items);
            });
        }).start();
    }

    @Override
    public void readDataItem(long id, Consumer<DataItem> onRead) {

    }

    @Override
    public void updateDataItem(DataItem item, Consumer<DataItem> onUpdated) {
        this.progressBar.setVisibility(View.VISIBLE);
        new Thread(() -> {
            DataItem updated = crudExecutor.updateDataItem(item);
            this.progressBar.setVisibility(View.GONE);
            this.uiThreadProvider.runOnUiThread(() -> onUpdated.accept(updated));
        }).start();
    }

    @Override
    public void deleteDataItem(long id, Consumer<Boolean> onDeleted) {

    }
}
