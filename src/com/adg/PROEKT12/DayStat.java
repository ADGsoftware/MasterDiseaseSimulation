package com.adg.PROEKT12;

public class DayStat {
    private int day;
    private float currentSick;
    private float totalSick;
    private float cost;

    public DayStat(int day, float currentSick, float totalSick, float cost) {
        this.day = day;
        this.currentSick = currentSick;
        this.totalSick = totalSick;
        this.cost = cost;
    }

    //Getters
    public int getDay() {
        return day;
    }

    public float getCurrentSick() {
        return currentSick;
    }

    public float getTotalSick() {
        return totalSick;
    }

    public float getCost() {
        return cost;
    }

    //Setters
    public void setDay(int day) {
        this.day = day;
    }

    public void setCurrentSick(float currentSick) {
        this.currentSick = currentSick;
    }

    public void setTotalSick(float totalSick) {
        this.totalSick = totalSick;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}
