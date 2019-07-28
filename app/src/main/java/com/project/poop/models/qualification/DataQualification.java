package com.project.poop.models.qualification;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataQualification {

    @SerializedName("id_subject")
    @Expose
    private Integer idSubject;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id_user")
    @Expose
    private Integer idUser;
    @SerializedName("cuts")
    @Expose
    private List<DataCut> cuts = null;
    @SerializedName("total")
    @Expose
    private Double total;

    public Integer getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(Integer idSubject) {
        this.idSubject = idSubject;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public List<DataCut> getCuts() {
        return cuts;
    }

    public void setCuts(List<DataCut> cuts) {
        this.cuts = cuts;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}