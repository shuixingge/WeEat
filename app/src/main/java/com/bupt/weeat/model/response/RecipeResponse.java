package com.bupt.weeat.model.response;

import com.bupt.weeat.model.entity.RecipeBean;

import java.util.List;

/**
 * Created by zhaoruolei1992 on 2016/5/23.
 */
public class RecipeResponse {
    private String resultcode;
    private String reason;
    private Result result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class Result {
        private String totalNum;
        private int pn;
        private int rn;
        private  List<RecipeBean> data;

        public String getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(String totalNum) {
            this.totalNum = totalNum;
        }

        public int getPn() {
            return pn;
        }

        public void setPn(int pn) {
            this.pn = pn;
        }

        public int getRn() {
            return rn;
        }

        public void setRn(int rn) {
            this.rn = rn;
        }



        public List<RecipeBean> getData() {
            return data;
        }

        public void setData(List<RecipeBean> data) {
            this.data = data;
        }
    }
}
