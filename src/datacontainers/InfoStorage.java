package datacontainers;

public class InfoStorage {
    private int day;
    private int numSick;
    private int cost;

    public InfoStorage(int day, int numSick, int cost) {
        this.day = day;
        this.numSick = numSick;
        this.cost = cost;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setNumSick(int numSick) {
        this.numSick = numSick;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDay() {
        return day;
    }

    public int getNumSick() {
        return numSick;
    }

    public int getCost() {
        return cost;
    }
}