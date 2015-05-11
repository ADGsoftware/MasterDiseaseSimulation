package masterdiseasesimulation;

import datacontainers.InfoStorage;
import moremethods.MoreMethods;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import au.com.bytecode.opencsv.CSVReader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ModelTown {
    MoreMethods methods = new MoreMethods();
    LinkedHashMap<Integer, Integer> numberOfHouseholdsBasedOnPeople = new LinkedHashMap<Integer, Integer>();
    LinkedHashMap<Integer, Float> ownersAndAges = new LinkedHashMap<Integer, Float>();
    LinkedHashMap<Integer, Float> agesAndPrecentages = new LinkedHashMap<Integer, Float>();
    ArrayList<Household> households = new ArrayList<Household>();
    private ArrayList<Person> people = new ArrayList<Person>();

    //Used for timing
    private long timeStamp;


    //Status
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel();
    private JTextArea status = new JTextArea("PROGRAM STATUS");
    
    //HUMONGOUS COUNSTRUCTOR THAT DYUSHKA WILL KILL ME FOR
    public ModelTown(String networkType, int minFriends, int maxFriends, int hubNumber, Random random) throws IOException, InterruptedException {
    	
        status.setColumns(89);
        panel.setBorder(new EmptyBorder(5, 10, 5, 10));
        panel.add(status);
        frame.setTitle("Status...");
        frame.setSize(1000, 800);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        //frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        stamp("Reading and saving CSV...");

        CSVReader reader = new CSVReader(new FileReader(System.getProperty("user.dir") + "//data//householdinfo//DEC_10_SF1_QTH2_with_ann.csv"));
        CSVReader reader1 = new CSVReader(new FileReader(System.getProperty("user.dir") + "//data//householdinfo//DEC_10_SF1_QTP1.csv"));
        
        List<String[]> rows = reader.readAll();
        List<String[]> rows1 = reader1.readAll();
        //Reading CSV
        for (int i = 1; i < 8; i++) {
            int number = Integer.parseInt(rows.get(2)[getColumnByPeoplePerHousehold(i)]);
            numberOfHouseholdsBasedOnPeople.put(i, number);
            //System.out.println("People: " + i + " number: " + number);
        }
        
        for(int i = 1; i < 9; i++) {
        	float number = Float.parseFloat(rows.get(2)[getColumnByOwnerAge(i)]);
        	ownersAndAges.put(10*i + 5, number);
        	//System.out.println("OwnerAge: " + (10*i+5) + " Percent: " + number);
        }
        
        for(int i = 1; i < 20; i++) {
        	float number = Float.parseFloat(rows1.get(1)[getColumnAge(i-1)]);
        	agesAndPrecentages.put(i*5, number);
        	//System.out.println("Age: " + i*5 + " Percent: " + number);
        }

        getOperationTime();
        stamp("Creating households...");

        int id = 1;
        for (int i = 1; i < 8; i++) {
            //System.out.println("There are " + numberOfHouseholdsBasedOnPeople.get(i) + " households with " + i + " people in them in Lexington, MA.");
            for (int j = 0; j < numberOfHouseholdsBasedOnPeople.get(i); j++) {
                ArrayList<Person> people = createPeople("random:" + i);
                households.add(new Household(id, people));
                id++;
            }
        }
       // System.out.println(households);
        getOperationTime();

       // System.out.println(households.size() + " households created.");
        
        stamp("Generating network...");

       int cpID = 1; //Universal identification number for each person
        for (Household household : households) {
            for (Person person : household.getResidents()) {
                person.setID(cpID);
                person.setHousehold(household);
                this.people.add(person);
                cpID++;
            }
        }
        //Befriending Methods
        for (Household household : households) {
            befriend(household, "reflexive");
        }
        if (networkType.equals("Small World")) {
            methods.befriendSmallWorld(this.people, minFriends, maxFriends, random, hubNumber);
        } else if (networkType.equals("Random")) {
            methods.befreindRandomNew(this.people, minFriends, maxFriends, random, hubNumber);
        } else if (networkType.equals("Scale-Free")) {
            methods.befriendScaleFree(this.people, minFriends, maxFriends, random);
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR: Network selection error. Shutting down program..." + networkType, "Input Error", JOptionPane.ERROR_MESSAGE);
            //System.exit(0);
        }
        for(Household h : households){
        	for(Person p : h.getResidents()){
        		//System.out.println(p.getFriends().size() + " : Many Friends");
        	}
        }
        
        //AGES STUFF-----------
        ownersAndAges = rewritePercentMap(ownersAndAges);
        agesAndPrecentages = rewritePercentMap(agesAndPrecentages);
       
        for(Household household : households){
        	distributeAgesAndOwner(household, ownersAndAges, agesAndPrecentages);
        	setSucaptablies(household.getResidents());
        }
        //------------------------
        
        getOperationTime();
        stamp("Printing results...");

        getOperationTime();
        
        //Hist
        HistogramGenerator hist = new HistogramGenerator();   
        hist.makeHistAges(people, "AgesHistogram");
        
        System.out.println("DONE!!!!!!!");
/*
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

        modelTownGraph(results);

        getOperationTime();
        stamp("Opening graph...");

        //TODO: Open graph

        getOperationTime();

        Thread.sleep(1000000000);*/
    }
    
    public ModelTown(String networkType, int minFriends, int maxFriends, int hubNumber, Random random, int[] hs, double[] ha, double[] ages) throws IOException, InterruptedException {
    	
        status.setColumns(89);
        panel.setBorder(new EmptyBorder(5, 10, 5, 10));
        panel.add(status);
        frame.setTitle("Status...");
        frame.setSize(1000, 800);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        //frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        stamp("Reading and saving CSV...");
        
        //Reading CSV
        for (int i = 1; i < 8; i++) {
            numberOfHouseholdsBasedOnPeople.put(i, hs[i - 1]);
            //System.out.println("People: " + i + " number: " + number);
        }
        
        for(int i = 1; i < 9; i++) {
        	ownersAndAges.put(10*i + 5, (float)ha[i - 1]);
        	//System.out.println("OwnerAge: " + (10*i+5) + " Percent: " + number);
        }
        
        LinkedHashMap<Integer, Float> test = new LinkedHashMap<Integer, Float>();
        for(int i = 1; i < 19; i++) {
        	agesAndPrecentages.put(i*5, (float)ages[i - 1]);
        }
        //System.out.println(agesAndPrecentages);
        //System.out.println(test);

        getOperationTime();
        stamp("Creating households...");

        int id = 1;
        for (int i = 1; i < 8; i++) {
            //System.out.println("There are " + numberOfHouseholdsBasedOnPeople.get(i) + " households with " + i + " people in them in Lexington, MA.");
            for (int j = 0; j < numberOfHouseholdsBasedOnPeople.get(i); j++) {
                ArrayList<Person> people = createPeople("random:" + i);
                households.add(new Household(id, people));
                id++;
            }
        }
       // System.out.println(households);
        getOperationTime();

       // System.out.println(households.size() + " households created.");
        
        stamp("Generating network...");

       int cpID = 1; //Universal identification number for each person
        for (Household household : households) {
            for (Person person : household.getResidents()) {
                person.setID(cpID);
                person.setHousehold(household);
                this.people.add(person);
                cpID++;
            }
        }
        //Befriending Methods
        for (Household household : households) {
            befriend(household, "reflexive");
        }
        if (networkType.equals("Small World")) {
            methods.befriendSmallWorld(this.people, minFriends, maxFriends, random, hubNumber);
        } else if (networkType.equals("Random")) {
            methods.befreindRandomNew(this.people, minFriends, maxFriends, random, hubNumber);
        } else if (networkType.equals("Scale-Free")) {
            methods.befriendScaleFree(this.people, minFriends, maxFriends, random);
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR: Network selection error. Shutting down program..." + networkType, "Input Error", JOptionPane.ERROR_MESSAGE);
            //System.exit(0);
        }
        for(Household h : households){
        	for(Person p : h.getResidents()){
        		//System.out.println(p.getFriends().size() + " : Many Friends");
        	}
        }
        
        //AGES STUFF-----------
        ownersAndAges = rewritePercentMap(ownersAndAges);
        agesAndPrecentages = rewritePercentMap(agesAndPrecentages);
       
        for(Household household : households){
        	distributeAgesAndOwnerNew(household, ownersAndAges, agesAndPrecentages);
        	setSucaptablies(household.getResidents());
        }
        //------------------------
        
        getOperationTime();
        stamp("Printing results...");

        getOperationTime();
        
        //Hist
        HistogramGenerator hist = new HistogramGenerator();   
        hist.makeHistAges(people, "AgesHistogram");
        
        System.out.println("DONE!!!!!!!");
	}

    private int getColumnByPeoplePerHousehold(int peoplePerHousehold) {
        return 2 * peoplePerHousehold + 11;
    }
    private int getColumnByOwnerAge(int ownerAge) {
    	if(ownerAge >= 6){
    		return 2*ownerAge + 46;
    	}
    	else{
    	return 2*ownerAge + 44;
    	}
    }
    private int getColumnAge(int age){
    	return 7*age + 13;
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
    private LinkedHashMap<Integer, Float> rewritePercentMap(LinkedHashMap<Integer, Float> oldMap){
    	LinkedHashMap<Integer, Float> newMap = new LinkedHashMap<Integer, Float>();
    	float counter = 0;
    	for(Map.Entry<Integer, Float> entry : oldMap.entrySet()){
    		counter = entry.getValue() + counter;
    		newMap.put(entry.getKey(), counter);
    	}
    	return newMap;
    }
    private void distributeAgesAndOwner(Household household, LinkedHashMap<Integer, Float> ownersAndAges, LinkedHashMap<Integer, Float> agesAndPrecentages){
    	ArrayList<Person> residents = household.getResidents();
    	
    	//OwnerStuffs
    	residents.get(0).isOwner();
    	household.newOwner();
    	
    	//Owner Age
    	Random r = new Random();
    	float ageFloat = 100*r.nextFloat();
    	Boolean doneOwnerAgeDist = false;
    	for(Map.Entry<Integer, Float> entry : ownersAndAges.entrySet()){
    		if(entry.getValue() < ageFloat){
    			continue;
    		}
    		else{
    			residents.get(0).setAge(entry.getKey() - r.nextInt(10));
    			doneOwnerAgeDist = true;
    			break;
    		}
    	}
    	if(!doneOwnerAgeDist){
    		residents.get(0).setAge(90 + r.nextInt(10));
    	}
    	//Other Residents Age
    	for(Person resident : residents){
	    	if(!resident.getIsOwner()){
	    		ageFloat = r.nextFloat()*100;
	    		Boolean doneResidentAgeDist = false;
	    		for(Map.Entry<Integer, Float> entry : agesAndPrecentages.entrySet()){
	        		if(entry.getValue() >= ageFloat){
	        			resident.setAge(entry.getKey() - r.nextInt(5));
	        			doneResidentAgeDist = true;
	        			break;
	        		}
	        	}
	    		if(!doneResidentAgeDist){
	    			//System.out.println("I AM HERE");
	        		resident.setAge(95 + r.nextInt(5));
	        	}
	    	}
    	}
    }
    private void distributeAgesAndOwnerNew(Household household, LinkedHashMap<Integer, Float> ownersAndAges, LinkedHashMap<Integer, Float> agesAndPrecentages){
    	ArrayList<Person> residents = household.getResidents();
    	
    	//OwnerStuffs
    	residents.get(0).isOwner();
    	household.newOwner();
    	
    	//Owner Age
    	Random r = new Random();
    	float ageFloat = 100*r.nextFloat();
    	Boolean doneOwnerAgeDist = false;
    	for(Map.Entry<Integer, Float> entry : ownersAndAges.entrySet()){
    		if(entry.getValue() < ageFloat){
    			continue;
    		}
    		else{
    			residents.get(0).setAge(entry.getKey() - r.nextInt(10));
    			doneOwnerAgeDist = true;
    			break;
    		}
    	}
    	if(!doneOwnerAgeDist){
    		residents.get(0).setAge(90 + r.nextInt(10));
    	}
    	//Other Residents Age
    	for(Person resident : residents){
	    	if(!resident.getIsOwner()){
	    		ageFloat = r.nextFloat()*100;
	    		Boolean doneResidentAgeDist = false;
	    		for(Map.Entry<Integer, Float> entry : agesAndPrecentages.entrySet()){
	        		if(entry.getValue() >= ageFloat){
	        			resident.setAge(entry.getKey() - r.nextInt(5));
	        			doneResidentAgeDist = true;
	        			break;
	        		}
	        	}
	    		if (!doneResidentAgeDist) {
	    			//System.out.println("I AM HERE");
	        		resident.setAge(90 + r.nextInt(5));
	        	}
	    	}
    	}
    }
    public ArrayList<Household> getHouseholds(){
    	return households;
    }
    public ArrayList<Person> getPeople(){
    	return people;
    }
    public ArrayList<Person> getTeenagers(){
    	ArrayList<Person> teens = new ArrayList<Person>();
    	for(Person p : people){
    		if(p.getAge() < 20 && p.getAge() > 12 ){
    			teens.add(p);
    		}
    	}
    	return teens;
    }
    private void setSucaptablies(ArrayList<Person> people){
    	for(Person p : people){
    		if(p.getAge() >=0 && p.getAge() <=4){
    			//System.out.println("Here!!!!1");
    			p.setSuceptability(66.4/14.5);
    		}
    		if(p.getAge() >=5 && p.getAge() <=17){
    			//System.out.println("Here!!!!2");
    			p.setSuceptability(14.5/14.5);
    		}
    		if(p.getAge() >=18 && p.getAge() <=49){
    			//System.out.println("Here!!!!3");
    			p.setSuceptability(16.2/14.5);
    		}
    		if(p.getAge() >=50 && p.getAge() <=64){
    			//System.out.println("Here!!!!4");
    			p.setSuceptability(41.3/14.5);
    		}
    		if(p.getAge() >=65){
    			//System.out.println("Here!!!!5");
    			p.setSuceptability(192.4/14.5);
    		}
    	}
    }
}
