package com.bupt.weeat.model;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class DishBean extends BmobObject implements Serializable {
    private String Name;
    private String Location;
    private int ImageId;
    private String Id;
    private String heat;
    private String praise;
    private String imageUrl;
    private String price;
    private String flavor;
    private String windowId;
    private String windowName;
    private String windowUrl;
    private String updateTime;
    private BmobRelation relation;
    private List<DishBean> data;

    public List<DishBean> getData() {
        return data;
    }

    public void setData(List<DishBean> data) {
        this.data = data;
    }

    public BmobRelation getRelation() {
        return relation;
    }

    public void setRelation(BmobRelation relation) {
        this.relation = relation;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getWindowUrl() {
        return windowUrl;
    }

    public void setWindowUrl(String windowUrl) {
        this.windowUrl = windowUrl;
    }

    public String getWindowName() {

        return windowName;
    }

    public void setWindowName(String windowName) {
        this.windowName = windowName;
    }

    public String getWindowId() {

        return windowId;
    }

    public void setWindowId(String windowId) {
        this.windowId = windowId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }


    public DishBean(String name, String location, int imageId) {
        super();
        Name = name;
        Location = location;
        ImageId = imageId;
    }

    public DishBean(String name, int imageId) {
        Name = name;
        ImageId = imageId;
    }

    public DishBean() {

    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String toString() {
        return "this is DishBean";
    }

}
