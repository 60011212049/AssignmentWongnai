package com.example.assignmentwongnai;

import android.content.Context;
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

    //เซ็ตค่าข้อมูลที่จะแสดง
    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final JSONData jsonData = listData.get(i);

        //เช็คว่าข้อมูลที่ได้มีไหม แล้วก็เช็ค type ของ icon
        if (!jsonData.getIconUrl().isEmpty() && jsonData.getIconType().equals("pixel")) {
            if (!jsonData.getIconUrl().contains(".svg")) {
                Picasso.with(context).load(jsonData.getIconUrl())
                        .into(customViewHolder.iconUrl);
            } else {
                Utils.fetchSvg(context, jsonData.getIconUrl(), customViewHolder.iconUrl);
            }

        } //ส่วนไอคอนที่ type เป็น vector ต้องนำค่าที่ได้ไปแปลงที่คลาส Utils เป็น library ที่หามา
        else if (!jsonData.getIconUrl().isEmpty() && jsonData.getIconType().contains("vector")) {
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
}
