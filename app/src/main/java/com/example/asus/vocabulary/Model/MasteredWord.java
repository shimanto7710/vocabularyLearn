package com.example.asus.vocabulary.Model;

public class MasteredWord {
    private int id;
    private String engWord;
    private String bangWord;


    public MasteredWord(int id, String engWord, String bangWord) {
        this.id = id;
        this.engWord = engWord;
        this.bangWord = bangWord;
    }

    @Override
    public String toString() {
        return "MasteredWord{" +
                "id=" + id +
                ", engWord='" + engWord + '\'' +
                ", bangWord='" + bangWord + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEngWord() {
        return engWord;
    }

    public void setEngWord(String engWord) {
        this.engWord = engWord;
    }

    public String getBangWord() {
        return bangWord;
    }

    public void setBangWord(String bangWord) {
        this.bangWord = bangWord;
    }
}
