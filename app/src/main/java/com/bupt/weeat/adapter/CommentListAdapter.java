package com.bupt.weeat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bupt.weeat.R;
import com.bupt.weeat.model.entity.Comment;

import java.util.List;

public class CommentListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Comment> list;

    public CommentListAdapter(Context mContext, List<Comment> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        try {
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.comment_list_item_layout, parent, false);
                holder = new ViewHolder();
                holder.authorName = (TextView) convertView.findViewById(R.id.comment_author_name);
                holder.commentIndex = (TextView) convertView.findViewById(R.id.comment_index);
                holder.commentContent = (TextView) convertView.findViewById(R.id.comment_content_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.authorName.setText(list.get(position).getUser().getUsername());
            holder.commentContent.setText(list.get(position).getCommentContent());
            holder.commentIndex.setText((++position)+ "楼");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView authorName;
        TextView commentContent;
        TextView commentIndex;
    }
}
