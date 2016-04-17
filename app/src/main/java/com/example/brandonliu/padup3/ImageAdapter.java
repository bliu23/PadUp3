package com.example.brandonliu.padup3;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ImageAdapter extends BaseAdapter {
    Context context;
    ArrayList<Input> inputList;
    public static final int MONSTERS_DISPLAYED = 5;


    //pass in an arrayList of arrayLists. Outer array list is the arrayList that holds the list of rooms
    //inner arrayList holds each room's data, which are the image urls
    public ImageAdapter(Context context, ArrayList<Input> items) {
        this.context = context;
        this.inputList = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView textView;
        ImageView[] imageViews;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.team_list_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            holder.imageViews = new ImageView[MONSTERS_DISPLAYED];
            holder.imageViews[0] = (ImageView) convertView.findViewById(R.id.imageView1);
            holder.imageViews[1] = (ImageView) convertView.findViewById(R.id.imageView2);
            holder.imageViews[2] = (ImageView) convertView.findViewById(R.id.imageView3);
            holder.imageViews[3] = (ImageView) convertView.findViewById(R.id.imageView4);
            holder.imageViews[4] = (ImageView) convertView.findViewById(R.id.imageView5);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Bitmap[] imageBitmaps = new Bitmap[MONSTERS_DISPLAYED];
        for(int i = 0; i < MONSTERS_DISPLAYED; i++) {
            imageBitmaps[i] = null;
        }

        holder.textView.setText("Room ID: " + '\n' + inputList.get(position).getRoomId());
        //load images directly
        for (int i = 0; i < MONSTERS_DISPLAYED; i++) {
            try {
                URL imageURL = new URL(inputList.get(position).getImgs().get(i));
                Log.d("testurl", inputList.get(position).getImgs().get(i));
                imageBitmaps[i] = BitmapFactory.decodeStream(imageURL.openStream());
                holder.imageViews[i].setImageBitmap(imageBitmaps[i]);
            } catch (IOException e) {
                // TODO: handle exception
                Log.e("error", "Downloading Image Failed");
                //holder.imageViews[i].setImageResource(R.drawable.postthumb_loading);


            }
        }
        return convertView;
    }
    @Override
    public int getCount() {
        return inputList.size();
    }

    @Override
    public Object getItem(int position) {
        return inputList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return inputList.indexOf(getItem(position));
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}