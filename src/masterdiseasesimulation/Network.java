package masterdiseasesimulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

@SuppressWarnings("serial")
public class Network extends ArrayList<Person> {
	private int hubNumber;
	private int maxFriends;
	private int minFriends;
	private int numPeople;

	public Network(String type, int numPeople, int minFriends, int maxFriends, int hubNumber){
		for(int x = 0; x < numPeople; x++){
			Person person = new Person(x + 1);
			this.add(person);
		}
		this.numPeople = numPeople;
		this.maxFriends = maxFriends;
		this.minFriends = minFriends;
		this.hubNumber = hubNumber;
		if(type == "SW"){
			befriendSmallWorld(this, minFriends, maxFriends, new Random(), hubNumber);
		}
		if(type == "SF"){
			befriendScaleFree(this, minFriends, maxFriends, new Random());
		}
		if(type == "Rand"){
			befriendRandom(this, minFriends, maxFriends, new Random(), hubNumber);
		}
	}
	//--------------------------------------------------------------------------NETWORK CONSTRUCTION METHODS---------------------------------------------------------------------------------------------------------------------------
	public void befriendRandom(ArrayList<Person> people, int minFriends, int maxFriends, Random random, int hubNumber){//It will be possible to make this algorhyth even better suggest way!!1 
		//Choose people for hubs
		makeHubs(hubNumber, people, random);
		assignCapacities(people, minFriends, maxFriends, random);
		// Make friends
		ArrayList<Person> peopleReference = new ArrayList<Person>();
		for(Person newPerson : people){
			peopleReference.add(newPerson);
		}
		for(Person person : people){
			//Hub Case
			if(person.isHub()){
				person.setCapacity(people.size() + 1);
				for(Person hubFriend : people){
					if((hubFriend != person) && !(person.getFriends().contains(hubFriend))){
						if((random.nextInt(100) + 1 > 20) && !(hubFriend.capacityFull())){
							person.addFriend(hubFriend);
							hubFriend.addFriend(person);
						}
					}
				}
			}
			// If not HUB
			else{
				Collections.shuffle(peopleReference);
				//System.out.println(person.getID());
				for(Person possibleFriend: peopleReference){
					//System.out.println(possibleFriend.getID());
					if(possibleFriend.getID() <= person.getID()){
						//System.out.println("PERSON BEFRIEND HIMSELF ERROR");
					}
					else if(possibleFriend.capacityFull() || person.capacityFull()){
						//System.out.println("Capacity full ERROR");
					}
					else{
						// System.out.println("FRIENDSHIP!!!!!!!!!!!");
						person.addFriend(people.get(possibleFriend.getID()-1));
						people.get(possibleFriend.getID()-1).addFriend(person);
					}
				}
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
			for(int x = 0; x < halfRange*2; x++){
				ID = (((ID)) % numPeople + numPeople) % numPeople;
				if(ID == 0){
					ID = numPeople;
				}
				possibleFriends.add(people.get(ID -1));
				ID = (((ID + 1)) % numPeople + numPeople) % numPeople;
			}
			//System.out.println(person + " : " + possibleFriends + " ' " + person.getCapacity());		
			while(!person.capacityFull()){
				Collections.shuffle(possibleFriends);
				for(Person friendApplicant : possibleFriends){
					if(person.capacityFull()){
						break;
					}
					if(friendApplicant.capacityFull()){
						continue;
					}
					if(person.getFriends().contains(friendApplicant)){
						continue;
					}
					if(person == friendApplicant){
						continue;
					}
					if(!person.getFriends().contains(friendApplicant) && !friendApplicant.getFriends().contains(person)){
						person.addFriend(people.get(friendApplicant.getID() - 1));
						people.get(friendApplicant.getID() - 1).addFriend(person);
					}			
				}
				counter = 0;
				for(Person control : possibleFriends){
					if(control.capacityFull()){
						counter++;
					}
					if(person.getFriends().contains(control)){
						counter++;
					}
					if(control == person){
						counter++;
					}
				}
				if(counter >= possibleFriends.size()){
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
	//-----------------------------------------------------------------------------------------------------------------------------------------------METHODS USED BY NETWROK CONSTRUCTORS-------------------------------------------------------------------------------------------------------------------------------------------
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
	public void assignCapacities(ArrayList<Person> people, int minFriends, int maxFriends, Random random) { // Assign friend capacities to each person
		int randomInt;
		for (Person person : people) {
			randomInt = random.nextInt((maxFriends - minFriends) + 1) + minFriends;
			person.setCapacity(randomInt);
		}
	}
	//-------------------------------------------------------------------------------------------------------------------------------------METHODS TO GET AND STORE VALUES------------------------------------------------------------------------------------------------------------------------------------------------
	public int getMinFriends(){
		return this.minFriends;
	}
	public int getMaxFriends(){
		return this.maxFriends;
	}
	public int getHubNumber(){
		return this.hubNumber;
	}
	public int getNumPeople() {
		return this.numPeople;
	}
	//------------------------------------------------------------------------------------------------------------------------------METHODS THAT RETURN VALUES CALCULATED USING THE NETWORK---------------------------------------------------------------------------------------------------------------------------
	public ArrayList<ArrayList<Integer>> createFriendTable(){
		ArrayList<ArrayList<Integer>> table = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> friendNumbers = new ArrayList<Integer>();
		ArrayList<Integer> friendNumberValuesUnsorted = new ArrayList<Integer>();

		for(Person person: this){
			friendNumberValuesUnsorted.add(person.getNumFriends());
		}
		Collections.sort(friendNumberValuesUnsorted);		
		for(int x = 0; x <= friendNumberValuesUnsorted.get(friendNumberValuesUnsorted.size()-1); x++){
			friendNumbers.add(x);
		}
		removeDuplicate(friendNumbers);

		for(int number : friendNumbers){
			ArrayList<Integer> row = new ArrayList<Integer>();
			row.add(number);
			row.add(Collections.frequency(friendNumberValuesUnsorted, number));
			table.add(row);
		}
		return table;
	}
	public int maxFriendNumber(){
		int result = 0;
		for(Person person: this){
			if(result < person.getNumFriends()){
				result = person.getNumFriends();
			}
		}
		return result;
	}
	// -----------------------------------------------------------------------------------------------------------------------------------------------METHODS FOR DRAWING WITH JUNG------------------------------------------------------------------------------------------------------------------
	public void draw(String layoutString){
		UndirectedSparseMultigraph<Person, String> graph = new UndirectedSparseMultigraph<Person, String>();
		JFrame frame = new JFrame("ManyLinesAverage");
		drawVerticies(graph, this);

		// Create vv and layout
		int xDim = 1000;
		int yDim = 600;
		//LAYOUT STUFF THAT SHOULD WORK BUT PROBABLY WON"T
		Layout<Person, String> layout = chooseLayout(graph, layoutString);
		layout.setSize(new Dimension(xDim, yDim));

		VisualizationViewer<Person, String> vv = new VisualizationViewer<Person, String>(layout);
		vv.setPreferredSize(new Dimension(xDim + 50, yDim + 50));
		vv.getRenderContext().setVertexLabelTransformer(Person.labelByID);//Makes Labels on Vertices
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

		// Graph Mouse
		DefaultModalGraphMouse<Object, Object> mouse = new DefaultModalGraphMouse<Object, Object>();
		mouse.setMode(ModalGraphMouse.Mode.PICKING);
		vv.addKeyListener(mouse.getModeKeyListener());
		vv.setGraphMouse(mouse);

		//DrawJung
		drawJung(graph, vv, this);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(vv);
		frame.pack();
		frame.setVisible(true);
	}
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
	//-----------------------------------------------------------------------------------------------------------------------------------------------------MISCELLANEOUS-------------------------------------------------------------------------------------------------------------------------------------------------------------
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
}