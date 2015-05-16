package moremethods;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Paint;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import masterdiseasesimulation.ManyLinesAverageObject;
import masterdiseasesimulation.Person;
import masterdiseasesimulation.TransmissionTestInfoStorage;

import org.apache.commons.collections15.Transformer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import datacontainers.InfoJungStorage;
import datacontainers.InfoStorage;
import datacontainers.JungStorage;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;

public class MoreMethods {
	/**
	 * METHODS WORKING WITH ARRAYLISTS ----------------------------------------------------------------------
	 */

	public static ArrayList<Integer> commaListToArrayList(String commaString) { // Given a string of integers separated by commas, returns an ArrayList with the integers
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		commaString = commaString.replaceAll(" ", "");
		if (commaString.equals("")) { // If string is empty, return empty ArrayList
			return arrayList;
		}
		int i;
		ArrayList<String> list = new ArrayList<String>(Arrays.asList(commaString.split(","))); // Split into String ArrayList
		for (String s : list) { // For every string, parse for integer
			i = Integer.parseInt(s);
			arrayList.add(i);
		}
		return arrayList;
	}

	public static ArrayList<Integer> arrayListFromTo(int start, int step, int end) { // Returns an ArrayList containing integers from start to end with given step
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = start; i <= end; i += step) {
			list.add(i);
		}
		return list;
	}

	public void removeDuplicate(ArrayList<Integer> arlList) {// Removes duplicates from given ArrayList
		ArrayList<Integer> newList = new ArrayList<Integer>();
		for (Integer item : arlList) {
			if (newList.contains(item)) {
				//Do Nothing
			} else {
				newList.add(item);
			}
		}
		arlList.clear();
		arlList.addAll(newList);
	}

	public double sum (ArrayList<Double> list) {
		double sum = 0.0;
		for (double i : list) {
			sum += i;
		}
		return sum;
	}

	/**
	 * WORKING WITH SPREADSHEETS ----------------------------------------------------------------------
	 */

	public static ArrayList<ArrayList<Integer>> getAllData(String filename) throws BiffException, IOException {
		ArrayList<ArrayList<Integer>> data = new ArrayList<ArrayList<Integer>>();
		Workbook workbook = Workbook.getWorkbook(new File(filename));
		int numSheets = workbook.getSheets().length;
		Sheet sheet;
		int rows;
		int columns;
		for (int sheetNum = 0; sheetNum < numSheets; sheetNum++) {
			sheet = workbook.getSheet(sheetNum);
			rows = sheet.getRows();
			columns = sheet.getColumns();
			for (int row = 2; row < rows; row++) {
				ArrayList<Integer> rowList = new ArrayList<Integer>();
				for (int col = 0; col < columns; col++) {
					Cell cell = sheet.getCell(col, row);
					String contentsStr = cell.getContents();
					if (!contentsStr.equals("")) {
						int contents = Integer.parseInt(contentsStr);
						rowList.add(contents);
					}
				}
				data.add(rowList);
			}
		}
		//System.out.println(data);
		return data;
	}

	/**
	 * MISCELLANEOUS ----------------------------------------------------------------------
	 */

	public static int randInt(int min, int max) {
		Random rand = new Random();

		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public static ArrayList<Long> getTime(long leftSec) { // This method returns an ArrayList with the number of hours, minutes, and seconds in a given number of seconds
		// Creates arrayList to add values to later
		ArrayList<Long> times = new ArrayList<Long>();

		// Calculates number of days and leftover seconds
		long days = leftSec / 86400;
		leftSec = leftSec % 86400;
		times.add(days);

		// Calculates number of hours and leftover seconds
		long hr = leftSec / 3600;
		leftSec = leftSec % 3600;
		times.add(hr);

		// Calculates number of minutes and leftover seconds
		long min = leftSec / 60;
		leftSec = leftSec % 60;
		times.add(min);

		// Calculates number of seconds
		long sec = leftSec;
		times.add(sec);

		return (times);
	}

	public static String timeString(double estTime) { // Returns time in readable format
		ArrayList<Long> times = getTime((long) estTime);
		long days = times.get(0);
		long hours = times.get(1);
		long mins = times.get(2);
		long secs = times.get(3);

		if (days != 0) {
			return days + " days, " + hours + " hours, " + mins + " minutes, and " + secs + " seconds";
		}
		if (hours != 0) {
			return hours + " hours, " + mins + " minutes, and " + secs + " seconds";
		} else if (mins != 0) {
			return mins + " minutes and " + secs + " seconds";
		} else {
			return secs + " seconds";
		}
	}

	/**
	 * INITIAL SETUP OF PEOPLE ----------------------------------------------------------------------
	 */

	public static ArrayList<Person> getPeople(int numPeople) {
		ArrayList<Person> people = new ArrayList<Person>();

		for (int i = 1; i <= numPeople; i++) {
			people.add(new Person(i));
		}

		return people;
	}

	public ArrayList<Person> infectRandom(ArrayList<Person> people, int numInfect) { // Make numInfect people initially sick
		ArrayList<Person> infected = new ArrayList<Person>();

		for (int i = 0; i < numInfect; i++) {
			int personInt = randInt(0, people.size() - 1);
			if (!people.get(personInt).isSick() && !people.get(personInt).isImmune()) {
				people.get(personInt).setOrigSick(true);
				infected.add(people.get(personInt));
			} else {
				i--;
			}
		}

		return infected;
	}

	public ArrayList<Person> vaccRandom(ArrayList<Person> people, int numVacc) { // Make numVacc people initially vaccinated
		ArrayList<Person> vaccinnated = new ArrayList<Person>();

		for (int i = 0; i < numVacc; i++) {
			int personInt = randInt(0, people.size() - 1);
			if (!people.get(personInt).isSick() && !people.get(personInt).isImmune()) {
				people.get(personInt).setOrigVacc(true);
				vaccinnated.add(people.get(personInt));
			} else {
				i--;
			}
		}

		return vaccinnated;
	}

	public void makeHubs(int hubNumber, ArrayList<Person> people, Random random) { // Make hubNumber people hubs
		int randomInt;
		Boolean done;
		for (int i = 0; i < hubNumber; i++) {
			done = false;
			while (!done) {
				randomInt = random.nextInt(people.size());
				for (Person person : people) {
					if (people.indexOf(person) == randomInt && !(person.isHub())) {
						person.setHub(true);
						done = true;
						break;
					}
				}
			}
		}
	}

	public ArrayList<Person> getAndSetTeenagers(ArrayList<Person> people, int percentTeenagers) { // Make percentTeenagers of people teenagers
		Collections.sort(people, new Person.ComparatorByFriendNumber()); //Sort the people by their friend number

		int teenagerNumber = (int) (percentTeenagers * 0.01 * people.size());

		//System.out.println("teenagers: " + teenagerNumber);

		ArrayList<Person> teenagers = new ArrayList<Person>();

		List<Person> subList = people.subList(0, teenagerNumber);

		teenagers.addAll(subList);

		for (Person teenager : teenagers) {
			teenager.setTeenager(true);
		}

		return teenagers;
	}

	public void assignCapacities(ArrayList<Person> people, int minFriends, int maxFriends, Random random) { // Assign friend capacities to each person
		int randomInt;
		for (Person person : people) {
			randomInt = random.nextInt((maxFriends - minFriends) + 1) + minFriends;
			person.setCapacity(randomInt);
		}
	}

	public static void resetAll(ArrayList<Person> people) {
		for (Person person : people) {
			person.reset();
		}
	}

	/**
	 * NETWORK/BEFRIENDING METHODS ----------------------------------------------------------------------
	 */

//	public void befriendRandom(ArrayList<Person> people, int minFriends, int maxFriends, Random random, int hubNumber) {//It will be possible to make this algorhyth even better suggest way!!1
//		//Choose people for hubs
//		makeHubs(hubNumber, people, random);
//		assignCapacities(people, minFriends, maxFriends, random);
//		// Make friends
//		ArrayList<Person> peopleReference = new ArrayList<Person>();
//		for (Person newPerson : people) {
//			peopleReference.add(newPerson);
//		}
//		for (Person person : people) {
//			//Hub Case
//			if (person.isHub()) {
//				person.setCapacity(people.size() + 1);
//				for (Person hubFriend : people) {
//					if ((hubFriend != person) && !(person.getFriends().contains(hubFriend))) {
//						if ((random.nextInt(100) + 1 > 20) && !(hubFriend.capacityFull())) {
//							person.addFriend(hubFriend);
//							hubFriend.addFriend(person);
//						}
//					}
//				}
//			}
//			// If not HUB
//			else {
//				Collections.shuffle(peopleReference);
//				System.out.println(person.getID());
//				for (Person possibleFriend : peopleReference) {
//					System.out.println(possibleFriend.getID());
//					if (possibleFriend.getID() <= person.getID()) {
//						System.out.println("PERSON BEFRIEND HIMSELF ERROR");
//					} else if (possibleFriend.capacityFull() || person.capacityFull()) {
//						System.out.println("Capacity full ERROR");
//					} else {
//						System.out.println("FRIENDSHIP!!!!!!!!!!!");
//						person.addFriend(people.get(possibleFriend.getID() - 1));
//						people.get(possibleFriend.getID() - 1).addFriend(person);
//					}
//				}
//			}
//		}
//	}
	public void befriendRandom(ArrayList<Person> people, int minFriends, int maxFriends, Random random, int hubNumber) {
		//Choose people for hubs
		makeHubs(hubNumber, people, random);
		assignCapacities(people, minFriends, maxFriends, random);
		// Make friends
		ArrayList<Person> peopleReference = new ArrayList<Person>();
		ArrayList<Person> fullCapacitiesNew = new ArrayList<Person>();
		ArrayList<Person> fullCapacities = new ArrayList<Person>();
		for (Person newPerson : people) {
			peopleReference.add(newPerson);
		}
		for (Person person : people) {
			//Hub Case
			if (person.isHub()) {
				person.setCapacity(people.size() + 1);
				for (Person hubFriend : people) {
					if ((hubFriend != person) && !(person.getFriends().contains(hubFriend))) {
						if ((random.nextInt(100) + 1 > 20) && !(hubFriend.capacityFull())) {
							person.addFriend(hubFriend);
							hubFriend.addFriend(person);
						}
					}
				}
			}
			// If not HUB
			else {
				Collections.shuffle(peopleReference);
				//System.out.println(person.getID());
				for (Person possibleFriend : peopleReference) {
					//System.out.println(possibleFriend);
					if (possibleFriend.getID() <= person.getID()) {
						//System.out.println("PERSON BEFRIEND HIMSELF ERROR");
						continue;
					} 
					else if (possibleFriend.capacityFull()) {
						fullCapacitiesNew.add(possibleFriend);
						continue;
					} 
					else if(person.capacityFull()){
						fullCapacitiesNew.add(person);
						break;
					}
					else {
						//System.out.println("FRIENDSHIP!!!!!!!!!!!");
						person.addFriend(people.get(possibleFriend.getID() - 1));
						people.get(possibleFriend.getID() - 1).addFriend(person);
					}
				}
				for(Person p : fullCapacitiesNew){
					fullCapacities.add(p);
					if(!fullCapacities.contains(p)){
						int index = peopleReference.indexOf(p);
						peopleReference.remove(index);
					}
				}
			//System.out.println(peopleReference);
			}
		}
	}
	public void befreindRandomNew(ArrayList<Person> people, int minFriends, int maxFriends, Random random, int hubNumber){
		makeHubs(hubNumber, people, random);
		int numEdges = random.nextInt(people.size()*maxFriends/2) + minFriends*people.size()/2;
		int minEdgesNum = minFriends*people.size()/2;
		int additionalEdgesNum = numEdges - minEdgesNum;
		
		ArrayList<Person> peopleCopy = new ArrayList<Person>();
		for(Person p : people){
			peopleCopy.add(p);
		}

		iterateForBefriendRandomNew(minFriends, maxFriends, people, peopleCopy, minEdgesNum, additionalEdgesNum, random, true);
		
		peopleCopy.clear();
		for(Person p : people){
			peopleCopy.add(p);
		}
		
		iterateForBefriendRandomNew(minFriends, maxFriends, people, peopleCopy, minEdgesNum, additionalEdgesNum, random, false);

		for (Person person : people) {
			//Hub Case
			if (person.isHub()) {
				person.setCapacity(people.size() + 1);
				for (Person hubFriend : people) {
					if ((hubFriend != person) && !(person.getFriends().contains(hubFriend))) {
						if ((random.nextInt(100) + 1 > 20) && !(hubFriend.capacityFull())) {
							person.addFriend(hubFriend);
							hubFriend.addFriend(person);
						}
					}
				}
			}
		}
	}
	public void iterateForBefriendRandomNew(int minFriends,int maxFriends, ArrayList<Person> people, ArrayList<Person> peopleCopy, int minEdgesNum, int additionalEdgesNum, Random random, boolean min){
		int minOrAdditionalEdgesNum;
		int minOrMaxFriends;
		if (min){
			minOrAdditionalEdgesNum = minEdgesNum;
			minOrMaxFriends = minFriends;
		}
		else{
			minOrAdditionalEdgesNum =  additionalEdgesNum;
			minOrMaxFriends = maxFriends;
		}
		
		for(int i = 0; i < minOrAdditionalEdgesNum; i++){
			int index1 = random.nextInt(peopleCopy.size()-1);
			int index2 = random.nextInt(peopleCopy.size()-1);
			
			int index1Real = people.indexOf(peopleCopy.get(index1));
			int index2Real = people.indexOf(peopleCopy.get(index2));
			
			people.get(index1Real).addReflexiveFriend(people.get(index2Real));
			peopleCopy.get(index1).addReflexiveFriend(peopleCopy.get(index2));
			
			if(peopleCopy.get(index1).getNumFriends() == minOrMaxFriends){
				peopleCopy.remove(index1);
			}
			if(peopleCopy.get(index2).getNumFriends() == minOrMaxFriends){
				peopleCopy.remove(index1);
			}
		}
	}
	public void befriendScaleFree(ArrayList<Person> people, int minFriends, int maxFriends, Random random) {
		ArrayList<Person> relativeConnections = new ArrayList<Person>();
		ArrayList<Person> check = new ArrayList<Person>();
		int randomPerson;
		Person randomPersonPlaceholder;//Used to switch arrayLists
		assignCapacities(people, minFriends, maxFriends, random);
		int addConstant = 30;
		for (Person person : people) {
			relativeConnections.add(person);
			while (!person.capacityFull()) {//Loops until person fits nescessary Conditions
				if (relativeConnections.size() == 1) {
					break;
				}
				check.clear();
				check.add(person);
				check.addAll(person.getFriends());
				if (check.containsAll(relativeConnections)) {
					break;
				}
				randomPerson = random.nextInt(relativeConnections.size());
				randomPersonPlaceholder = relativeConnections.get(randomPerson);
				randomPerson = people.indexOf(randomPersonPlaceholder);
				if ((people.get(randomPerson) != person) && !person.getFriends().contains(people.get(randomPerson))) {
					person.addFriend(people.get(randomPerson));
					people.get(randomPerson).addFriend(person);
					//System.out.println(person.getID() + " and " + people.get(randomPerson).getID());
					for (int addCount = 0; addCount < addConstant; addCount++) {
						relativeConnections.add(person);
						relativeConnections.add(people.get(randomPerson));
					}
				}
				//System.out.println(person.getID());
				//System.out.println("Capacity: " + person.getCapacity());
			}
		}
	}

	//	public void befriendSmallWorld(ArrayList<Person> people, int minFriends, int maxFriends, Random random, int hubNumber) {
	//		int randomPersonForHub;
	//		assignCapacities(people, minFriends, maxFriends, random);
	//		int numPeople = people.size();
	//		makeHubs(hubNumber, people, random);
	//		boolean done = false;
	//		//Range Variable
	//		int halfRange;
	//
	//		//Other
	//		int counter;
	//
	//		//CODE
	//		if (maxFriends % 2 == 0) {
	//			halfRange = maxFriends / 2;
	//		} else {
	//			halfRange = (maxFriends + 1) / 2;
	//		}
	//		ArrayList<Person> possibleFriends = new ArrayList<Person>();
	//		//This is where actual befriending starts! Otdel Znakmostv!!! Get Excited
	//		for (Person person : people) {
	//			//Add People in the Possible Friend range to ArrayList
	//			int ID = (((person.getID() - halfRange)) % numPeople + numPeople) % numPeople;
	//			for (int x = 0; x < halfRange * 2; x++) {
	//				ID = (((ID)) % numPeople + numPeople) % numPeople;
	//				if (ID == 0) {
	//					ID = numPeople;
	//				}
	//				possibleFriends.add(people.get(ID - 1));
	//				ID = (((ID + 1)) % numPeople + numPeople) % numPeople;
	//			}
	//			//System.out.println(person + " : " + possibleFriends + " ' " + person.getCapacity());
	//			while (!person.capacityFull()) {
	//				Collections.shuffle(possibleFriends);
	//				for (Person friendApplicant : possibleFriends) {
	//					if (person.capacityFull()) {
	//						break;
	//					}
	//					if (friendApplicant.capacityFull()) {
	//						continue;
	//					}
	//					if (person.getFriends().contains(friendApplicant)) {
	//						continue;
	//					}
	//					if (person == friendApplicant) {
	//						continue;
	//					}
	//					if (!person.getFriends().contains(friendApplicant) && !friendApplicant.getFriends().contains(person)) {
	//						person.addFriend(people.get(friendApplicant.getID() - 1));
	//						people.get(friendApplicant.getID() - 1).addFriend(person);
	//					}
	//				}
	//				counter = 0;
	//				for (Person control : possibleFriends) {
	//					if (control.capacityFull()) {
	//						counter++;
	//					}
	//					if (person.getFriends().contains(control)) {
	//						counter++;
	//					}
	//					if (control == person) {
	//						counter++;
	//					}
	//				}
	//				if (counter >= possibleFriends.size()) {
	//					break;
	//				}
	//			}
	//			done = false;
	//			if (person.isHub()) {
	//				while (!done) {
	//					randomPersonForHub = random.nextInt(people.size());
	//					if ((people.get(randomPersonForHub) != person) && !person.getFriends().contains(people.get(randomPersonForHub))) {
	//						person.addFriend(people.get(randomPersonForHub));
	//						people.get(randomPersonForHub).addFriend(person);
	//						done = true;
	//					}
	//				}
	//			}
	//			possibleFriends.clear();
	//		}
	//	}
	public void befriendSmallWorld(ArrayList<Person> people, int minFriends, int maxFriends, Random random, int hubNumber) {
		int randomPersonForHub;
		assignCapacities(people, minFriends, maxFriends, random);
		int numPeople = people.size();
		makeHubs(hubNumber, people, random);
		boolean done = false;
		//Range Variable
		int halfRange;

		//Other
		int counter;

		//CODE
		if (maxFriends % 2 == 0) {
			halfRange = maxFriends / 2;
		} else {
			halfRange = (maxFriends + 1) / 2;
		}
		ArrayList<Person> possibleFriends = new ArrayList<Person>();
		//This is where actual befriending starts! Otdel Znakmostv!!! Get Excited
		for (Person person : people) {
			//Add People in the Possible Friend range to ArrayList
			int ID = (((person.getID() - halfRange)) % numPeople + numPeople) % numPeople;
			for (int x = 0; x < halfRange * 2; x++) {
				ID = (((ID)) % numPeople + numPeople) % numPeople;
				if (ID == 0) {
					ID = numPeople;
				}
				possibleFriends.add(people.get(ID - 1));
				ID = (((ID + 1)) % numPeople + numPeople) % numPeople;
			}
			//System.out.println(person + " : " + possibleFriends + " ' " + person.getCapacity());
			while (!person.capacityFull()) {
				Collections.shuffle(possibleFriends);
				for (Person friendApplicant : possibleFriends) {
					if (person.capacityFull()) {
						break;
					}
					if (friendApplicant.capacityFull()) {
						continue;
					}
					if (person.getFriends().contains(friendApplicant)) {
						continue;
					}
					if (person == friendApplicant) {
						continue;
					}
					if (!person.getFriends().contains(friendApplicant) && !friendApplicant.getFriends().contains(person)) {
						person.addFriend(people.get(friendApplicant.getID() - 1));
						people.get(friendApplicant.getID() - 1).addFriend(person);
					}
				}
				counter = 0;
				for (Person control : possibleFriends) {
					if (control.capacityFull()) {
						counter++;
					}
					if (person.getFriends().contains(control)) {
						counter++;
					}
					if (control == person) {
						counter++;
					}
				}
				if (counter >= possibleFriends.size()) {
					break;
				}
			}
			done = false;
			if (person.isHub()) {
				while (!done) {
					randomPersonForHub = random.nextInt(people.size());
					if ((people.get(randomPersonForHub) != person) && !person.getFriends().contains(people.get(randomPersonForHub))) {
						person.addFriend(people.get(randomPersonForHub));
						people.get(randomPersonForHub).addFriend(person);
						done = true;
					}
				}
			}
			possibleFriends.clear();
		}
	}

	/*
    // Used by ManyLinesAverage
	public static void addFriendsRandom (ArrayList<Person> people, int hubNumber, int minFriends, int maxFriends) {
		int numPeople = people.size();

		for (int hubPerson = numPeople; hubPerson > numPeople - hubNumber; hubPerson--) {
			people.get(hubPerson - 1).setHub(true);
		}

		int numFriends;
		int friendInt;
		Person friendPerson;
		for (Person person : people) {
			if (person.isHub()) {
				numFriends = (int)(0.8 * (double)numPeople);
				while (numFriends > 0 && person.getFriends().size() + 1 != numPeople) {
					friendInt = randInt(1, numPeople);
					friendPerson = people.get(friendInt - 1);

					if (!person.isFriend(friendPerson) && !friendPerson.isFriend(person) && friendPerson != person) {
						person.addFriend(friendPerson);
						friendPerson.addFriend(person);
						numFriends--;
					}
				}
			}
			else {
				numFriends = minFriends;
				while (numFriends > 0 && person.getFriends().size() < maxFriends) {
					friendInt = randInt(1, numPeople);
					friendPerson = people.get(friendInt - 1);

					if (!person.isFriend(friendPerson) && !friendPerson.isFriend(person) && friendPerson != person) {
						person.addFriend(friendPerson);
						friendPerson.addFriend(person);
						numFriends--;
					}
				}
			}
		}
	}
	 */

	/**
	 * WORKING WITH JUNG ----------------------------------------------------------------------
	 */

	public Layout<Person, String> chooseLayout(UndirectedSparseMultigraph<Person, String> graph, String layoutString) { // Set layout type
		if (layoutString.equals("FR")) {
			FRLayout<Person, String> layout = new FRLayout<Person, String>(graph);
			return layout;
		} else if (layoutString.equals("ISOM")) {
			ISOMLayout<Person, String> layout = new ISOMLayout<Person, String>(graph);
			return layout;
		} else if (layoutString.equals("Spring")) {
			SpringLayout<Person, String> layout = new SpringLayout<Person, String>(graph);
			return layout;
		} else if (layoutString.equals("Circle")) {
			CircleLayout<Person, String> layout = new CircleLayout<Person, String>(graph);
			layout.setVertexOrder(Person.orderByID);
			return layout;
		} else {
			ISOMLayout<Person, String> layout = new ISOMLayout<Person, String>(graph); // Make it default (ISOM)
			return layout;
		}
	}

	public void drawVerticies(UndirectedSparseMultigraph<Person, String> g, ArrayList<Person> people) { // Draw vertices based on graph
		for (Person person : people) {
			g.addVertex(person);
		}
	}

	public void drawJung(UndirectedSparseMultigraph<Person, String> g, VisualizationViewer<Person, String> vv, ArrayList<Person> people) { // Draw network based on graph
		ArrayList<Person> friends = new ArrayList<Person>();
		for (Person person : people) {
			friends = person.getFriends();
			for (Person friend : friends) {
				if (people.indexOf(friend) > people.indexOf(person)) {
					g.addEdge(Integer.toString(person.getID()) + " to " + Integer.toString(friend.getID()), person, friend);
				}
			}
		}
		greenVertices(vv);
		recolorHubs(g, vv, people);
	}

	public void greenVertices(VisualizationViewer<Person, String> vv) { // Make all vertices green
		Transformer<Person, Paint> vertexColorGreen = new Transformer<Person, Paint>() {
			@Override
			public Paint transform(Person i) {
				return Color.GREEN;
			}
		};
		vv.getRenderContext().setVertexFillPaintTransformer(vertexColorGreen);
	}

	public void recolorHubs(UndirectedSparseMultigraph<Person, String> g, VisualizationViewer<Person, String> vv, final ArrayList<Person> people) { // Color all hubs orange
		Transformer<Person, Paint> vertexColorOrange = new Transformer<Person, Paint>() {
			@Override
			public Paint transform(Person i) {
				ArrayList<Person> friends = i.getFriends();
				//System.out.println(friends);
				if (friends.size() >= (people.size() / 100) * 10) {
					return Color.ORANGE;
				}
				return Color.GREEN;
			}
		};
		vv.getRenderContext().setVertexFillPaintTransformer(vertexColorOrange);
	}

	/**
	 * FRIENDS WITH FRIENDS METHODS ----------------------------------------------------------------------
	 */

	public void calculateConnectivityRatios(ArrayList<Person> people) {
		float numerator = 0;
		float denominator;
		ArrayList<Person> friends = new ArrayList<Person>();
		ArrayList<Person> friendsOfFriend = new ArrayList<Person>();
		for (Person person : people) {
			friends = person.getFriends();
			denominator = friends.size() * (friends.size() - 1) / 2;
			if (denominator == 0) {
				person.setConnecticity(0);
				continue;
			}
			for (Person friend : friends) {
				friendsOfFriend = friend.getFriends();
				for (Person friendOfFriend : friendsOfFriend) {
					if (friends.contains(friendOfFriend)) {
						numerator++;
					}
				}
			}
			numerator = numerator / 2;
			person.setConnecticity(numerator / denominator);
			numerator = 0;
			denominator = 0;
		}
	}

	public float averageConnectivityPercentage(ArrayList<Person> people) {
		float result = 0;
		for (Person person : people) {
			result = result + person.getConnectivity();
		}
		return result * 100 / people.size();
	}

	public float medianConnectivityPrecentage(ArrayList<Person> people) {
		int index = 0;
		Collections.sort(people, Person.orderByConnectivity);
		if (people.size() % 2 == 0) {
			index = people.size() / 2;
			return 100 * (people.get(index - 1).getConnectivity() + people.get(index).getConnectivity()) / 2;
		} else {
			index = people.size() / 2 + 1;
			return 100 * people.get(index).getConnectivity();
		}
	}

	public float standardDeviation(ArrayList<Person> people) {
		float mean = averageConnectivityPercentage(people);
		float result = 0;
		for (Person person : people) {
			//System.out.println("Mean Is: " + mean);
			//System.out.println(result);
			//System.out.println("Connectivity Is: " + person.getConnectivity());
			//System.out.println("We are Adding: " + Math.pow((mean - 100 * person.getConnectivity()), 2) + " To the Result");
			result = (float) (result + Math.pow((mean - 100 * person.getConnectivity()), 2));
			//System.out.println("Result Is: " + result);
		}
		return (float) (Math.sqrt((result / people.size())));
	}

	/**
	 * SIMULATION METHODS ----------------------------------------------------------------------
	 */

	private static int getNumSickPeople(ArrayList<Person> people) { // Get number of people currently sick
		int totalSick = 0;
		for (Person person : people) {
			if (person.isSick()) {
				totalSick++;
			}
		}
		return totalSick;
	}

	public Integer[] simulate(ArrayList<Person> people, ArrayList<Person> teenagers, int getWellDays, int discovery, ArrayList<Integer> infectedPeople, int percentSick, int getVac, ArrayList<Integer> vaccinatedPeople, String filename, int runTimes, int percentCurfewed, int curfewDays) throws IOException {
		boolean success = true;

		int day = 0;

		int numberOfSickPeople = infectedPeople.size();

		DefaultCategoryDataset dataset = new DefaultCategoryDataset(); //Create a new dataset to store all of the simulation data

		while (numberOfSickPeople > 0) { // While there are sick people (This while loop goes every day)
			day++;

			addPoint(dataset, numberOfSickPeople, "Infected People", Integer.toString(day));

			// First of all, check the people that are already sick.
			for (Person person : people) {
				if (person.isSick()) {
					person.incrementDaysSick();
				}
				if (person.getDaysSick() > getWellDays) {
					person.getWell();
				}
			}

			// Now, curfew some random teenagers
			for (Person teenager : teenagers) {
				teenager.incrementCurfewedDays();
				if (!teenager.isImmuneToCurfews()) {
					if (randInt(0, 10) == percentCurfewed) {
						teenager.setCurfewed(true);
					}
				}
				if (teenager.getCurfewedDays() > curfewDays) { // If he finished his curfew, reset him
					teenager.setCurfewed(false);
					teenager.setCurfewedDays(0);
					teenager.setImmuneToCurfews(true);
				}
			}

			// For every person...
			for (Person person : people) {
				//System.out.println("At person " + person.getID());
				ArrayList<Person> friends = person.getFriends();
				for (Person friend : friends) {
					//System.out.println("At friend " + friend.getID());
					if (!(friend.isSick() || friend.isImmune() || friend.isCurfewed())) { // If they have a vulnerable friend
						if (randInt(0, 100 / percentSick) == 100 / percentSick) { // A random chance according to percentSick
							//System.out.println("Make him sick!");
							friend.setSick(true);
						}
					}
				}
			}

			// Update the number of sick people
			numberOfSickPeople = getNumSickPeople(people);
		}

		//System.out.println("Simulation complete after " + day + " days.");

		people.get(2).setSick(true); // For now, make number 2 sick from the very beginning
		people.get(2).setDaysSick(0);

		int successful = success == true ? 1 : 0; // Convert boolean success to an integer (0 if true, 1 is false)

		makeChart(dataset, filename, "Number of Sick People", "Days", "Infected People");

		return new Integer[]{day, successful};
	}

	// Simulation for MasterManySims updated for ManyLinesAverage
	public static InfoJungStorage simulate(ArrayList<Person> people, ArrayList<Person> teenagers, int getWellDays, int origSick, int origVacc, int discovery, int newGetWellDays, int percentSick, int getVac, int curfewDays, int runTimes, int percentCurfewed, boolean transmissionTest, boolean modelTownSim) {
		ArrayList<JungStorage> jungStorage = new ArrayList<JungStorage>();
		int day = 0;
		int cost = origVacc;
		Random random = new Random();

		//System.out.println("RunTimes: " + runTimes);

		ArrayList<ArrayList<InfoStorage>> infoStorage = new ArrayList<ArrayList<InfoStorage>>();

		/*
		// Make progress bar
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 0));
		panel.add(new JLabel("Running simulations..."));
		JFrame pBar = new JFrame();
		JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setIndeterminate(false);
		progressBar.setStringPainted(true);
		panel.add(progressBar);
		panel.setBorder(new EmptyBorder(5, 10, 5, 10));
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		progressBar.setStringPainted(true);
		//progressBar.setBackground(new Color(255, 255, 255));
		//progressBar.setForeground(new Color(0, 0, 0));
		pBar.add(panel);
		pBar.pack();
		pBar.setLocationRelativeTo(null);
		pBar.setResizable(false);
		pBar.setVisible(true);
		pBar.setTitle("Running simulations...");
		pBar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 */

		int averageDuration = 0;
		
		for(Person p : people){
			p.setSick(false);
			p.setImmune(false);
			p.setDaysSick(0);
		}
		
		//Why are we using ArrayLists, and not Lists?     
		ArrayList<Integer> totalSickPeople = new ArrayList<Integer>();
		ArrayList<Integer> totalImmunePeople = new ArrayList<Integer>();
		for (int runTime = 0; runTime < runTimes; runTime++) {
			//Makes Random people sick or immune, this should result in less stable graphs
			for (int i= 0 ; i < origSick; i++) {
				Boolean done = false;
				while(!done){
					int index = random.nextInt(people.size() - 1);
					if(people.get(index).isSick() || people.get(index).isImmune()){
						continue;
					}
					else{
						people.get(index).setSick(true);
						totalSickPeople.add(people.get(index).getID());
						done = true;
					}
				}
			}
			for (int i= 0 ; i < origVacc; i++) {
				Boolean done = false;
				while(!done){
					int index = random.nextInt(people.size() - 1);
					if(people.get(index).isSick() || people.get(index).isImmune()){
						continue;
					}
					else{
						people.get(index).setImmune(true);
						totalImmunePeople.add(people.get(index).getID());
						done = true;
					}
				}
			}
			//System.out.println("VARS DEFINED");
			/*
			int value = runTime * 100 / runTimes;
			progressBar.setValue(value);
			if (runTime == Math.floor(runTimes / 4)) {
				pBar.setTitle("Running simulations... 25%");
			}
			if (runTime == Math.floor(runTimes / 2)) {
				pBar.setTitle("Running simulations... 50%");
			}
			if (runTime == Math.floor(runTimes * 0.75)) {
				pBar.setTitle("Running simulations... 75%");
			}
			 */
			infoStorage.add(new ArrayList<InfoStorage>());
			//System.out.println(runTime);
			//System.out.println(getNumSickPeople(people));
			
			//System.out.println(totalSickPeople);
			while (getNumSickPeople(people) > 0) {
				//int numberSickOnDay = 0;
				for (Person person : people) {
					for (Person teenager : teenagers) {
						teenager.incrementCurfewedDays();
						if (!teenager.isImmuneToCurfews()) {
							if (randInt(1, 100 / percentCurfewed) == 1) {
								teenager.setCurfewed(true);
							}
						}
						if (teenager.getCurfewedDays() > curfewDays) { // If he finished his curfew, reset him
							teenager.setCurfewed(false);
							teenager.setCurfewedDays(0);
							teenager.setImmuneToCurfews(true);
						}
					}
					if (person.isImmune() && !person.isSick()) {
						// Do nothing
					} else if (person.isSick()) {
						person.incrementDaysSick();
					} else {
						ArrayList<Person> friends = person.getFriends();
						for (Person friend : friends) {
							if (friend.getDaysSick() > 0 && friend.isSick()) {
								boolean getSick;
								//System.out.println(person + " has a get vac of : " + person.getSuceptability());
								if(modelTownSim) {
									getSick = (new Random().nextInt(99) + 1) < person.getSuceptability()*percentSick; //because susceptabilities are relative [incorrect]
								}
								else{
									getSick = (new Random().nextInt(99) + 1) < percentSick;
								}
								boolean getVacc = (new Random().nextInt(99) + 1) < getVac;
								if (getSick) {
									person.setSick(true);
									totalSickPeople.add(person.getID());
									break;
								}
								else{
									//System.out.println("Person " + person + " did not get sick from " + friend);
								}
								if (getVacc) {
									person.getWell();
									totalImmunePeople.add(person.getID());
									cost++;
									break;
								}
							}
						}
					}
					if (person.getDaysSick() >= getWellDays) { // && !person.isImmune()
						person.getWell();
						totalImmunePeople.add(person.getID());
					}
				}
				day++;
				//FOR JUNG
				if (runTime == 0) {
					ArrayList<Person> vaccPeople = new ArrayList<Person>();
					ArrayList<Person> sickPeople = new ArrayList<Person>();
					for (Person p : people) {
						if (p.isImmune()) {
							vaccPeople.add(p);
						}
						if (p.isSick()) {
							sickPeople.add(p);
						}
					}

					jungStorage.add(new JungStorage(vaccPeople, sickPeople, day));
				}
				//System.out.println("Day is : " + day);
				//System.out.println(getNumSickPeople(people));
//				for(Person p : people){
//					if(p.isSick() && day == 1000){
//						System.out.println("The Sick Annoying Person is: " + p + ". THIS PERSON IS VACCINATED: " + p.isImmune() + ". The Day IS: " + day + " .The Runtime is: " + runTime + " out of: " + runTimes + "He has been sick for: " + p.getDaysSick());
//						System.out.println("He is Included in Immune People " + totalImmunePeople.contains(p));
//						System.out.println("He is Included in Sick People" + totalSickPeople.contains(p));
//						System.out.println(people.contains(p));
//					}	
//				}
				//System.out.println("Total sick:" + totalSickPeople.size());
				infoStorage.get(runTime).add(new InfoStorage(day, getNumSickPeople(people), totalSickPeople.size(), cost, totalImmunePeople.size()));
				//System.out.println(people);
				//System.out.println(getNumSickPeople(people));
			}
			averageDuration += day;

			//For any other days, make totalSickPeople equal to the number of total sick people on the last day
			while (day < ManyLinesAverageObject.daysLimit) {
				day++;
				//System.out.println("totalSickPeople.size() = " + totalSickPeople.size());
				infoStorage.get(runTime).add(new InfoStorage(day, 0, totalSickPeople.size(), cost, totalImmunePeople.size()));
			}

			day = 0;
			cost = origVacc;

			totalSickPeople.clear();
			totalImmunePeople.clear();

			//This should be the new RestAll method but i'm not changing it for now...
			for(Person p : people){
				p.setSick(false);
				p.setImmune(false);
				p.setDaysSick(0);
			}
			//people.get(2).setSick(true); //TODO:Yes, yes! This is the problem. Resetting does not work properly. For now, setting 2 as sick to make it work.
			// It should, Person.reset() sets the person's sick and vacc states to their original sick and vacc states...
			//System.out.println("LO AND BEHOLD NEW RUNTIME!!!!!!!!!!!!!!");
		}

		averageDuration /= runTimes;
		ManyLinesAverageObject.maxDays = averageDuration;


		InfoJungStorage infoJungStorage = new InfoJungStorage(infoStorage, jungStorage);
		//System.out.println("SIMULATION COMPLETE");
		return infoJungStorage;
	}

	public static ArrayList<ArrayList<TransmissionTestInfoStorage>> transmissionTest(ArrayList<Person> people, ArrayList<Person> teenagers, int getWellDays, int origSick, int origVacc, int discovery, int newGetWellDays, int percentSick, int getVac, int curfewDays, int runTimes, int percentCurfewed) {
		ArrayList<ArrayList<TransmissionTestInfoStorage>> infoStorage = new ArrayList<ArrayList<TransmissionTestInfoStorage>>();
		for (int k = 0; k < runTimes; k++) { //Populate arraylist of arraylists with arraylists
			infoStorage.add(new ArrayList<TransmissionTestInfoStorage>());
		}
		for (int i = 0; i < 100; i += 5) {
			percentSick = i;

			int day = 0;
			//int cost = origVacc;

			//System.out.println("RunTimes: " + runTimes);

			// Make progress bar
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 0));
			panel.add(new JLabel("Running simulations..."));
			JFrame pBar = new JFrame();
			JProgressBar progressBar = new JProgressBar(0, 100);
			progressBar.setValue(0);
			progressBar.setIndeterminate(false);
			progressBar.setStringPainted(true);
			panel.add(progressBar);
			panel.setBorder(new EmptyBorder(5, 10, 5, 10));
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			progressBar.setStringPainted(true);
			//progressBar.setBackground(new Color(255, 255, 255));
			//progressBar.setForeground(new Color(0, 0, 0));
			pBar.add(panel);
			pBar.pack();
			pBar.setLocationRelativeTo(null);
			pBar.setResizable(false);
			pBar.setVisible(true);
			pBar.setTitle("Running simulations...");
			pBar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			ArrayList<Integer> totalSickPeople = new ArrayList<Integer>();
			for (int runTime = 0; runTime < runTimes; runTime++) {
				int value = runTime * 100 / runTimes;
				progressBar.setValue(value);
				if (runTime == Math.floor(runTimes / 4)) {
					pBar.setTitle("Running simulations... 25%");
				}
				if (runTime == Math.floor(runTimes / 2)) {
					pBar.setTitle("Running simulations... 50%");
				}
				if (runTime == Math.floor(runTimes * 0.75)) {
					pBar.setTitle("Running simulations... 75%");
				}
				//System.out.println(runTime);
				while (getNumSickPeople(people) > 0) {
					//int numberSickOnDay = 0;
					for (Person person : people) {
						for (Person teenager : teenagers) {
							teenager.incrementCurfewedDays();
							if (!teenager.isImmuneToCurfews()) {
								if (randInt(1, 100 / percentCurfewed) == 1) {
									teenager.setCurfewed(true);
								}
							}
							if (teenager.getCurfewedDays() > curfewDays) { // If he finished his curfew, reset him
								teenager.setCurfewed(false);
								teenager.setCurfewedDays(0);
								teenager.setImmuneToCurfews(true);
							}
						}
						if (person.isImmune()) {
							// Do nothing
						} else if (person.isSick()) {
							person.incrementDaysSick();
						} else {
							ArrayList<Person> friends = person.getFriends();
							for (Person friend : friends) {
								if (friend.getDaysSick() > 0 && friend.isSick()) {
									boolean getSick = (new Random().nextInt(99) + 1) < percentSick;
									boolean getVacc = (new Random().nextInt(99) + 1) < getVac;
									if (getSick) {
										person.setSick(true);
										totalSickPeople.add(person.getID());
										break;
									}
									if (getVacc) {
										person.getWell();
										//cost++;
										break;
									}
								}
							}
						}
						//System.out.println(person.getDaysSick());
						if (person.getDaysSick() == getWellDays && !person.isImmune()) {
							person.getWell();
						}
					}
				}
				//For any other days, make totalSickPeople equal to the number of total sick people on the last day
				infoStorage.get(runTime).add(new TransmissionTestInfoStorage(percentSick, day));

				day = 0;
				//cost = origVacc;

				totalSickPeople.clear();

				resetAll(people);
				people.get(2).setSick(true); //Yes, yes! This is the problem. Resetting does not work properly. For now, setting 2 as sick to make it work.
				// It should, Person.reset() sets the person's sick and vacc states to their original sick and vacc states...
			}
		}

		return infoStorage;
	}

	// Simulation for MasterManySims - DEPRECATED -----------------------
//	public static ArrayList<Integer> simulate(ArrayList<Person> people, int getWellDays, int origSick, int origVacc, int discovery, int newGetWellDays, int percentSick, int percentVacc, int curfewDays, int runTimes) {
//		int day = 0;
//		int totalDay = 0;
//		int cost = origVacc;
//		int totalCost = 0;
//		int totalSick = origSick;
//		int totalTotalSick = 0;
//
//		for (int runTime = 1; runTime <= runTimes; runTime++) {
//			//System.out.println("Simulation RunTime: " + runTime);
//			while (getNumSickPeople(people) > 0) {
//				for (Person person : people) {
//					if (person.isTeenager()) {
//						if (day == curfewDays) {
//							person.setCurfewed(false);
//							person.setCurfewedDays(0);
//							person.setImmuneToCurfews(true);
//						}
//					}
//					if (person.isImmune()) {
//						// Do nothing
//					} else if (person.isSick()) {
//						person.incrementDaysSick();
//					} else {
//						ArrayList<Person> friends = person.getFriends();
//						for (Person friend : friends) {
//							if (friend.getDaysSick() > 0 && friend.isSick()) {
//								boolean getSick = (new Random().nextInt(99) + 1) < percentSick;
//								boolean getVacc = (new Random().nextInt(99) + 1) < percentVacc;
//								if (getSick) {
//									person.setSick(true);
//									totalSick++;
//									break;
//								}
//								if (getVacc) {
//									person.getWell();
//									cost++;
//									break;
//								}
//							}
//						}
//					}
//					//System.out.println(person.getDaysSick());
//					if (person.getDaysSick() == getWellDays && !person.isImmune()) {
//						person.getWell();
//					}
//				}
//				day++;
//				//System.out.println(people);
//				//System.out.println(getNumSickPeople(people));
//			}
//			totalDay += day;
//			day = 0;
//			totalCost += cost;
//			cost = origVacc;
//			totalTotalSick += totalSick;
//			totalSick = origSick;
//			resetAll(people);
//		}
//
//		day = totalDay / runTimes;
//		cost = totalCost / runTimes;
//		totalSick = totalTotalSick / runTimes;
//
//		ArrayList<Integer> results = new ArrayList<Integer>();
//		results.add(day);
//		results.add(cost);
//		results.add(totalSick);
//
//		return results;
//	}

	/**
	 * WORKING WITH JFREECHART
	 */

	public static File makeChart(DefaultCategoryDataset dataset, String filename, String title, String xAxis, String yAxis) throws IOException {
		JFreeChart lineChartObject = ChartFactory.createLineChart(title, xAxis, yAxis, dataset, PlotOrientation.VERTICAL, true, true, false);
		
		int width = 1920;
		int height = 1440;
		File lineChart = new File(filename + ".png");
		ChartUtilities.saveChartAsPNG(lineChart, lineChartObject, width, height);

		return lineChart;
	}

	public static File makeChart(XYSeriesCollection dataset, String filename, String title, String xAxis, String yAxis) throws IOException {
		JFreeChart lineChartObject = ChartFactory.createXYLineChart(title, xAxis, yAxis, dataset, PlotOrientation.VERTICAL, true, true, false);
		
		XYPlot plot = (XYPlot) lineChartObject.getPlot();		
		int seriesCount = plot.getSeriesCount();

		for (int i = 0; i < seriesCount; i++) {
		   plot.getRenderer().setSeriesStroke(i, new BasicStroke(10));
		}
		
		int width = 960;
		int height = 720;
		File lineChart = new File(filename + ".png");
		ChartUtilities.saveChartAsPNG(lineChart, lineChartObject, width, height);

		return lineChart;
	}

	public static void addPoint(DefaultCategoryDataset dataset, int yValue, String lineLabel, String xValue) {
		dataset.addValue(yValue, lineLabel, xValue);
	}

	public static void addPoint(DefaultCategoryDataset dataset, float yValue, String lineLabel, String xValue) {
		dataset.addValue(yValue, lineLabel, xValue);
	}

	public static void addPoint(DefaultCategoryDataset dataset, int yValue, String lineLabel, int xValue) {
		String newXValue = Integer.toString(xValue); //To disambiguate the method call
		dataset.addValue(yValue, lineLabel, newXValue);
	}

	public static void addPoint(XYSeries series, int xValue, double yValue) {
		series.add(xValue, yValue);
	}

	public static LinkedHashMap<Integer, Double> getDerivative (LinkedHashMap<Integer, Double> averages) {
		Set<Integer> keySet = averages.keySet();
		ArrayList<Integer> keyList = new ArrayList<Integer>();
		for (int key : keySet) {
			keyList.add(key);
		}

		LinkedHashMap<Integer, Double> derivative = new LinkedHashMap<Integer, Double>();

		for (int i = 1; i < keySet.size() - 1; i++ ) {
			double deriv1 = (1.0 * averages.get(keyList.get(i + 1)) - 1.0 * averages.get(keyList.get(i - 1)))/ (1.0 * keyList.get(i + 1) - 1.0 * keyList.get(i - 1));
			double deriv2 = ((1.0 * averages.get(keyList.get(i + 1)) - 1.0 * averages.get(keyList.get(i)))/ (1.0 * keyList.get(i + 1) - 1.0 * keyList.get(i)) + (1.0 * averages.get(keyList.get(i)) - 1.0 * averages.get(keyList.get(i - 1)))/ (1.0 * keyList.get(i) - 1.0 * keyList.get(i - 1)) ) / 2;
			if (deriv1 < deriv2) {
				derivative.put(keyList.get(i), deriv2);
			}
			else {
				derivative.put(keyList.get(i), deriv1);
			}
			//derivative.put(keyList.get(i), (deriv1 + deriv2) / 2);
		}

		return derivative;
	}

	//Alert function. Creates an alert dialog with some text.
	public static void alert(String text, String title) {
		JOptionPane.showMessageDialog(new JFrame(), text, title, JOptionPane.INFORMATION_MESSAGE);
	}

	//Data mining
	public static int getColumnByType(int type) {
		return type + 3;
	}

	//InfoStorageStuffForCompatabilityLongCommentSos
	public InfoStorage averageInfostorage(ArrayList<ArrayList<InfoStorage>> simOutput){
		ArrayList<Double> costs = new ArrayList<Double>();
		ArrayList<Double> days = new ArrayList<Double>();
		ArrayList<Double> totalSick = new ArrayList<Double>();
		ArrayList<Double> totalImmune = new ArrayList<Double>();

		//Add to arrayLists
		for (ArrayList<InfoStorage> runtimeList : simOutput) {
			//System.out.println("Runtime size = " + runtimeList.size());
			InfoStorage lastInfoStorage = runtimeList.get(runtimeList.size() - 1); //Aka the last info storage
			costs.add(lastInfoStorage.getCost());
			//System.out.println("Get Cost = " + lastInfoStorage.getDay());
			days.add(lastInfoStorage.getDay());
			//System.out.println("Get day = " + lastInfoStorage.getDay());
			totalSick.add(lastInfoStorage.getTotalSick());
			//System.out.println("Get totalSick = " + lastInfoStorage.getTotalSick());
			totalImmune.add(lastInfoStorage.getImmune());
		}
		double avgCost = findAverage(costs);
		double avgDays = findAverage(days);
		double avgTotalSick = findAverage(totalSick);
		double avgImmunePeople = findAverage(totalImmune);

		InfoStorage avgInfoStorage = new InfoStorage((double) avgDays, (double)0, (double)avgTotalSick, (double)avgCost, (double) avgImmunePeople);

		return avgInfoStorage;
	}

	public double findAverage(ArrayList<Double> numbers){
		double result = 0;
		for(double i : numbers){
			result = result + i;
		}
		return result/numbers.size();
	}
}