package com.bupt.weeat.model.response;

import com.bupt.weeat.model.entity.DishBean;
import com.bupt.weeat.model.entity.Window;

import java.util.List;

/**
 * Created by zhaoruolei1992 on 2016/5/22.
 */
public class WeekRankDishResponse {
    private int errno;
    private Data data;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private List<DishBean> dishes;
        private List<Window> windows;

        public List<DishBean> getDishes() {
            return dishes;
        }

        public void setDishes(List<DishBean> dishes) {
            this.dishes = dishes;
        }

        public List<Window> getWindows() {
            return windows;
        }

        public void setWindows(List<Window> windows) {
            this.windows = windows;
        }
    }

}
