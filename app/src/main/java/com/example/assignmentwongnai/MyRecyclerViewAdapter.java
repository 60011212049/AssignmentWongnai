package com.example.assignmentwongnai;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
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
        if (TextUtils.isEmpty(jsonData.getIconUrl()) != true && jsonData.getIconType().equals("pixel")) {
            if (!jsonData.getIconUrl().contains(".svg")) {
                Picasso.with(context).load(jsonData.getIconUrl())
                        .into(customViewHolder.iconUrl);
            } else {
                Utils.fetchSvg(context, jsonData.getIconUrl(), customViewHolder.iconUrl);
            }

        } else if (!jsonData.getIconUrl().isEmpty() && jsonData.getIconType().contains("vector")) {
            Utils.fetchSvg(context, jsonData.getIconUrl(), customViewHolder.iconUrl);
        }
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
        protected ProgressBar progressBar;

        public CustomViewHolder(View view) {
            super(view);
            this.iconUrl = (ImageView) view.findViewById(R.id.iconCoins);
            this.name = (TextView) view.findViewById(R.id.name);
            this.description = (TextView) view.findViewById(R.id.description);
            this.progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        }
    }
//    class LoadSVG extends AsyncTask<String, Void, Drawable> {
//
//        @Override
//        protected Drawable doInBackground(String... strings) {
//            try {
//                final URL url = new URL("https://cdn.coinranking.com/bOabBYkcX/bitcoin_btc.svg");
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                InputStream inputStream = urlConnection.getInputStream();
//                SVG svg = SVGParser. getSVGFromInputStream(inputStream);
//                Drawable drawable = svg.createPictureDrawable();
//                return drawable;
//            } catch (Exception e) {
//                Log.e("MainActivity", e.getMessage(), e);
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Drawable drawable) {
//            super.onPostExecute(drawable);
//        }
//    }
}
