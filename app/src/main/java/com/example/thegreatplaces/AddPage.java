package com.example.thegreatplaces;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.Calendar;

public class AddPage extends AppCompatActivity {

//************* Initialize View Widgets ******************
    EditText placeTitle;
    TextView dateText;
    Button camera_btn,map_btn,add_btn;
    TextView address_text;
    ImageView imagePlace;

    //************* SQL ***************
    long id;
    //********************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);
        SQLiteHelper sqLiteHelper = new SQLiteHelper(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id = getIntent().getLongExtra(SQLiteHelper.r_id,0);
        initView();




        //******* Date Picker *********//

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        dateText = findViewById(R.id.dateTxt);
        dateText.setText(currentDate);


        //************* Start Camera After Clicking on Camera Btn **********************
        camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermissions();
            }
        });
        //****** Open Maps After Clicking Camera Btn ***************************
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent map_intent = new Intent(AddPage.this,MapsActivity.class);
                startActivityForResult(map_intent,1);



            }
        });

        //********** Send data Back to main Activity *******************
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //************* Get Values inTO DATABASE **********************
                if (imagePlace.getDrawable() == null) {
                    Toast.makeText(AddPage.this, "Capture Image", Toast.LENGTH_SHORT).show();
                } else {
                    String title = placeTitle.getText().toString().trim();
                    byte[] images = imagePlaceToByte();
                    String date = dateText.getText().toString().trim();
                    String address = address_text.getText().toString().trim();

                    ContentValues values = new ContentValues();
                    values.put(SQLiteHelper.r_title, title);
                    values.put(SQLiteHelper.r_image, images);
                    values.put(SQLiteHelper.r_date, date);
                    values.put(SQLiteHelper.r_address, address);

                    if (title.equals("") || address.isEmpty()) {
                        Toast.makeText(AddPage.this, "Fill Title Or Address", Toast.LENGTH_LONG).show();
                    } else {
                        sqLiteHelper.insertData(values);
                        Toast.makeText(AddPage.this, "Added Successfully", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
        });


    }

    private byte[] imagePlaceToByte() {
        Bitmap bitmap = ((BitmapDrawable)imagePlace.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void initView(){
        placeTitle = findViewById(R.id.placetitle);
        dateText = findViewById(R.id.dateTxt);
        add_btn = findViewById(R.id.add_btn);
        address_text = findViewById(R.id.addressText);
        camera_btn = findViewById(R.id.picture_button);
        imagePlace = findViewById(R.id.imagePlace);
        map_btn = findViewById(R.id.mapActivity);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //*************** Received data from Camera and place it in Add Page *************
        if(requestCode==0){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imagePlace.setImageBitmap(bitmap);
        }

        //************* Received data from Maps and place it in Add Page **************
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                String address = data.getStringExtra("address");
                address_text.setText(address);
            }
        }
    }

    //**********Asking For Camera Permission*************
    private void askCameraPermissions(){

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},0);
        }else {
            openCamera();
        }

    }

    //**********Asking For Camera Permission*************
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==0){
            if (grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                openCamera();
            } else{
              Toast.makeText(this,"Camera Permission Needed",Toast.LENGTH_LONG).show();
            }
        }

    }

    //**********Open Camera After Permission*************
    private void openCamera() {
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera_intent,0);
    }


}