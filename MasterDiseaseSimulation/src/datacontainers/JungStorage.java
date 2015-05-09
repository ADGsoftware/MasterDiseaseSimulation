package datacontainers;

import java.util.ArrayList;

import masterdiseasesimulation.Person;

public class JungStorage {
	private ArrayList<Person> vaccPeople;
	private ArrayList<Person> sickPeople;
	private int day;
	
	public JungStorage(ArrayList<Person> vaccPeople, ArrayList<Person> sickPeople, int day) {
		this.vaccPeople = vaccPeople;
		this.sickPeople = sickPeople;
		this.day = day;
	}
	
	public ArrayList<Person> getVaccPeople() {
		return vaccPeople;
	}
	
	public ArrayList<Person> getSickPeople() {
		return sickPeople;
	}
	
	public int getDay () {
		return day;
	}
}
