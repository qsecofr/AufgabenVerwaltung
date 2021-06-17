package org.dieschnittstelle.mobile.android.skeleton.model.impl;

import org.dieschnittstelle.mobile.android.skeleton.model.DataItem;
import org.dieschnittstelle.mobile.android.skeleton.model.IDataItemCRUDOperations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class RetrofitRemoteDataItemCRUDOperationsImpl implements IDataItemCRUDOperations {

    public static interface TodoWebAPI{

        @POST("/api/todos")
        public Call<DataItem> createTodo(@Body DataItem item);

        @GET("/api/todos")
        public Call<List<DataItem>> readAllTodos();

        @GET("/api/todos/{id}")
        public Call<DataItem> readTodo(@Path("id") long id);

        @PUT("/api/todos/{todoid}")
        public Call<DataItem> updateTodo(@Path("todoid") long id,@Body DataItem item);

        @DELETE("/api/todos/{id}")
        public Call<Boolean> deleteTodo(@Path("id") long id);

        @DELETE("/api/todos")
        public Call<Boolean> deleteAllTodos();



    }

    private TodoWebAPI webAPI;


    public RetrofitRemoteDataItemCRUDOperationsImpl() {
        Retrofit apiBase = new Retrofit.Builder()
                                .baseUrl("http://192.168.24.94:8080/")   //api/
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
        this.webAPI = apiBase.create(TodoWebAPI.class);
    }



    @Override
    public DataItem createDataItem(DataItem item) {
        try {
            return this.webAPI.createTodo(item).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            //TODO Catch error WebAPI.createDataIte()
            return null;
        }
    }

    @Override
    public List<DataItem> readAllDataItems() {
        try {
            return this.webAPI.readAllTodos().execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            //TODO Catch error WebAPI.readAllDataItems()
            return new ArrayList<>();
        }
    }

    @Override
    public DataItem readDataItem(long id) {
        try {
            return this.webAPI.readTodo(id).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            //TODO Catch error WebAPI.readDataItem(long id)
            return new DataItem();
        }
    }

    @Override
    public DataItem updateDataItem(DataItem item) {
        try {
            return this.webAPI.updateTodo(item.getId(), item).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            //TODO Catch error WebAPI.updateDataItem(DataItem item)
            return null;
        }
    }

    @Override
    public boolean deleteDataItem(long id) {
        try {
            return this.webAPI.deleteTodo(id).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            //TODO Catch error WebAPI.deleteDataItem(long id)
            return false;
        }
    }
}
