package kr.or.womanup.nambu.hjy.spaceminiproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String END_POINT_APOD = "https://api.nasa.gov/planetary/apod?api_key=%s&date=%s"; //비디오:date=2020-12-09
    private static final String API_KEY_APOD = "key";
    SearchView searchView;
    RecyclerView recyclerView; //글용
    RecyclerView recyclerVideo; //비디오용
    InfoAdapter adapter; //메인화면에 제목,날짜,내용 뜨는 정보용
    VideoAdapter videoAdapter;
    String urlV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new InfoAdapter(this,R.layout.item_in_main);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String today = format.format(new Date());
                String json = searchAPOD(today);
                parsingAPOD(json);
                APOD temp = adapter.apods.get(0);
                System.out.println(temp.url);
            }
        });
        thread.start();

        Button button = findViewById(R.id.button);
        button.setText("화성 구경하기");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MarsActivity.class);
                v.getContext().startActivity(intent); //현재 클래스엔 startActivity가 없어서 메인 꺼 씀
            }
        });

/*        if(adapter.apods.get(0).media_type.equals("video")){
            String videoURL= adapter.apods.get(0).url;
            videoAdapter = new VideoAdapter(this,videoURL,R.layout.item_video);
            recyclerVideo = findViewById(R.id.recycler_media);
            recyclerVideo.setAdapter(videoAdapter);

            LinearLayoutManager manager2 = new LinearLayoutManager(this);
            manager2.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerVideo.setLayoutManager(manager2);
        }

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        thread2.start();*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("날짜를 입력: yyyy-MM-dd ");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        String json = searchAPOD(query);
                        parsingAPOD(json);
                    }
                });
                thread.start();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    public String searchAPOD(String query) {
        //api검색
        String strURL = String.format(END_POINT_APOD, API_KEY_APOD,query);
        String result = null;
        String str;
        try {
            URL url = new URL(strURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            if (con.getResponseCode() == con.HTTP_OK) {
                InputStreamReader streamReader = new InputStreamReader(con.getInputStream()); //스트림에 리더 연결
                BufferedReader reader = new BufferedReader(streamReader); //리더에 버퍼 리더 연결
                StringBuffer buffer = new StringBuffer(); //임시저장용
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
        System.out.println(result);
        return result;
    }

    void parsingAPOD(String json){
        try {
            JSONObject root = new JSONObject(json);
            String date = root.getString("date");
            String explanation = root.getString("explanation");
            String media_type = root.getString("media_type");
            String service_version = root.getString("service_version");
            String title = root.getString("title");
            String url = root.getString("url");

            APOD apod = new APOD(date, explanation, media_type, service_version,
                    title, url);
            adapter.addItem(apod);
            System.out.println(apod.media_type);
            System.out.println(apod.url);

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