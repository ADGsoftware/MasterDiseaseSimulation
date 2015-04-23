package datacontainers;

public class DayStat {
    private int day;
    private double currentSick;
    private double totalSick;
    private double cost;
    private double immune;

    public DayStat(int day, double currentSick, double totalSick, double cost, double immune) {
        this.day = day;
        this.currentSick = currentSick;
        this.totalSick = totalSick;
        this.cost = cost;
        this.immune = immune;
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
    public void setNumImmune(double immuneNum){
		this.immune = immuneNum;
	}
	public double getImmune(){
		return this.immune;
	}
}