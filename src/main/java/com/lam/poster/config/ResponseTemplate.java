package com.lam.poster.config;


/**
 * 统一的接口返回数据结构模版
 *
 * @author Roger_Luo
 *
 */
public class ResponseTemplate<T> {
    // 默认成功调用时返回的返回码
    public static final Integer SUCCESS_RETURN_CODE = 0;

    private int code;

    private String message;

    private T data;

    public ResponseTemplate(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    public ResponseTemplate(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 成功之后默认返回的对象
    public ResponseTemplate(T data) {
        this.code = SUCCESS_RETURN_CODE;
        this.message = "OK";
        this.data = data;
    }

    /**
     * Getter & Setter
     *
     * @return
     */
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
