package com.project.poop.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Difficulty {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("difficulty")
    @Expose
    private String difficulty;

    @SerializedName("is_approved")
    @Expose
    private String is_approved;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getIs_approved() { return is_approved; }

    public void setIs_approved(String is_approved) {
        this.is_approved = is_approved;
    }


}
