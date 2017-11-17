package com.xxg.jcatch.api;

/**
 * Created by wucao on 17/3/14.
 */
public class ApiResult {

    private boolean success;
    private Object data;
    public boolean getSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public static ApiResult success() {
        return success(null);
    }
    public static ApiResult success(Object data) {
        ApiResult result = new ApiResult();
        result.setSuccess(true);
        result.setData(data);
        return result;
    }
    public static ApiResult fail() {
        return fail(null);
    }
    public static ApiResult fail(Object data) {
        ApiResult result = new ApiResult();
        result.setSuccess(false);
        result.setData(data);
        return result;
    }
}
