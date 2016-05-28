package com.bupt.weeat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bupt.weeat.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HealthAdapter extends RecyclerView.Adapter<HealthAdapter.ViewHolder> {
    private int[] imageIds = {R.drawable.ic_heat_statistics, R.drawable.ic_recipe_recommended};
    private String[] textArray = {"热量统计", "食谱推荐"};
    private Context context;

    public HealthAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.heath_fragment_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.heathItemTv.setText(textArray[position]);
        holder.heathItemImg.setImageResource(imageIds[position]);
    }

    @Override
    public int getItemCount() {
        return imageIds.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.heath_item_tv)
        TextView heathItemTv;
        @Bind(R.id.heath_item_iv)
        ImageView heathItemImg;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
