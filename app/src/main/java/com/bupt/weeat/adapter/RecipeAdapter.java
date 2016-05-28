package com.bupt.weeat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bupt.weeat.R;
import com.bupt.weeat.model.entity.RecipeBean;
import com.bupt.weeat.utils.LogUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhaoruolei1992 on 2016/5/23.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private Context context;
    private ArrayList<RecipeBean> list;
    private static final String TAG = RecipeAdapter.class.getSimpleName();

    public RecipeAdapter(Context context, ArrayList<RecipeBean> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.recipe_list_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getTitle());
        holder.tag.setText(list.get(position).getTags());
        holder.food.setText(list.get(position).getBurden());
        String path=list.get(position).getSteps().get(0).getImg();
        LogUtils.i(TAG, path);
        Picasso.with(context).load(path)
                .resize(100,100)
                .placeholder(R.drawable.loading_placeholer)
                .config(Bitmap.Config.ARGB_4444)
                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.recipe_image)
        ImageView image;
        @Bind(R.id.recipe_tag)
        TextView tag;
        @Bind(R.id.recipe_name)
        TextView name;
        @Bind(R.id.recipe_food)
        TextView food;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
