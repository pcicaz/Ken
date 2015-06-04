package com.sc.test.bean;

import java.io.Serializable;

/**
 * @author steven
 * @date 5/21/15
 */
public class ResponseMessage implements Serializable {
    public static int ERR_NOT_LOGGED = 1;
    public static int ERR_UNAUTHORIZED = 2;
    public static int ERR_NOT_FOUND = 3;
    public static int ERR_DUPLICATE = 4;
    public static int ERR_UNKNOWN = 99;
    public static int WARNING = 100;

    private boolean success;
    private int code;
    private String message;
    private Object obj;

    public static ResponseMessage success(Object obj) {
        return new ResponseMessage(true, obj);
    }

    public static ResponseMessage success(String message, Object obj) {
        return new ResponseMessage(true, message, obj);
    }

    public static ResponseMessage success(String message) {
        return new ResponseMessage(true, message);
    }

    public static ResponseMessage success() {
        return new ResponseMessage(true);
    }

    public static ResponseMessage warning(String message) {
        return new ResponseMessage(true, WARNING, message);
    }

    public static ResponseMessage error(int code, String message) {
        return new ResponseMessage(false, code, message);
    }

    public static ResponseMessage error(String message) {
        return new ResponseMessage(false, ERR_UNKNOWN, message);
    }

    public static ResponseMessage error(int code) {
        return new ResponseMessage(false, code);
    }


    private ResponseMessage(boolean success, String message, Object obj) {
        this.success = success;
        this.message = message;
        this.obj = obj;
    }

    private ResponseMessage(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    private ResponseMessage(boolean success, int code) {
        this.success = success;
        this.code = code;
    }

    private ResponseMessage(boolean success, Object obj) {
        this.success = success;
        this.obj = obj;
    }

    private ResponseMessage(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
