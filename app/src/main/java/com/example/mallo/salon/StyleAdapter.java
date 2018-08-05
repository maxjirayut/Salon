package com.example.mallo.salon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mallo.salon.Model.StyleModel;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class StyleAdapter extends BaseAdapter {

    List<StyleModel> listStyle;
    Context ct;

    public StyleAdapter(List<StyleModel> listStyle, Context ct) {
        this.listStyle = listStyle;
        this.ct = ct;
    }

    @Override
    public int getCount() {
        return listStyle.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.style_layout, parent, false);

        ImageView imageView = convertView.findViewById(R.id.imgstyle);
        TextView tvstyle_name = convertView.findViewById(R.id.tvstyle_name);

        tvstyle_name.setText(listStyle.get(position).getStyle_name());

        String photo_url_str = "http://192.168.1.58/salon/img/";

        new DownLoadImageTask(imageView).execute(photo_url_str+listStyle.get(position).getStyle_img());

        return convertView;
    }

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }


        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}
