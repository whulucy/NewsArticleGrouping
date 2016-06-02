package edu.scu.xli2.photonotesplus;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private int maxRecId;
    RecyclerView mainRecyclerView;
    PhotoAdapter photo_adapter;
    PhotoDbHelper dbHelper;
    private List<MyPhoto> photoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new PhotoDbHelper(this);
        maxRecId = dbHelper.getMaxRecID();
        photoList = getData();
        mainRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mainRecyclerView.setLayoutManager(llm);

        setMainRecyclerView();

        //create actionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("PhotoNotes");
        actionBar.setBackgroundDrawable(ContextCompat.getDrawable(
                getApplicationContext(), R.drawable.action_bar_background));

        //TouchHelperFunction
        TouchHelperCallback callback = new TouchHelperCallback(photo_adapter, dbHelper);

        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);

        touchHelper.attachToRecyclerView(mainRecyclerView);


    }

    public List<MyPhoto> getData() {
        List<MyPhoto> data = new ArrayList<>();
        MyPhoto my_photo = null;
        Cursor c = dbHelper.fetchAll();
        if (c != null) {
            while (c.moveToNext()) {
                String photo_description = c.getString(c.getColumnIndex("description"));
                String photo_path = c.getString(c.getColumnIndex("photoPath"));
                my_photo = new MyPhoto();
                my_photo.setDescription(photo_description);
                my_photo.setPath(photo_path);
                data.add(my_photo);
            }
        }
        return data;
    }

    public void setMainRecyclerView() {
        photo_adapter = new PhotoAdapter(getData(), dbHelper);  //return List<MyPhoto>
//        mainRecyclerView.setHasFixedSize(true);
//        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainRecyclerView.setAdapter(photo_adapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_photo:
                Intent intent = new Intent(this, AddPhoto.class);
                startActivity(intent);
                return true;

            case R.id.un_install:
                // User chose the "un_install" action, mark the current item
                Uri packageURI = Uri.parse("package:"+MainActivity.class.getPackage().getName());
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(uninstallIntent);

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    private void toastShow(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


}