package com.example.assignmentwongnai;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {
    private List<JSONData> listData;
    private Context context;

    public MyRecyclerViewAdapter(Context context, List<JSONData> listData) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_list_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final JSONData jsonData = listData.get(i);

        //Render image using Picasso library
        if (TextUtils.isEmpty(jsonData.getIconUrl()) != true && jsonData.getIconType() != "vector") {
            Log.d("TAG", "onBindViewHolder: "+jsonData.getIconType()+" "+i);
            Picasso.with(context).load(jsonData.getIconUrl())
                    .into(customViewHolder.iconUrl, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError() {

                        }
                    });
        }
        else {
            Log.d("TAG", "onCCCCC: "+jsonData.getIconUrl());
//            try {
//
////                URL url = new URL(jsonData.getIconUrl().toString());
////                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
////                InputStream inputStream = urlConnection.getInputStream();
////                SVG svg = SVGParser.getSVGFromInputStream(inputStream);
////                Drawable drawable = svg.createPictureDrawable();
////                customViewHolder.iconUrl.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
////                customViewHolder.iconUrl.setImageDrawable(drawable);
//            } catch (Exception e) {
//                Log.e("MainActivity", e.getMessage(), e);
//            }

        }

        //Setting text view title
        customViewHolder.name.setText(jsonData.name);
        customViewHolder.description.setText(jsonData.description);
    }

    @Override
    public int getItemCount() {
        return (null != listData ? listData.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView iconUrl;
        protected TextView name;
        protected TextView description;

        public CustomViewHolder(View view) {
            super(view);
            this.iconUrl = (ImageView) view.findViewById(R.id.iconCoins);
            this.name = (TextView) view.findViewById(R.id.name);
            this.description = (TextView) view.findViewById(R.id.description);
        }
    }
}
