package masterdiseasesimulation;

import com.opencsv.CSVReader;
import datacontainers.InfoStorage;
import moremethods.MoreMethods;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class DataSearch {
    MoreMethods methods = new MoreMethods();
    HashMap<Integer, Integer> numberOfHouseholdsBasedOnPeople = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> ownersAndAges = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> agesAndPrecentages = new HashMap<Integer, Integer>();
    ArrayList<Household> households = new ArrayList<Household>();

    //Used for timing
    private long timeStamp;


    //Status
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JTextArea status = new JTextArea("PROGRAM STATUS");

    public void run() throws IOException, InterruptedException {
        status.setColumns(89);
        panel.setBorder(new EmptyBorder(5, 10, 5, 10));
        panel.add(status);
        frame.setTitle("Status...");
        frame.setSize(1000, 800);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stamp("Reading and saving CSV...");

        CSVReader reader = new CSVReader(new FileReader(System.getProperty("user.dir") + "\\data\\householdinfo\\DEC_10_SF1_QTH2.csv"));
        CSVReader reader1 = new CSVReader(new FileReader(System.getProperty("user.dir") + "\\data\\householdinfo\\DEC_10_SF1_QTP1_with_ann.csv"));
        
        List<String[]> rows = reader.readAll();
        List<String[]> rows1 = reader1.readAll();

        for (int i = 1; i < 8; i++) {
            int number = Integer.parseInt(rows.get(1)[getColumnByPeoplePerHousehold(i)]);
            numberOfHouseholdsBasedOnPeople.put(i, number);
        }
        
        for(int i = 15; i < 7; i = i + 10) {
        	int number = Integer.parseInt(rows.get(1)[getColumnByOwnerAge(i)]);
        	ownersAndAges.put(i, number);
        }
        //ALSO NEW FOR LOOP FOR THE SECOND DOCUMENT
        
        for(int i = 15; i < 7; i = i + 10) {
        	int number = Integer.parseInt(rows1.get(1)[getColumnAge(i)]);
        	ownersAndAges.put(i, number);
        }
        
        getOperationTime();
        stamp("Creating households...");

        int id = 0;
        for (int i = 1; i < 8; i++) {
            System.out.println("There are " + numberOfHouseholdsBasedOnPeople.get(i) + " households with " + i + " people in them in Lexington, MA.");
            for (int j = 0; j < numberOfHouseholdsBasedOnPeople.get(i); j++) {
                ArrayList<Person> people = createPeople("random:" + i);
                households.add(new Household(id, people));
                id++;
            }
        }

        getOperationTime();

        System.out.println(households.size() + " households created.");


        //Simulation____________________________________________________________________________________________________


        stamp("Generating network...");

        ArrayList<Person> people = new ArrayList<Person>();

        int cpID = 0; //Universal identification number for each person
        for (Household household : households) {
            for (Person person : household.getResidents()) {
                person.setID(cpID);
                person.setHousehold(household);
                people.add(person);
                cpID++;
            }
        }

        for (Household household : households) {
            befriend(household, "reflexive");
        }

        getOperationTime();
        stamp("Printing results...");


        for (Person person : people) {
            System.out.println("Hello! I am person " + person.getID() + ". I live in household " + person.getHousehold().getID() + ". My household size is " + person.getHousehold().getResidents().size() + ". Currently, I do not know anybody except for my family, so I have " + person.getFriends().size() + " friends.");
            System.out.println("Hello! I am Alik and I have no friends.");
        }

        getOperationTime();


        //Simulation method_____

        stamp("Running simulations...");

        //For now, do a random simulation with the people, just like in MasterManySimsObject.
        ArrayList<Double> daysList = new ArrayList<Double>();
        ArrayList<Double> totalSickList = new ArrayList<Double>();
        ArrayList<Double> costList = new ArrayList<Double>();
        InfoStorage results;
        String networkType = "Random";
        int minFriends = 2;
        int maxFriends = 5;
        Random random = new Random();
        int hubNumber = 183;
        int percentTeens = 0;
        int initiallySick = 1;
        int initiallyVacc = 0;
        int runs = 1;
        int getWellDays = 10;
        int discovery = 100000;
        int newGetWellDays = 3;
        int percentSick = 10;
        int getVac = 10;
        int curfewDays = 0;
        int percentCurfew = 0;

        if (networkType.equals("Small World")) {
            methods.befriendSmallWorld(people, minFriends, maxFriends, random, hubNumber);
        } else if (networkType.equals("Random")) {
            methods.befriendRandom(people, minFriends, maxFriends, random, hubNumber);
        } else if (networkType.equals("Scale-Free")) {
            methods.befriendScaleFree(people, minFriends, maxFriends, random);
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR: Network selection error. Shutting down program..." + networkType, "Input Error", JOptionPane.ERROR_MESSAGE);
            //System.exit(0);
        }


        ArrayList<Person> teenagers = methods.getAndSetTeenagers(people, 0);

        methods.infectRandom(people, initiallySick);
        methods.vaccRandom(people, initiallyVacc);

        for (int runTime = 0; runTime < runs; runTime++) {
            //ArrayList<Person> infected = methods.infectRandom(people, initiallySickI);
            //ArrayList<Person> vaccinnated = methods.vaccRandom(people, initiallyVaccI);
            //ArrayList<Person> teens = methods.getAndSetTeenagers(people, percentTeensI);


            results = methods.averageInfostorage(methods.simulate(people, teenagers, getWellDays, initiallySick, initiallyVacc, discovery, newGetWellDays, percentSick, getVac, curfewDays, 1, percentCurfew, false).getInfoStorages());
            daysList.add(results.getDay());
            totalSickList.add(results.getTotalSick());
            costList.add(results.getCost());

            methods.resetAll(people);
        }
        //System.out.println(numPeopleI + " " + minFriends  + " " +  maxFriends + " " + hubNumber + " " + getWellDaysI + " " + discoveryI + " " + newGetWellDaysI + " " + initiallySickI + " " + initiallyVaccI + " " + percentSickI + " " + getVacI);
        //System.out.println(results);

        results = new InfoStorage(methods.sum(daysList) / runs, 0.0, methods.sum(totalSickList) / runs, methods.sum(costList) / runs);

        getOperationTime();
        stamp("Saving graph...");

        //TODO: Save graph

        getOperationTime();
        stamp("Opening graph...");

        //TODO: Open graph

        getOperationTime();

        Thread.sleep(1000000000);
    }

    private int getColumnByPeoplePerHousehold(int peoplePerHousehold) {
        return 2 * peoplePerHousehold + 11;
    }
    private int getColumnByOwnerAge(int ownerAge) {
    	return 3*ownerAge + 46;
    }
    private ArrayList<Person> createPeople(String typeArg) {
        ArrayList<Person> people = new ArrayList<Person>();
        String[] parts = typeArg.split(":");
        String type = parts[0];
        if (type.equals("random")) {
            int max = Integer.parseInt(parts[1]);
            for (int i = 0; i < max; i++) {
                people.add(new Person(i));
            }
        }
        return people;
    }

    private void stamp(String text) {
        status.append("\n" + text);
        timeStamp = System.currentTimeMillis();
    }

    private long getOperationTime() {
        status.append("     Operation completed in " + (System.currentTimeMillis() - timeStamp) + " ms.");
        return System.currentTimeMillis() - timeStamp;
    }

    private void status(String text) {
        status.append("\n" + text);
    }

    //SIMULATION METHODS___________________________
    private void befriend(Household household, String how) {
        if (how.equals("reflexive")) { //Interbefriend a household
            ArrayList<Person> residents = household.getResidents();
            for (int i = 0; i < residents.size(); i++) {
                if (residents.size() == 1) {
                    return;
                } else if (residents.size() == 2) {
                    residents.get(0).addReflexiveFriend(residents.get(1));
                } else {
                    int peopleToBefriendUpperLimit = residents.size() - 1;
                    for (int j = i + 1; j <= peopleToBefriendUpperLimit; j++) {
                        residents.get(i).addReflexiveFriend(residents.get(j));
                    }
                }
            }
        }
    }
    // Age Distribution
    private void ageDistribute(Household h){
    	ArrayList<Person> residents = h.getResidents();
    	Person owner = residents.get(0);
    	owner.isOwner();
    	Random random = new Random();
    	int randomOwnerNum = random.nextInt(100);
    	//FIGURE OUT MATH HERE AFTER FIGURE OUT CSV
    	
    }
}