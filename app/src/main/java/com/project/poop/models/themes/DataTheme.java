package com.project.poop.models.themes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataTheme {

    @SerializedName("id_theme")
    @Expose
    private String id_theme;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("items")
    @Expose
    private ArrayList<Items>  items;


    public String getId_theme() {
        return id_theme;
    }

    public void setId_theme(String id_theme) {
        this.id_theme = id_theme;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Items> getItems() {
        return items;
    }

    public void setItems(ArrayList<Items> items) { this.items = items; }

}
