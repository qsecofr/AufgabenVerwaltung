package org.dieschnittstelle.mobile.android.skeleton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
//import android.widget.EditText;

import javax.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.dieschnittstelle.mobile.android.skeleton.databinding.ActivityDetailviewBinding;
import org.dieschnittstelle.mobile.android.skeleton.model.DataItem;


public class DetailViewActivity extends AppCompatActivity{

//Static
    private static final String logtag = DetailViewActivity.class.getSimpleName();
    public static final String ARG_ITEM = "item";
//    private EditText itemNameText;
//    private EditText itemDescription;
//UI
    private FloatingActionButton saveButton;

//Data
    private DataItem item;
    private ActivityDetailviewBinding dataBindingHandle;

    public void onCreate (@Nullable Bundle savedInstanceState)
    {
        //init class
        super.onCreate(savedInstanceState);
        this.dataBindingHandle = DataBindingUtil.setContentView(this, R.layout.activity_detailview);

        String msg = logtag + "Method onCreate."; //+ " at " + ARG_MARKER_TIME;
        Log.i(logtag, msg);


        //get data
        item = (DataItem) getIntent().getSerializableExtra(ARG_ITEM);


        //existing/new item
        if (item == null) {
            item = new DataItem();
            item.setId(DataItem.nextId());
            msg = logtag + "Item created. New ID: " + item.getId() + ", Name: " + item.getName(); //+ " at " + ARG_MARKER_TIME;
            Log.i(logtag, msg);
        }
        this.dataBindingHandle.setController(this);
    }

    public void onSaveItem() {
//        String itemName = this.itemNameText.getText().toString();

//        item.setName(this.itemNameText.getText().toString());
//        item.setDescription(this.itemDescription.getText().toString());

        String msg = logtag + "Method onSaveItem. ReturnCode  " + Activity.RESULT_OK; //+ " at " + ARG_MARKER_TIME;
        Log.i(logtag, msg);

        Intent returnIntent = new Intent();
        returnIntent.putExtra(ARG_ITEM, item);

        this.setResult(Activity.RESULT_OK, returnIntent);

        finish();
    }

    public DataItem getItem() {
        return item;
    }

    public void setItem(DataItem item) {
        this.item = item;
    }
}
