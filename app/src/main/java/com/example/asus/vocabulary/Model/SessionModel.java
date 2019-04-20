package com.example.asus.vocabulary.Model;

public class SessionModel {
    private int sessionNumber;
    private int runningLvl;
    private String expireDate;

    public SessionModel(int sessionNumber, int runningLvl, String expireDate) {
        this.sessionNumber = sessionNumber;
        this.runningLvl = runningLvl;
        this.expireDate = expireDate;
    }

    @Override
    public String toString() {
        return "SessionModel{" +
                "sessionNumber=" + sessionNumber +
                ", runningLvl=" + runningLvl +
                ", expireDate='" + expireDate + '\'' +
                '}';
    }

    public int getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(int sessionNumber) {
        this.sessionNumber = sessionNumber;
    }

    public int getRunningLvl() {
        return runningLvl;
    }

    public void setRunningLvl(int runningLvl) {
        this.runningLvl = runningLvl;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
}
