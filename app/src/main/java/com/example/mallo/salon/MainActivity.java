package com.example.mallo.salon;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mallo.salon.Model.SalonModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    String url = "http://192.168.1.58/json/json.php";
    List<SalonModel> listSalon;
    ListView lvWebservice;
    SwipeRefreshLayout swipe ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvWebservice = findViewById(R.id.lvWebservice);
        swipe = findViewById(R.id.swipe);

        ConnectWebservice syn = new ConnectWebservice();
        syn.execute();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConnectWebservice syn = new ConnectWebservice();
                syn.execute();

                swipe.setEnabled(false);
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
            Type type = new TypeToken<ArrayList<SalonModel>>() {
            }.getType();

            listSalon = new Gson().fromJson(s, type);

            MyAdapter adapter = new MyAdapter(listSalon, getApplication());
            lvWebservice.setAdapter(adapter);

//            Toast.makeText(getApplication(), s + listSalon.get(0).getSalon_id(), Toast.LENGTH_SHORT).show();

            lvWebservice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    String salon_id = listSalon.get(position).getSalon_id();
                    String salon_name = listSalon.get(position).getSalon_name();
                    String detail = listSalon.get(position).getDetail();
                    String latitude = listSalon.get(position).getLatitude();
                    String longitude = listSalon.get(position).getLatitude();
                    String user_id = listSalon.get(position).getUser_id();
                    String name = listSalon.get(position).getName();
                    String phone = listSalon.get(position).getPhone();
                    String email = listSalon.get(position).getEmail();

                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    intent.putExtra("A", salon_id);
                    intent.putExtra("B", salon_name);
                    intent.putExtra("C", detail);
                    intent.putExtra("D", latitude);
                    intent.putExtra("E", longitude);
                    intent.putExtra("F", user_id);
                    intent.putExtra("G", name);
                    intent.putExtra("S", phone);
                    intent.putExtra("I", email);
                    startActivity(intent);
                }
            });

        }
    }
}
