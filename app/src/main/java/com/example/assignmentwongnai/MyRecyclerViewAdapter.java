package com.example.assignmentwongnai;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        if (!TextUtils.isEmpty(jsonData.getIconUrl())) {
            Picasso.with(context).load(jsonData.getIconUrl())
                    .error(R.drawable.ic_launcher_background)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(customViewHolder.iconUrl);
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
