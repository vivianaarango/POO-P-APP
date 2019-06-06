package com.project.poop.models.themes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class ResponseTheme {

    @SerializedName("return")
    @Expose
    private Boolean _return;
    @SerializedName("data")
    @Expose
    private List<DataTheme> data = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;

    public Boolean getReturn() {
        return _return;
    }

    public void setReturn(Boolean _return) {
        this._return = _return;
    }

    public List<DataTheme> getData() {
        return data;
    }

    public void setData(List<DataTheme> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}