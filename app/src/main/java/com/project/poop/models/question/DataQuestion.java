package com.project.poop.models.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataQuestion {

    @SerializedName("id_game")
    @Expose
    private String id_game;

    @SerializedName("id_question")
    @Expose
    private String id_question;

    @SerializedName("question")
    @Expose
    private String question;

    @SerializedName("answers")
    @Expose
    private ArrayList<Answer>  answers;

    @SerializedName("progress")
    @Expose
    private Progress progress;


    public String getId_game() {
        return id_game;
    }

    public void setId_game(String id_game) {
        this.id_game = id_game;
    }

    public String getId_question() {
        return id_question;
    }

    public void setId_question(String id_question) {
        this.id_question = id_question;
    }

    public String getQuestion() { return question; }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<Answer> getAnswer() {
        return answers;
    }

    public void setAnswer(ArrayList<Answer> answer) {
        this.answers = answers;
    }

    public Progress getProgress() {
        return progress;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
    }



}
