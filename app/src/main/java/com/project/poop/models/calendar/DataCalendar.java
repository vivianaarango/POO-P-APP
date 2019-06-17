package com.project.poop.models.calendar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataCalendar {

    @SerializedName("id_calendar")
    @Expose
    private Integer idCalendar;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("id_user")
    @Expose
    private Integer idUser;

    public Integer getIdCalendar() {
        return idCalendar;
    }

    public void setIdCalendar(Integer idCalendar) {
        this.idCalendar = idCalendar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

}