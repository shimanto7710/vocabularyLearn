package com.example.asus.vocabulary.Model;

/**
 * Created by Shimanto Ahmed on 12/23/2017.
 */

public class Word {
    private int id;
    private String engWord;
    private String bangWord;
    private String bngSyn;
    private String engSyn;
    private String example;
    private String engPron;
    private String bangPron;
    private String type;
    private String definition;
    private String antonyms;
    private int session;
    private int mood;
    private int dif;
    private int learn;

    public Word(){}
    public Word(int id, String engWord, String bangWord, String bngSyn, String engSyn, String example, String engPron, String bangPron, String type, String definition, String antonyms, int session, int mood) {
        this.id = id;
        this.engWord = engWord;
        this.bangWord = bangWord;
        this.bngSyn = bngSyn;
        this.engSyn = engSyn;
        this.example = example;
        this.engPron = engPron;
        this.bangPron = bangPron;
        this.type = type;
        this.definition = definition;
        this.antonyms = antonyms;
        this.session = session;
        this.mood = mood;
    }

    public Word(int id, String engWord, String bangWord, String bngSyn, String engSyn, String example, String engPron, String bangPron, String type, String definition, String antonyms, int session, int mood, int dif, int learn) {
        this.id = id;
        this.engWord = engWord;
        this.bangWord = bangWord;
        this.bngSyn = bngSyn;
        this.engSyn = engSyn;
        this.example = example;
        this.engPron = engPron;
        this.bangPron = bangPron;
        this.type = type;
        this.definition = definition;
        this.antonyms = antonyms;
        this.session = session;
        this.mood = mood;
        this.dif = dif;
        this.learn = learn;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", engWord='" + engWord + '\'' +
                ", bangWord='" + bangWord + '\'' +
                ", bngSyn='" + bngSyn + '\'' +
                ", engSyn='" + engSyn + '\'' +
                ", example='" + example + '\'' +
                ", engPron='" + engPron + '\'' +
                ", bangPron='" + bangPron + '\'' +
                ", type='" + type + '\'' +
                ", definition='" + definition + '\'' +
                ", antonyms='" + antonyms + '\'' +
                ", session=" + session +
                ", mood=" + mood +
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

    public String getBngSyn() {
        return bngSyn;
    }

    public void setBngSyn(String bngSyn) {
        this.bngSyn = bngSyn;
    }

    public String getEngSyn() {
        return engSyn;
    }

    public void setEngSyn(String engSyn) {
        this.engSyn = engSyn;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getEngPron() {
        return engPron;
    }

    public void setEngPron(String engPron) {
        this.engPron = engPron;
    }

    public String getBangPron() {
        return bangPron;
    }

    public void setBangPron(String bangPron) {
        this.bangPron = bangPron;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(String antonyms) {
        this.antonyms = antonyms;
    }

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public int getDif() {
        return dif;
    }

    public void setDif(int dif) {
        this.dif = dif;
    }

    public int getLearn() {
        return learn;
    }

    public void setLearn(int learn) {
        this.learn = learn;
    }
}
