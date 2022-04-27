package com.example.thegreatplaces;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewItemActivity extends AppCompatActivity {
    TextView viewTitle , viewAddress,viewDate;
    ImageView viewImage;

    SQLiteHelper sqLiteHelper;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        sqLiteHelper = new SQLiteHelper(this);

        id=getIntent().getLongExtra(SQLiteHelper.r_id,0);
        inIt();
        getData();



    }



    private void inIt(){
        viewTitle=findViewById(R.id.viewTitle);
        viewImage=findViewById(R.id.viewImage);
        viewAddress=findViewById(R.id.viewAddress);
        viewDate=findViewById(R.id.viewDate);

    }

    private void getData(){
        Cursor cursor = sqLiteHelper.oneData(id);
        if (cursor.moveToFirst()){
            String title = cursor.getString(cursor.getColumnIndex(SQLiteHelper.r_title));
            byte[] images = cursor.getBlob(cursor.getColumnIndex(SQLiteHelper.r_image));
            String date = cursor.getString(cursor.getColumnIndex(SQLiteHelper.r_date));
            String address = cursor.getString(cursor.getColumnIndex(SQLiteHelper.r_address));
            Bitmap bitmap = BitmapFactory.decodeByteArray(images, 0,
                    images.length);

            viewImage.setImageBitmap(bitmap);
            viewTitle.setText(title);
            viewDate.setText(date);
            viewAddress.setText(address);


        }
    }


}