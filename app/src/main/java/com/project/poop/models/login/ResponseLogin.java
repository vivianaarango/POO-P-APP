package com.project.poop.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin {

    @SerializedName("return")
    @Expose
    private Boolean _return;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private DataLogin data;
    @SerializedName("status")
    @Expose
    private Integer status;

    /**
     *
     * @return
     *     The _return
     */
    public Boolean getReturn() {
        return _return;
    }

    /**
     *
     * @param _return
     *     The return
     */
    public void setReturn(Boolean _return) {
        this._return = _return;
    }

    /**
     *
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }


    /**
     *
     * @return
     *     The data
     */
    public DataLogin getData() {
        return data;
    }

    /**
     *
     * @param data
     *     The data
     */
    public void setData(DataLogin data) {
        this.data = data;
    }

    /**
     *
     * @return
     *     The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     *
     * @param status
     *     The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

}
