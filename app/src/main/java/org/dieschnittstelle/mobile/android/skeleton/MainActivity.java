package org.dieschnittstelle.mobile.android.skeleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.dieschnittstelle.mobile.android.skeleton.databinding.ActivityMainListitemBinding;
import org.dieschnittstelle.mobile.android.skeleton.model.impl.RoomDataItemCRUDOperationImpl;
import org.dieschnittstelle.mobile.android.skeleton.model.impl.SimpleDataItemCRUDOperationsImpl;
import org.dieschnittstelle.mobile.android.skeleton.model.impl.ThreadedDataItemCRUDOperationsAsyncImpl;
import org.dieschnittstelle.mobile.android.skeleton.model.DataItem;
import org.dieschnittstelle.mobile.android.skeleton.model.IDataItemCRUDOperationsAsync;

public class
MainActivity extends AppCompatActivity {

    //private TextView welcomeText;
    //structures to hold the data
    //private ViewGroup listView;



    private ListView listView;
//    private Object Stream;
    private List<DataItem> items = new ArrayList<>();
//            Stream.of("lorem", "ipsum", "dolor", "sit", "amet", "olor", "adipiscing", "elit", "laren")
//                    .map(itemstr -> {
//                        DataItem itemobj = new DataItem(itemstr);
//                        itemobj.setId(DataItem.nextId());
//                        return itemobj;
//                    }).collect(Collectors.toList());

    private ArrayAdapter<DataItem> listViewAdapter;
    private ProgressBar progressBar;
    //Get data for logging
//    Class class = this.getClass();
//    private static final String TAG = this.class.getSimpleName();
    private static final String logtag = MainActivity.class.getSimpleName();
//    private Intent DetailViewActivity;

    private FloatingActionButton addNewItemButton;
    //to define activity in Datei_VIEW
    private static final int CALL_DETAILVIEW_FOR_CREATE = 0;
    private static final int CALL_DETAILVIEW_FOR_EDIT = 1;   //implements View.OnClickListener

    public static final String ARG_MARKER_TIME = "%T";
    public static final String ARG_MARKER_STRING = "%s";

    private IDataItemCRUDOperationsAsync crudOperations;
    //arrayAdapter to allow Checkbox and DetailView being used
    private class DataItemsAdapter extends ArrayAdapter<DataItem>{
        private int layoutResource;

        public DataItemsAdapter(@NonNull Context context, int resource, @NonNull List<DataItem> objects){
            super(context, resource, objects);
            this.layoutResource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View recyclableItemView, @NonNull ViewGroup parent){
//            String msg;
//            msg = logtag + ".getView(): for position: " + position +
//                    " , and recyclableItemView" + recyclableItemView;
//            Log.i(msg);

            View itemView = null;
            DataItem currentItem = getItem(position);

            if (recyclableItemView != null) {
                TextView textView = (TextView) recyclableItemView.findViewById(R.id.itemName);
//                if (textView != null){
//                        msg = logtag + ".getView(): itemName in convertView: " + textView.getText();
//                        Log.i(msg);
//                }
                itemView = recyclableItemView;
                ActivityMainListitemBinding recycleBinding = (ActivityMainListitemBinding) itemView.getTag();  //5332
                recycleBinding.setItem(currentItem);
            }
            else {
                ActivityMainListitemBinding  currentBinding =
                        DataBindingUtil.inflate(getLayoutInflater(),
                                this.layoutResource,
                                null,
                                false);

                currentBinding.setItem(currentItem);
                currentBinding.setController(MainActivity.this);

                 itemView = (View) currentBinding.getRoot();
                 itemView.setTag(currentBinding);
            }

            return itemView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //define linked view, prepare structures for data
        setContentView(R.layout.activity_main);
        this.listView = findViewById(R.id.listView);
        this.listViewAdapter = new DataItemsAdapter(this, R.layout.activity_main_listitem, this.items);
//        this.listViewAdapter = new ArrayAdapter<>(this, R.layout.activity_main_listitem, R.id.itemName, this.items);
        this.listView.setAdapter(listViewAdapter);
        this.progressBar = findViewById(R.id.progressBar);


        //define buttons
        this.addNewItemButton = findViewById(R.id.addNewItemButton);
        this.addNewItemButton.setOnClickListener(v -> {this.onItemCreationRequested();});

        //Activate Listeners to all Items
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 DataItem selectedItem = listViewAdapter.getItem(position);
                 onItemSelected(selectedItem);
             }
         });

//        this.listView = findViewById(R.id.listView);

        //load data
//        listViewAdapter.addAll(readAllDataItems());
        this.crudOperations = new ThreadedDataItemCRUDOperationsAsyncImpl(new RoomDataItemCRUDOperationImpl(this), this, this.progressBar);
        this.crudOperations.readAllDataItems(items -> listViewAdapter.addAll(items));
    }


    protected void onItemSelected(DataItem item)
    {
        Intent detailViewIntent = new Intent(this, DetailViewActivity.class);
        detailViewIntent.putExtra(DetailViewActivity.ARG_ITEM, item);
        this.startActivityForResult(detailViewIntent, CALL_DETAILVIEW_FOR_EDIT);
    }

    protected void onItemCreationRequested()
    {
        Log.d(logtag, "onItemCreationRequested() Creation request received");
//        this.showFeedbackMessage("NewItemCreationRequested at " + ARG_MARKER_TIME);
        Intent detailViewForCreationIntent = new Intent(this, DetailViewActivity.class);
        this.startActivityForResult(detailViewForCreationIntent,CALL_DETAILVIEW_FOR_CREATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String msg = new String("#INIT");
        if (requestCode == CALL_DETAILVIEW_FOR_CREATE){
            if(resultCode == Activity.RESULT_OK){
                DataItem createdItem = (DataItem) data.getSerializableExtra(DetailViewActivity.ARG_ITEM);
                this.onNewItemCreated(createdItem);

                msg = logtag + "Item " + createdItem.getName() + " created. With RESULT_OK at " + ARG_MARKER_TIME;
                //                Log.d(TAG, msg);
            }
            else {
                msg = logtag+ "Item NOT created. ReturnCode  " + resultCode + " at " + ARG_MARKER_TIME;
                //                Log.d(TAG, msg);
            }
//            showFeedbackMessage(msg);
        }
        else if (requestCode == CALL_DETAILVIEW_FOR_EDIT){
            if(resultCode == Activity.RESULT_OK){
                DataItem editedItem = (DataItem) data.getSerializableExtra(DetailViewActivity.ARG_ITEM);
                this.onItemUpdated(editedItem);
                msg = logtag + "Item " + editedItem.getName() + " updated. With RESULT_OK at " + ARG_MARKER_TIME;
            }
            else {
                msg = logtag + "Item NOT Updated. ReturnCode  " + resultCode + " at " + ARG_MARKER_TIME;
            }
//            showFeedbackMessage(msg);
        }
        else {
            msg = "Returning from detailview with: " + requestCode + "  at " + ARG_MARKER_TIME;

        }
        showFeedbackMessage(msg);
    }


    protected void showFeedbackMessage(String msg) {
        //String timeMarker = "&T";
        if(msg.contains(ARG_MARKER_TIME)){
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            Log.v(logtag, "showFeedbackMessage Create Current Time");
            //LocalDateTime time = LocalDateTime.now();
            //int timeMarkerIdx = msg.indexOf(timeMarker);

            msg = msg.replace(ARG_MARKER_TIME, currentDateTimeString);
            Log.v(logtag, "showFeedbackMessage Replace TimeMarker");
        }
//        Toast
//                .makeText(MainActivity.this,
//                msg,
//                Toast.LENGTH_SHORT
//                )
//         .show();
        Log.i(logtag, msg);
        Snackbar.make(findViewById(R.id.rootView), msg, Snackbar.LENGTH_SHORT).show();

        Log.v(logtag, "showFeedbackMessage Show Snackbar");
    }

    protected void onNewItemCreated(DataItem item){
        //item.setId(DataItem.nextId()); // nach onCreate in DetailedView verschoben

        this.crudOperations.createDataItem(item, created -> {
            this.listViewAdapter.add(created);
            Log.i(logtag, ": list is now: " + this.items);
            this.listView.setSelection(this.listViewAdapter.getPosition(created));
        });
    }

    protected void onItemUpdated(DataItem item){
        //get position of changed item

        //remove old, add new item, update view
        this.crudOperations.updateDataItem(item, updated -> {
            int pos = this.items.indexOf(updated);
            this.items.remove(pos);
            this.items.add(pos,updated);
            this.listViewAdapter.notifyDataSetChanged();
            Log.i(logtag, ": List has been updated at pos: " + pos + "New Item: " + this.items);

            //position the view to the new element
            this.listView.setSelection(pos);
        });


    }

    public void onItemCompletedInListView(DataItem item){

        this.crudOperations.updateDataItem(item, updated -> {
            String msg;
            msg = logtag + "Item " + updated.getName() + " Status Completed changed: " +updated.isCompleted() + ".";
            showFeedbackMessage(msg);
        });

    }

    protected void readAllDataItems(Consumer<List<DataItem>> onRead){
        this.progressBar.setVisibility(View.VISIBLE);
        new Thread(() -> {
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

            runOnUiThread(() -> {
                this.progressBar.setVisibility(View.GONE);
                onRead.accept(items);
            });
        }).start();
    }

}

