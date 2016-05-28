package com.bupt.weeat.model.response;


import com.bupt.weeat.model.entity.DishBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhaoruolei1992 on 2016/5/21.
 */
public class RecommendationResponse implements Serializable {
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
        private List<DishBean> dish_hotest;

        public List<DishBean> getDish_hotest() {
            return dish_hotest;
        }

        public void setDish_hotest(List<DishBean> dish_hotest) {
            this.dish_hotest = dish_hotest;
        }
    }

}
