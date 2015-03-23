package masterdiseasesimulation;

import org.apache.commons.collections15.Transformer;

import java.util.ArrayList;
import java.util.Comparator;

public class Household implements Transformer<Household, String>, Comparator<Household> {
    private int ID;
    private ArrayList<Person> residents;
    private boolean hasOwner = false;

    public Household(int ID, ArrayList<Person> residents) {
        this.ID = ID;
        this.residents = residents;
    }

     //Getters
    public int getID() {
        return ID;
    }

    public ArrayList<Person> getResidents() {
        return residents;
    }
    public boolean getHasOwner(){
    	return hasOwner;
    }
    //Setters
    public void newOwner(){
    	hasOwner = true;
    }
    public String toString(){
    	return ("Household: " + ID);
    }

    //Order by ID
    public static final Comparator<Household> orderByID = new Comparator<Household>() {
        public int compare(Household household1, Household household2) {
            if (household1.getID() - household2.getID() == 0) {
                return 0;
            }
            if (household1.getID() - household2.getID() > 0) {
                return 1;
            }
            if (household1.getID() - household2.getID() < 0) {
                return -1;
            }
            return 0; // Program will never get to here
        }
    };

    //Order by number of residents
    @Override
    public int compare(Household p1, Household p2) {
        return p2.getResidents().size() - p1.getResidents().size(); // Descending
    }

    @Override
    public String transform(Household household) {
        return null;
    }
}
