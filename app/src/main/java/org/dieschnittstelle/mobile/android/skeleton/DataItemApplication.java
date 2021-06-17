package org.dieschnittstelle.mobile.android.skeleton;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.dieschnittstelle.mobile.android.skeleton.model.IDataItemCRUDOperations;
import org.dieschnittstelle.mobile.android.skeleton.model.impl.RetrofitRemoteDataItemCRUDOperationsImpl;
import org.dieschnittstelle.mobile.android.skeleton.model.impl.RoomLocalDataItemCRUDOperationImpl;


import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;

import retrofit2.Retrofit;

public class DataItemApplication extends Application {

//Static
    protected static String logtag = "DataItemApplication";

    private IDataItemCRUDOperations crudOperations;

    @Override
    public void onCreate() {
        super.onCreate();
//TODO Continue 09062021 26:00
//TODO Continue 0062021 80:11
        if (true) {
        //if (checkConnectivity()) {
            String msg = "Application started in SERVER MODE. At " + DateFormat.getDateTimeInstance().format(new Date()) + ".";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            this.crudOperations = new RetrofitRemoteDataItemCRUDOperationsImpl();
        }
        else {
            this.crudOperations =  new RoomLocalDataItemCRUDOperationImpl(this);
            String msg = "Applicati started in LOca MODE. At " + DateFormat.getDateTimeInstance().format(new Date()) + ".";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        }
    }

    public IDataItemCRUDOperations getCRUDOperations() {
//        return new RoomLocalDataItemCRUDOperationImpl(this);
        return crudOperations;
    }

    public boolean checkConnectivity() {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL("http://192.168.24.94:8080/api/todos").openConnection();
            conn.setReadTimeout(1000);
            conn.setConnectTimeout(1000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            conn.getInputStream();
            String msg = logtag + DateFormat.getDateTimeInstance().format(new Date()) + " Connection to Server: successful ";
            Log.v(logtag, msg);

            return true;
        }
        catch (Exception e) {
            String msg = logtag + DateFormat.getDateTimeInstance().format(new Date()) + " No Connection to Server: Exception: " + e;
            Log.e(logtag, msg, e);
            return false;
        }

    }
}
