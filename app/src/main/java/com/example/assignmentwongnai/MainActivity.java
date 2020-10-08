package com.example.assignmentwongnai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

    TextView txt_head;
    ArrayList<String> listSt = new ArrayList<>();
    ArrayAdapter<String> listAdapter;
    List<JSONData> listData = new ArrayList<>();
    MyRecyclerViewAdapter adapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        txt_head = (TextView) findViewById(R.id.head_text);
        init();
    }

    public void init() {
        Log.d("TAG", "init: start link");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://api.coinranking.com/v1/public/coins", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                String result = null;
                try {
                    result = new String(response, "UTF-8");
                    JSONObject obj = new JSONObject(result);
                    Log.d("TAG", "JSONArray : " + obj.length());
                    JSONObject dataArray = obj.getJSONObject("data");
                    Log.d("TAG", "JSONArrayData : " + dataArray.length());
                    JSONArray coinsArray = dataArray.getJSONArray("coins");
                    Log.d("TAG", "JSONArrayCoins : " + coinsArray.length());
                    for (int i = 0; i < coinsArray.length(); i++) {
                        JSONObject dataObj = coinsArray.getJSONObject(i);
                        JSONData jsonData = new  JSONData();
                        jsonData.setIconUrl(dataObj.getString("iconUrl"));
                        jsonData.setName(dataObj.getString("name"));
                        jsonData.setIconType(dataObj.getString("iconType"));
                        jsonData.setDescription(Html.fromHtml(dataObj.getString("description")).toString());
                        listData.add(jsonData);
//                        String strFormHtml = Html.fromHtml(dataObj.getString("description")).toString();
//                        listSt.add(strFormHtml);
                    }
                    Log.d("TAG", "Data in list ::: "+listData.size());
                    adapter = new MyRecyclerViewAdapter(MainActivity.this,listData);
                    mRecyclerView.setAdapter(adapter);
//                    listAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, listSt);

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