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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import android.util.Log;

import retrofit2.Retrofit;

public class DataItemApplication extends Application {

//Static
    protected static String logtag = "DataItemApplication";

    private IDataItemCRUDOperations crudOperations;

    @Override
    public void onCreate() {
        super.onCreate();

        Future<Boolean> connectivityFuture = checkConnectivityAsync();
//TODO Continue 09062021 87:00
        String msg2 = "Checking Connectivity... " + DateFormat.getDateTimeInstance().format(new Date()) + ".";
        Toast.makeText(this, msg2, Toast.LENGTH_SHORT).show();



        try {
            //if (true) {
            if (connectivityFuture.get()) {
                this.crudOperations = new RetrofitRemoteDataItemCRUDOperationsImpl();
                String msg = "Application started in SERVER MODE. At " + DateFormat.getDateTimeInstance().format(new Date()) + ".";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                Log.i(logtag, msg);

            }
            else {
                this.crudOperations =  new RoomLocalDataItemCRUDOperationImpl(this);
                String msg = "Application started in LOCAL MODE. At " + DateFormat.getDateTimeInstance().format(new Date()) + ".";
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                Log.i(logtag, msg);
            }
        } catch (ExecutionException e) {
            this.crudOperations =  new RoomLocalDataItemCRUDOperationImpl(this);
            String msg = "Application started in LOCAL MODE. At " + DateFormat.getDateTimeInstance().format(new Date()) + "."
                    + "ExecutionException " + e;
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            this.crudOperations =  new RoomLocalDataItemCRUDOperationImpl(this);
            String msg = "Application started in LOCAL MODE. At " + DateFormat.getDateTimeInstance().format(new Date()) + "."
                    + "InterruptedException " + e;
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();;
        }
    }

    public IDataItemCRUDOperations getCRUDOperations() {
//        return new RoomLocalDataItemCRUDOperationImpl(this);
        return this.crudOperations;
    }

    public Future<Boolean> checkConnectivityAsync() {
        CompletableFuture<Boolean> future  = new CompletableFuture<>();
        new Thread(() -> {
            boolean connectionAvailable = checkConnectivity();
            future.complete(connectionAvailable);
        }).start();
        return future;
    }


    public boolean checkConnectivity() {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL("http://192.168.24.94:8080/api/todos").openConnection();
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
        finally {
            if (conn != null) {
               conn.disconnect();
            }
        }

    }
}
