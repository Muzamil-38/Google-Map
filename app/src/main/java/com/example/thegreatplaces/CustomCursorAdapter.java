package com.example.thegreatplaces;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomCursorAdapter extends CursorAdapter {

    private LayoutInflater layoutInflater;
    private Bitmap bitmap;


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.list_row,viewGroup,false);
        MyHolder holder = new MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.listID);
        holder.title = (TextView)v.findViewById(R.id.title);
        holder.imagePlace = (ImageView) v.findViewById(R.id.imageView);
        holder.date = (TextView)v.findViewById(R.id.date);
        holder.address = (TextView)v.findViewById(R.id.address);

        v.setTag(holder);


        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        MyHolder holder = (MyHolder)view.getTag();
        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.r_id)));
        holder.title.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.r_title)));
        byte[] img = cursor.getBlob(cursor.getColumnIndex(SQLiteHelper.r_image));
        bitmap = BitmapFactory.decodeByteArray(img,0,img.length);
        holder.imagePlace.setImageBitmap(bitmap);
        holder.date.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.r_date)));
        holder.address.setText(cursor.getString(cursor.getColumnIndex(SQLiteHelper.r_address)));

    }

    class MyHolder{
        TextView ListID,title,date,address;
        ImageView imagePlace;
    }


}
