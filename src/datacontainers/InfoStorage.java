package datacontainers;

public class InfoStorage {
	private double day;
	private double numSick;
	private double totalSick;
	private double cost;

	public InfoStorage(double day, double numSick, double totalSick, double cost) {
		this.day = day;
		this.numSick = numSick;
		this.totalSick = totalSick;
		this.cost = cost;
	}

	public void setDay(double day) {
		this.day = day;
	}

	public void setNumSick(double numSick) {
		this.numSick = numSick;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public void setTotalSick(double totalSick) {
		this.totalSick = totalSick;
	}

	public double getDay() {
		return day;
	}

	public double getNumSick() {
		return numSick;
	}

	public double getCost() {
		return cost;
	}

	public double getTotalSick() {
		return totalSick;
	}
}