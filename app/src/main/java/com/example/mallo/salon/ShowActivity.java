package com.example.mallo.salon;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mallo.salon.Model.SalonModel;
import com.example.mallo.salon.Model.StyleModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShowActivity extends AppCompatActivity {

    String url = "http://192.168.1.58/json/style_json.php";
    ListView listView;
    List<StyleModel> listStyle;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        String id = getIntent().getExtras().getString("A");

        listView = findViewById(R.id.listview);
        swipeRefreshLayout = findViewById(R.id.swipeshow);

        ConnectWebservice syn = new ConnectWebservice();
        syn.execute();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConnectWebservice syn = new ConnectWebservice();
                syn.execute();
            }
        });

    }

    private class ConnectWebservice extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... voids) {

            String result = "";

            OkHttpClient ok = new OkHttpClient();
            Request.Builder builder = new Request.Builder();

            Request request = builder.url(url).build();

            try {
                Response response = ok.newCall(request).execute();
                result = response.body().string();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Type type = new TypeToken<ArrayList<StyleModel>>() {
            }.getType();

            listStyle = new Gson().fromJson(s, type);

            StyleAdapter adapter = new StyleAdapter(listStyle, getApplication());
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    String style_id = listStyle.get(position).getStyle_id();
                    String style_name = listStyle.get(position).getStyle_name();
                    String style_img = listStyle.get(position).getStyle_img();
                    String style_meny = listStyle.get(position).getStyle_meny();
                    String style_detali = listStyle.get(position).getStyle_detail();
                    String salon_id = listStyle.get(position).getSalon_id();

                    Intent intent = new Intent(getApplication(), HairStyleActivity.class);

                    intent.putExtra("ID", style_id);
                    intent.putExtra("NAME", style_name);
                    intent.putExtra("IMG", style_img);
                    intent.putExtra("MENY", style_meny);
                    intent.putExtra("DETAIL", style_detali);
                    intent.putExtra("SALON", salon_id);

                    startActivity(intent);
                }
            });

        }
    }

}
