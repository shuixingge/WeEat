package com.bupt.weeat.model.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class Post extends BmobObject implements Serializable {


    private User author;
    private String content;
    private BmobFile postImageFile;
    private int shareNum;
    private int commentNum;
    private int praiseNum;
    private boolean myPraise;

    public boolean isMyPraise() {
        return myPraise;
    }

    public void setMyPraise(boolean myPraise) {
        this.myPraise = myPraise;
    }

    private boolean myZan;
    private BmobRelation relation;

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public BmobRelation getRelation() {
        return relation;
    }

    public void setRelation(BmobRelation relation) {
        this.relation = relation;
    }

    public boolean isMyZan() {
        return myZan;
    }

    public void setMyZan(boolean myZan) {
        this.myZan = myZan;
    }



    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BmobFile getPostImageFile() {
        return postImageFile;
    }

    public void setPostImageFile(BmobFile postImageFile) {
        this.postImageFile = postImageFile;
    }

    public int getShareNum() {
        return shareNum;
    }

    public void setShareNum(int shareNum) {
        this.shareNum = shareNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }




}
