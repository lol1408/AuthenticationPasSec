package com.dante.passec.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * Class customer response.
 */
public class ResponseBody<T> {

    String msg;
    String code;
    @JsonIgnore
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
}
