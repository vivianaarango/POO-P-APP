package com.project.poop.models.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Answer {

    @SerializedName("id_answer")
    @Expose
    private String id_answer;

    @SerializedName("answer")
    @Expose
    private String answer;

    @SerializedName("is_correct")
    @Expose
    private String is_correct;

    public String getId_answer() {
        return id_answer;
    }

    public void setId_answer(String id_answer) {
        this.id_answer = id_answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getIs_correct() {
        return is_correct;
    }

    public void setIs_correct(String is_correct) {
        this.is_correct = is_correct;
    }


}
