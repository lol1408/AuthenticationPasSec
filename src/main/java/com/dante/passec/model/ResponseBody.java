package com.dante.passec.model;

/**
 * Class customer response.
 */
public class ResponseBody<T> {

    String msg;
    String code;
    T oneResult;


    public void setOneResult(T oneResult) {
        this.oneResult = oneResult;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setResponse(String msg, String code) {this.msg=msg; this.code=code;}

    public String toString() {
        return "AjaxResponseResult [msg=" + msg + ", code=" + code + ", result=" + oneResult + "]";
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public T getOneResult() {
        return oneResult;
    }
}
