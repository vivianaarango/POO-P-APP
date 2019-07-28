package com.project.poop.models.qualification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataCut {

    @SerializedName("id_qualification")
    @Expose
    private Integer idQualification;
    @SerializedName("cut")
    @Expose
    private String cut;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("value")
    @Expose
    private Double value;
    @SerializedName("is_calculed")
    @Expose
    private Integer isCalculed;

    public Integer getIdQualification() {
        return idQualification;
    }

    public void setIdQualification(Integer idQualification) {
        this.idQualification = idQualification;
    }

    public String getCut() {
        return cut;
    }

    public void setCut(String cut) {
        this.cut = cut;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Integer getIsCalculed() {
        return isCalculed;
    }

    public void setIsCalculed(Integer isCalculed) {
        this.isCalculed = isCalculed;
    }

}