package masterdiseasesimulation;

import org.apache.commons.collections15.Transformer;

import java.util.ArrayList;
import java.util.Comparator;

//Ladies and Gnetlemen lo and behold the Person class *Correction : "Klass!!!!!!!"
public class Person implements Transformer<Person, String>, Comparator<Person> {
	private int ID;
	private ArrayList<Person> friends = new ArrayList<Person>();// A komu vobshe prigoditsya takoj masiv? (podskzka: ne nam)
	private boolean sick = false;
	private boolean origSick = false;
	private boolean vacc = false;
	private boolean origVacc = false;
	private boolean hub = false;
	private boolean teen = false;
	private int daysSick = 0;
	private boolean curfewed;
	private int curfewedDays;
	private boolean immuneToCurfews;
	int friendCapacity = 0;
	private float connectivityAroundPerson = 0;
	private Household household;
	private boolean isOwner;
	private int age;
	private double suceptability;
	

	//The constructor
	public Person(int ID) {
		this.ID = ID;
	}

	// ID ------------------------------------------------------------------
	public int getID() {
		return ID;
	}

	// Curfew ------------------------------------------------------------------
	public void setCurfewed(boolean curfewed) {
		this.curfewed = curfewed;
	}
	
	public void setHousehold(Household household) {
		this.household = household;
	}
	
	public Household getHousehold() {
		return household;
	}

	/*
    public void curfew() { // We don't actually need this method since we have setCurfewed but it looks nice. As in Grisha.curfew(), not Grisha.setCurfewed(true).
		this.curfewed = true;
	}
	 */

	public int getCurfewedDays() {
		return this.curfewedDays;
	}

	public void incrementCurfewedDays() {
		this.curfewedDays++;
	}

	public void setCurfewedDays(int days) {
		this.curfewedDays = days;
	}

	public boolean isCurfewed() {
		return this.curfewed;
	}

	public void setImmuneToCurfews(boolean immuneToCurfews) {
		this.immuneToCurfews = immuneToCurfews;
	}

	public boolean isImmuneToCurfews() {
		return this.immuneToCurfews;
	}
	public int getAge(){
		return age;
	}
	public void setAge(int a){
		age = a;
	}


	// Friends ------------------------------------------------------------------
	public ArrayList<Person> getFriends() {
		return friends;
	}

	public int getNumFriends() {
		return friends.size();
	}

	public Boolean isFriend(Person person) {
		return friends.contains(person);
	}

	public int getCapacity() {
		return friendCapacity;
	}

	public boolean capacityFull() {
		if (friends.size() >= friendCapacity) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean isSick() {
		return sick;
	}

	public Boolean isImmune() {
		return vacc;
	}

	public Boolean isHub() {
		return hub;
	}


	// Teenager ------------------------------------------------------------------
	public void setTeenager(boolean isTeenager) {
		this.teen = isTeenager;
	}

	public boolean isTeenager() {
		return teen;
	}

	public int getDaysSick() {
		return daysSick;
	}

	public void setDaysSick(int daysSick) {
		this.daysSick = daysSick;
	}

	public void incrementDaysSick() {
		this.daysSick++;
	}

	// Set Values ------------------------------------------------------------------
	public void setID(int newID) {
		this.ID = newID;
	}

	public void addFriend(Person newFriend) {
		this.friends.add(newFriend);
	}
	
	public Person addReflexiveFriend(Person newFriend) {
		this.friends.add(newFriend);
		newFriend.addFriend(this);
		return this;
	}

	public void clearFriends() {
		friends.clear();
	}

	public void setOrigSick(boolean value) {
		this.origSick = value;
		this.sick = this.origSick;
	}

	public void setSick(boolean value) {
		this.sick = value;
	}

	public void setOrigVacc(boolean value) {
		this.origVacc = value;
		this.vacc = this.origVacc;
	}

	public void setImmune(boolean value) {
		this.vacc = value;
	}

	public void setHub(boolean newValue) {
		this.hub = newValue;
	}

	public void setCapacity(int newValue) {
		this.friendCapacity = newValue;
	}
	public void isOwner(){
		this.isOwner = true;
	}
	public boolean getIsOwner(){
		return isOwner;
	}
	public void getWell() {
//		this.daysSick = 0;
		this.vacc = true;
		this.sick = false;
	}

	public void reset() {
		this.daysSick = 0;
		this.vacc = this.origVacc;
		this.sick = this.origSick;
	}

	public void setConnecticity(float newValue) {
		this.connectivityAroundPerson = newValue;
	}

	// Get the Connectivity Value ----------------------------------------------------------------------
	public float getConnectivity() {
		return connectivityAroundPerson;
	}

	// Custom Transformer and Comparator ------------------------------------------------------------------
	public static final Comparator<Person> orderByID = new Comparator<Person>() {
		public int compare(Person person1, Person person2) {
			if (person1.getID() - person2.getID() == 0) {
				return 0;
			}
			if (person1.getID() - person2.getID() > 0) {
				return 1;
			}
			if (person1.getID() - person2.getID() < 0) {
				return -1;
			}
			return 0; // Program will never get to here
		}
	};
	// SUCEPTABILITY STUFF
	public double getSuceptability(){
		return suceptability;
	}
	public void setSuceptability(double f){
		suceptability = f;
	}
	public static Transformer<Person, String> labelByID = new Transformer<Person, String>() {
		@Override
		public String transform(Person person) {
			return Integer.toString(person.getID());
		}
	};

	@Override
	public String transform(Person i) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public int compare(Person o1, Person o2) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	//Create a Person Comparator subclass
	public static class ComparatorByFriendNumber implements Comparator<Person> {
		@Override
		public int compare(Person p1, Person p2) {
			return p2.getNumFriends() - p1.getNumFriends(); // Descending
		}
	}

	public static final Comparator<Person> orderByConnectivity = new Comparator<Person>() {
		public int compare(Person person1, Person person2) {
			if (person1.getConnectivity() - person2.getConnectivity() == 0) {
				return 0;
			}
			if (person1.getConnectivity() - person2.getConnectivity() > 0) {
				return 1;
			}
			if (person1.getConnectivity() - person2.getConnectivity() < 0) {
				return -1;
			}
			return 0; // Program will never get to here
		}
	};

	public String toString() {
		return ("Person " + ID + " (" + state() + ")");
	}

	private String state() {
		if (sick) {
			return "Sick";
		} else if (vacc) {
			return "Vaccinated";
		} else {
			return "Unchanged";
		}
	}
}