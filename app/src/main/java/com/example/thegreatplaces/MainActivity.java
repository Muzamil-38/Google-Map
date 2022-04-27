package com.example.thegreatplaces;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    //************* Initialize lists of contents***************
    ListView myList;
    SQLiteHelper sqLiteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //******** Create table ************
        sqLiteHelper = new SQLiteHelper(this);
        myList = findViewById(R.id.myList);
        //*******************************************

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = sqLiteHelper.oneData(id);
                cursor.moveToFirst();
                Intent viewItem = new Intent(MainActivity.this,ViewItemActivity.class);
                viewItem.putExtra(SQLiteHelper.r_id,id);
                startActivity(viewItem);


            }
        });
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Are you Sure You Want to Delete this?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sqLiteHelper.deleteData(id);

                        setMyList();

                        Toast.makeText(MainActivity.this,"Deleted",Toast.LENGTH_LONG).show();

                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
                return true;
            }
        });


        //*********************************************
        }

    //*******************Created Icon in Action Bar************************
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }


    //****************If User Click on Add Button*********************
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.addIcon:
                Intent intentLoadNewActivity = new Intent(MainActivity.this,AddPage.class);
                startActivity(intentLoadNewActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Show All Data in ListView

    public void setMyList(){
        Cursor cursor = sqLiteHelper.allData();
        CustomCursorAdapter customCursorAdapter = new CustomCursorAdapter(this,cursor,1);
        myList.setAdapter(customCursorAdapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { }

    
    protected void onResume() {
        super.onResume();
        setMyList();
    }
}