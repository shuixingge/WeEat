package com.bupt.weeat.model.entity;


import cn.bmob.v3.BmobObject;

public class Comment extends BmobObject {
    private User user;
    private String commentContent;
    private Post post;
    private DishBean dishObject;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public DishBean getDishObject() {
        return dishObject;
    }

    public void setDishObject(DishBean dishObject) {
        this.dishObject = dishObject;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
