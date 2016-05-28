package com.bupt.weeat.model.entity;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by zhaoruolei1992 on 2016/5/21.
 */
public class DishBean  extends BmobObject implements Serializable {
    private String id;
    private String name;
    private String coverId;
    private String windowId;
    private String tastes;
    private String price;
    private String praise;
    private String updateTime;
    private String heat;
    private DishCover Dish_Cover;
    private DishWindow Dish_Window;
    private String  timeInterval;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverId() {
        return coverId;
    }

    public void setCoverId(String coverId) {
        this.coverId = coverId;
    }

    public String getTastes() {
        return tastes;
    }

    public void setTastes(String tastes) {
        this.tastes = tastes;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPraise() {
        return praise;
    }

    public void setPraise(String praise) {
        this.praise = praise;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    public DishCover getDish_Cover() {
        return Dish_Cover;
    }

    public void setDish_Cover(DishCover dish_Cover) {
        Dish_Cover = dish_Cover;
    }

    public DishWindow getDish_Window() {
        return Dish_Window;
    }

    public void setDish_Window(DishWindow dish_Window) {
        Dish_Window = dish_Window;
    }
    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getWindowId() {
        return windowId;
    }

    public void setWindowId(String windowId) {
        this.windowId = windowId;
    }

    public static class DishCover implements Serializable{
        private String id;
        private String name;
        private String albumId;
        private String type;
        private String path;
        private String uploadTime;
        private String updateTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlbumId() {
            return albumId;
        }

        public void setAlbumId(String albumId) {
            this.albumId = albumId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getUploadTime() {
            return uploadTime;
        }

        public void setUploadTime(String uploadTime) {
            this.uploadTime = uploadTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }

    public static class DishWindow implements Serializable {
        private String id;
        private String name;
        private String coverId;
        private String location;
        private String point;
        private String tags;
        private String servingTime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCoverId() {
            return coverId;
        }

        public void setCoverId(String coverId) {
            this.coverId = coverId;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getServingTime() {
            return servingTime;
        }

        public void setServingTime(String servingTime) {
            this.servingTime = servingTime;
        }
    }
}
