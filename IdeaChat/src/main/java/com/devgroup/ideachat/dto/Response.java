package com.devgroup.ideachat.dto;

public class Response<T> {

    private int status;
    private String message;
    private T object;

    public Response() {
    }

    public Response(int status, String message, T object) {
        this.status = status;
        this.message = message;
        this.object = object;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "Response [status=" + status + ", message=" + message + ", object=" + object + "]";
    }

}
