package com.project.poop.models.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Progress {

    @SerializedName("correct")
    @Expose
    private String correct;

    @SerializedName("fail")
    @Expose
    private String fail;

    @SerializedName("total")
    @Expose
    private String total;

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getFail() {
        return fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
