package com.project.poop.models.themes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Items {

    @SerializedName("id_item")
    @Expose
    private Integer idItem;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private Integer type;

    public Integer getIdItem() {
        return idItem;
    }

    public void setIdItem(Integer idItem) {
        this.idItem = idItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}