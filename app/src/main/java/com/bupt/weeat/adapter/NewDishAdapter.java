package com.bupt.weeat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bupt.weeat.R;
import com.bupt.weeat.model.entity.DishBean;
import com.bupt.weeat.utils.ImageLoaderUtil;
import com.bupt.weeat.utils.ImageRequest;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class NewDishAdapter extends RecyclerView.Adapter<NewDishAdapter.ViewHolder> {
    private ArrayList<DishBean> list;
    private Context context;


    public NewDishAdapter(ArrayList<DishBean> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.new_dish_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.NewDishLocation.setText(list.get(position).getDish_Window().getLocation());
        holder.NewDishName.setText(list.get(position).getName());
        holder.NewDishPrice.setText(list.get(position).getPrice() + "元");
        holder.NewDishUpdateTime.setText(list.get(position).getUpdateTime());
        String path = list.get(position).getDish_Cover().getPath();
        if (path != null) {
            ImageRequest request = new ImageRequest.Builder()
                    .url(path)
                    .width(160)
                    .height(120)
                    .placeHolder(R.drawable.loading_placeholer)
                    .imageView(holder.NewDishImage).build();

            ImageLoaderUtil.getInstance().loadImage(context, request);

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.new_dish_location)
        TextView NewDishLocation;
        @Bind(R.id.new_dish_name)
        TextView NewDishName;
        @Bind(R.id.new_dish_price)
        TextView NewDishPrice;
        @Bind(R.id.new_dish_update_time)
        TextView NewDishUpdateTime;
        @Bind(R.id.new_dish_image)
        ImageView NewDishImage;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }
}
