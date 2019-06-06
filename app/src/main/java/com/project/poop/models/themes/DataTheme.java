package com.project.poop.models.themes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DataTheme {

    @SerializedName("id_theme")
    @Expose
    private Integer idTheme;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("items")
    @Expose
    private List<Items> items = null;

    public Integer getIdTheme() {
        return idTheme;
    }

    public void setIdTheme(Integer idTheme) {
        this.idTheme = idTheme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

}
