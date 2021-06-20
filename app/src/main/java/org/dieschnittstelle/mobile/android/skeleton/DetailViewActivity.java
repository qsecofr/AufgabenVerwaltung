package org.dieschnittstelle.mobile.android.skeleton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
//import android.widget.EditText;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.dieschnittstelle.mobile.android.skeleton.databinding.ActivityDetailviewBinding;
import org.dieschnittstelle.mobile.android.skeleton.model.DataItem;

import java.text.DateFormat;
import java.util.Date;


public class DetailViewActivity extends AppCompatActivity{

//Static
    private static final String logtag = DetailViewActivity.class.getSimpleName();
    public static final String ARG_ITEM = "item";
    public static final int ARG_PICK_CONTACT = 0;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String msg;
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        msg = logtag + "Pick Contact ReqCode: " + requestCode + ". Result: " + resultCode + "."; // + createdItem.getName() +
        Log.i(logtag, msg);

        if (requestCode == ARG_PICK_CONTACT && resultCode == Activity.RESULT_OK) {
            msg = logtag + "Contact " + " linked. With RESULT_OK at " + currentDateTimeString + "."; // + createdItem.getName() +
            Log.i(logtag, msg);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT);
//            Snackbar.make(findViewById(R.id.rootView), msg, Snackbar.LENGTH_SHORT).show();
            }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



    public void onSaveItem() {
        String msg = logtag + "Method onSaveItem. ReturnCode  " + Activity.RESULT_OK; //+ " at " + ARG_MARKER_TIME;
        Log.i(logtag, msg);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT);
//        Snackbar.make(findViewById(R.id.), msg, Snackbar.LENGTH_SHORT).show();

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


    public void onSelectContact() {
        showContacts();
    }


    public void showContacts() {
        Intent contactSelectionIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactSelectionIntent, ARG_PICK_CONTACT);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailview_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.selectContact) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void selectContact() {
        Intent selectContentIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(selectContentIntent, ARG_PICK_CONTACT);
    }
}
