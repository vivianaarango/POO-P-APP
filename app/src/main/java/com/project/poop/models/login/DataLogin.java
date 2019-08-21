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

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("difficulty")
    @Expose
    private ArrayList<Difficulty>  difficulty;

    @SerializedName("result_1")
    @Expose
    private String result_1;

    @SerializedName("result_2")
    @Expose
    private String result_2;

    @SerializedName("result_3")
    @Expose
    private String result_3;

    @SerializedName("result_4")
    @Expose
    private String result_4;


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

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() { return phone; }

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

    public void setResult_1(String result_1) {
        this.result_1 = result_1;
    }

    public String getResult_1() { return result_1; }

    public void setResult_2(String result_2) {
        this.result_2 = result_2;
    }

    public String getResult_2() { return result_2; }

    public void setResult_3(String result_3) {
        this.result_3 = result_3;
    }

    public String getResult_3() { return result_3; }

    public void setResult_4(String result_4) {
        this.result_4 = result_4;
    }

    public String getResult_4() { return result_4; }

}
