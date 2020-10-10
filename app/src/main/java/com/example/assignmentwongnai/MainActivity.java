package com.example.assignmentwongnai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    List<JSONData> listData = new ArrayList<>();
    MyRecyclerViewAdapter adapter;
    RecyclerView mRecyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        init();
    }

    //ดึงข้อมูลจาก api
    public void init() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.coinranking.com/v1/public/coins", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                String result = null;
                try {
                    result = new String(response, "UTF-8");
                    JSONObject obj = new JSONObject(result);
                    JSONObject dataArray = obj.getJSONObject("data");
                    JSONArray coinsArray = dataArray.getJSONArray("coins");
                    for (int i = 0; i < coinsArray.length(); i++) {
                        JSONObject dataObj = coinsArray.getJSONObject(i);
                        JSONData jsonData = new JSONData();
                        jsonData.setIconUrl(dataObj.getString("iconUrl"));
                        jsonData.setName(dataObj.getString("name"));
                        jsonData.setIconType(dataObj.getString("iconType"));
                        jsonData.setDescription(Html.fromHtml(dataObj.getString("description")).toString());
                        listData.add(jsonData);
                    }
                    //นำข้อมูล coins ที่ได้ไปเซ็ตแสดงผลในคลาส MyRecyclerViewAdapter
                    adapter = new MyRecyclerViewAdapter(MainActivity.this, listData);
                    mRecyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);

                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                Log.d("TAG", "onFailure: " + statusCode);
            }
        });
    }
}