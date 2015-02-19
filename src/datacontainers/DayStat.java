package datacontainers;

public class DayStat {
    private int day;
    private double currentSick;
    private double totalSick;
    private double cost;

    public DayStat(int day, double currentSick, double totalSick, double cost) {
        this.day = day;
        this.currentSick = currentSick;
        this.totalSick = totalSick;
        this.cost = cost;
    }

    //Getters
    public int getDay() {
        return day;
    }

    public double getCurrentSick() {
        return currentSick;
    }

    public double getTotalSick() {
        return totalSick;
    }

    public double getCost() {
        return cost;
    }

    //Setters
    public void setDay(int day) {
        this.day = day;
    }

    public void setCurrentSick(double currentSick) {
        this.currentSick = currentSick;
    }

    public void setTotalSick(double totalSick) {
        this.totalSick = totalSick;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}