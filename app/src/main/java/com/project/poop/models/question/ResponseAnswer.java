package com.project.poop.models.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.poop.models.login.DataLogin;

public class ResponseAnswer {

    @SerializedName("return")
    @Expose
    private Boolean _return;
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
