package com.bupt.weeat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bupt.weeat.R;
import com.bupt.weeat.model.entity.DishBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WeekRankAdapter extends RecyclerView.Adapter<WeekRankAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DishBean> list;
    private static final String TAG = WeekRankAdapter.class.getSimpleName();

    public WeekRankAdapter(Context context, ArrayList<DishBean> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(context).inflate(R.layout.week_rank_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.weekDishIndex.setText((position + 1) + "");
        viewHolder.weekDishName.setText(list.get(position).getName());
        viewHolder.weekDishPraise.setText(list.get(position).getPraise());
        String path = list.get(position).getDish_Cover().getPath();
        if (path != null) {
            Picasso.with(context)
                    .load(path)
                    .centerCrop()
                    .resize(160, 120)
                    .into(viewHolder.weekDishImage);
        }
        viewHolder.praiseNumLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNum = (String) viewHolder.weekDishPraise.getText();
                int intNum = Integer.parseInt(strNum);
                viewHolder.weekDishPraise.setText(++intNum + "");

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.week_dish_index)
        TextView weekDishIndex;
        @Bind(R.id.week_dish_name)
        TextView weekDishName;
        @Bind(R.id.week_dish_praise_num)
        TextView weekDishPraise;
        @Bind(R.id.week_dish_image)
        ImageView weekDishImage;
        @Bind(R.id.praiseNumLayout)
        LinearLayout praiseNumLayout;
        @Bind(R.id.week_dish_praise_image)
        ImageView weekDishPraiseImage;
        @Bind(R.id.select_dish_detail)
        ImageView selectDetail;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }

}
