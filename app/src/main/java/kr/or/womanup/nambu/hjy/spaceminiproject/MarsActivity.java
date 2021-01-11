package kr.or.womanup.nambu.hjy.spaceminiproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MarsActivity extends AppCompatActivity {
    private static final String END_POINT_MARS = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=2015-6-3&api_key=%s";
    private static final String API_KEY_APOD = "key";

    RecyclerView recyclerView;
    MarsInfoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars);

        recyclerView = findViewById(R.id.recycler_mars);
        adapter = new MarsInfoAdapter(this,R.layout.item_mars);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
                DividerItemDecoration.VERTICAL));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String json = search("");
                parsing(json);
            }
        });
        thread.start();
    }

    public String search(String query) {
        //api검색
        String strURL = String.format(END_POINT_MARS, API_KEY_APOD);
        String result = null;
        String str;
        try {
            URL url = new URL(strURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            if (con.getResponseCode() == con.HTTP_OK) {
                InputStreamReader streamReader = new InputStreamReader(con.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                result = buffer.toString();
            }
        } catch (IOException e) {
            System.out.println("예외 발생");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    void parsing(String json){
        try {
            JSONObject root = new JSONObject(json);
            JSONArray photos = root.getJSONArray("photos");

            for (int i=0; i<photos.length();i++){
                JSONObject info = photos.getJSONObject(i);
                String img_src= info.getString("img_src");

                String earth_date = info.getString("earth_date");

                JSONObject camera = info.getJSONObject("camera");
                String cameraName = camera.getString("full_name");

                JSONObject rover = info.getJSONObject("rover");
                String roverName = rover.getString("name");

                Mars newMars = new Mars(roverName,cameraName,img_src,earth_date);
                adapter.addItem(newMars);
                System.out.println(roverName+" "+cameraName+" "+cameraName+img_src);
            }

            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });


        } catch (JSONException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}