package com.bupt.weeat.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bupt.weeat.R;
import com.bupt.weeat.model.entity.Comment;

import java.util.ArrayList;

public class DishCommentAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Comment> commentList;

    public DishCommentAdapter(Context mContext, ArrayList<Comment> commentList) {
        this.mContext = mContext;
        this.commentList = commentList;
    }
    public ArrayList<Comment>  getDataList(){
        return  commentList;
    }
    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.dish_comment_adapter_layout, parent, false);
            holder = new ViewHolder();
            holder.authorName = (TextView) convertView.findViewById(R.id.dish_comment_author_name);
            holder.commentContent = (TextView) convertView.findViewById(R.id.dish_comment_content_tv);
            holder.commentIndex = (TextView) convertView.findViewById(R.id.dish_comment_index);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.authorName.setText(commentList.get(position).getUser().getUsername());
        holder.commentContent.setText(commentList.get(position).getCommentContent());
        holder.commentIndex.setText((position + 1) + "楼");
        return convertView;
    }

    private class ViewHolder {
        TextView authorName;
        TextView commentContent;
        TextView commentIndex;
    }
}
