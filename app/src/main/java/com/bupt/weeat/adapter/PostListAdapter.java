package com.bupt.weeat.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.bupt.weeat.R;
import com.bupt.weeat.model.entity.Post;
import com.bupt.weeat.model.entity.User;
import com.bupt.weeat.utils.ImageLoaderUtil;
import com.bupt.weeat.utils.ImageRequest;
import com.bupt.weeat.utils.LogUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> implements View.OnClickListener {
    private OnFeedItemClickListener onFeedItemClickListener;
    private Context mContext;
    private List<Post> postList;
    private static final String TAG = PostListAdapter.class.getSimpleName();

    public PostListAdapter(Context mContext, List<Post> postList) {
        this.mContext = mContext;
        this.postList = postList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_list_item_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.praiseImg.setOnClickListener(this);
        holder.commentImg.setOnClickListener(this);
        holder.postImageList.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Post post = postList.get(position);
        User user = post.getAuthor();
        holder.postContent.setText(post.getContent());
        try {
            if (user.getUserImage() != null) {
                String avatarUrl = user.getUserImage().getFileUrl(mContext);
                LogUtils.i(TAG, avatarUrl);
                if (avatarUrl != null) {
                    ImageRequest request = new ImageRequest.Builder()
                            .url(avatarUrl)
                            .width(160)
                            .height(120)
                            .placeHolder(R.drawable.loading_placeholer)
                            .imageView(holder.userAvatar).build();
                    ImageLoaderUtil.getInstance().loadImage(mContext, request);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.userName.setText(user.getUsername());
        LogUtils.i(TAG, user.getUsername() + "Username");
        holder.postCreateTime.setText(post.getCreatedAt());
        LogUtils.i(TAG, post.getCreatedAt() + "CreatedAt");
        int currentLikesCount = post.getPraiseNum();
        holder.likeCounterSwitcher.setText(currentLikesCount + " " + "赞");
        int currentCommentsCount = post.getCommentNum();
        holder.commentCounterSwitcher.setText(currentCommentsCount + " " + "评论");
        int currentSharesCount = post.getShareNum();
        holder.shareCounterSwitcher.setText(currentSharesCount + " " + "分享");
        try {
            if (post.getPostImageFile() != null) {
                holder.postImageList.setVisibility(View.VISIBLE);
                String imageUri = post.getPostImageFile().getFileUrl(mContext);
                Picasso.with(mContext).
                        load(imageUri)
                        .resize(60, 60)
                        .centerCrop()
                        .into(holder.pic1);
            } else {
                holder.postImageList.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (post.isMyPraise()) {
            holder.praiseImg.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.praiseImg.setImageResource(R.drawable.ic_heart_outline_grey);
        }

        holder.praiseImg.setTag(R.id.tag_holder, holder);
        holder.praiseImg.setTag(R.id.tag_position, position);
        holder.commentImg.setTag(R.id.tag_holder, holder);
        holder.commentImg.setTag(R.id.tag_position, position);
        holder.shareImg.setTag(R.id.tag_holder, holder);
        holder.shareImg.setTag(R.id.tag_position, position);
        holder.userAvatar.setTag(position);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.square_praise_post_iv:
                if (onFeedItemClickListener != null) {
                    int position = (Integer) view.getTag(R.id.tag_position);
                    onFeedItemClickListener.onPraiseClick(view, position);

                }
                break;
            case R.id.square_comment_post_iv:
                try {
                    if (onFeedItemClickListener != null) {
                        int position = (Integer) view.getTag(R.id.tag_position);
                        onFeedItemClickListener.onCommentsClick(view, position);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.square_share_post_iv:
                if (onFeedItemClickListener != null) {
                    int position = (Integer) view.getTag(R.id.tag_position);
                    onFeedItemClickListener.onShareClick(view, position);
                }
                break;
            case R.id.square_user_avatar_iv:
                if (onFeedItemClickListener != null) {
                    int position = (Integer) view.getTag();
                    onFeedItemClickListener.onUserAvatarClick(view, position);
                }
                break;
            default:
                break;


        }

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.pic1)
        ImageView pic1;
        @Bind(R.id.square_user_avatar_iv)
        ImageView userAvatar;
        @Bind(R.id.square_praise_post_iv)
        public ImageView praiseImg;
        @Bind(R.id.square_comment_post_iv)
        ImageView commentImg;
        @Bind(R.id.square_share_post_iv)
        ImageView shareImg;
        @Bind(R.id.square_user_name_tv)
        TextView userName;
        @Bind(R.id.square_post_content)
        TextView postContent;
        @Bind(R.id.square_post_create_time)
        TextView postCreateTime;
        @Bind(R.id.square_post_image_list)
        LinearLayout postImageList;
        @Bind(R.id.praise_post_counter)
        public  TextSwitcher likeCounterSwitcher;
        @Bind(R.id.comment_post_counter)
        TextSwitcher commentCounterSwitcher;
        @Bind(R.id.share_post_counter)
        public TextSwitcher shareCounterSwitcher;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public void setOnFeedItemClickListener(OnFeedItemClickListener onFeedItemClickListener) {
        this.onFeedItemClickListener = onFeedItemClickListener;
    }


    public interface OnFeedItemClickListener {
        void onCommentsClick(View v, int position);

        void onShareClick(View v, int position);

        void onPraiseClick(View v, int position);

        void onUserAvatarClick(View v, int position);
    }
}
