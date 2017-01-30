package com.dante.passec.model;

import java.util.List;

/**
 * Class customer response.
 */
public class AjaxResponseBody<T> {

    String msg;
    String code;
    T oneResult;

    public T getOneResult() {
        return oneResult;
    }

    public void setOneResult(T oneResult) {
        this.oneResult = oneResult;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "AjaxResponseResult [msg=" + msg + ", code=" + code + ", result=" + oneResult + "]";
    }
}
