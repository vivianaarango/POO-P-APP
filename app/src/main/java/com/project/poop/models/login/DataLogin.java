package com.project.poop.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataLogin {

    @SerializedName("id_user")
    @Expose
    private String id_user;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("difficulty")
    @Expose
    private ArrayList<Difficulty>  difficulty;


    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<Difficulty> getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(ArrayList<Difficulty> difficulty) {
        this.difficulty = difficulty;
    }



}
