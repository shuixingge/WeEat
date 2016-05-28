package com.bupt.weeat.model.response;

import com.bupt.weeat.model.entity.DishBean;

import java.util.List;

/**
 * Created by zhaoruolei1992 on 2016/5/22.
 */
public class NewDishResponse {
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
        private List<DishBean> New_dishes;

        public List<DishBean> getNew_dishes() {
            return New_dishes;
        }

        public void setNew_dishes(List<DishBean> new_dishes) {
            New_dishes = new_dishes;
        }
    }
}
