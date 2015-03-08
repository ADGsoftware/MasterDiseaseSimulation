package masterdiseasesimulation;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import moremethods.MoreMethods;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

public class HistogramGenerator {
	public static void run () throws IOException {
		boolean done = false;
		
		int numPeople = 0;
		ArrayList<Person> people = new ArrayList<Person>();
		MoreMethods methods = new MoreMethods();
		ArrayList<String> networks = new ArrayList<String>();
		
		int maxFriends = 0;
		int minFriends = 0;
		int hubNumber = 0;
		int numRepeats = 0;
		Boolean average = false;
		Boolean open = false;
		String analysisChoice = null;
		
		while(!done){
			//Interface SHtuff
			
			JPanel panel = new JPanel(new GridLayout(11, 0));
			JTextField numPeopleField = new JTextField("100", 10);
			JTextField minFriendsField = new JTextField("2", 10);
			JTextField maxFriendsField = new JTextField("5", 10);
			JTextField hubNumberField = new JTextField("0", 10);
			JTextField numRepeatsField = new JTextField("100", 10);
			JCheckBox checkBoxSW = new JCheckBox();
			JCheckBox checkBoxRand = new JCheckBox();
			JCheckBox checkBoxSF = new JCheckBox();
			JCheckBox checkBoxAverage = new JCheckBox();
			JCheckBox checkBoxOpen = new JCheckBox();
			String[] analysisTypeChoices = {"Friends", "Ages"};
			final JComboBox comboBoxAnalysisType = new JComboBox(analysisTypeChoices);
			
			panel.add(new JLabel("How many people should this run for?"));
			panel.add(numPeopleField);
			panel.add(new JLabel("What should minFriends be?"));
			panel.add(minFriendsField);
			panel.add(new JLabel("What should maxFriends be?"));
			panel.add(maxFriendsField);
			panel.add(new JLabel("What should hubNumber be?"));
			panel.add(hubNumberField);
			panel.add(new JLabel("What should the number of repeats be?   "));
			panel.add(numRepeatsField);
			panel.add(new JLabel("Small World Network"));
			panel.add(checkBoxSW);
			panel.add(new JLabel("Random Network"));
			panel.add(checkBoxRand);
			panel.add(new JLabel("Scale Free Network"));
			panel.add(checkBoxSF);
			panel.add(new JLabel("Make average network graph?"));
			panel.add(checkBoxAverage);
			panel.add(new JLabel("Open file?"));
			panel.add(checkBoxOpen);
			panel.add(new JLabel("Which type of analysis would you like to preform?"));
			panel.add(comboBoxAnalysisType);
			
			int result = JOptionPane.showConfirmDialog(null, panel, "UI", JOptionPane.OK_CANCEL_OPTION);
	
			if (result != JOptionPane.OK_OPTION) {
				System.exit(0);
			}
			
			try{
				numPeople = Integer.parseInt(numPeopleField.getText());
				if (numPeople <= 0) {
					throw new NumberFormatException();
				}

				minFriends = Integer.parseInt(minFriendsField.getText());
				if (minFriends >= numPeople || minFriends < 0) {
					throw new NumberFormatException();
				}

				maxFriends = Integer.parseInt(maxFriendsField.getText());
				if (maxFriends >= numPeople || maxFriends < 0 || maxFriends < minFriends) {
					throw new NumberFormatException();
				}
				hubNumber = Integer.parseInt(hubNumberField.getText());
				if (hubNumber < 0) {
					throw new NumberFormatException();
				}
				numRepeats = Integer.parseInt(numRepeatsField.getText());
				if (numRepeats <= 1) {
					throw new NumberFormatException();
				}
				if(checkBoxSW.isSelected()){
					networks.add("SW");
				}
				if(checkBoxRand.isSelected()){
					networks.add("Rand");
				}
				if(checkBoxSF.isSelected()){
					networks.add("SF");
				}
				if(checkBoxAverage.isSelected()){
					average = true;
				}
				if(checkBoxOpen.isSelected()){
					open = true;
				}
				done = true;
				analysisChoice = (String) comboBoxAnalysisType.getSelectedItem();
			}
			catch(NumberFormatException e){
				if (result == JOptionPane.OK_OPTION) {
					JOptionPane.showMessageDialog(new JFrame(), "ERROR: Input is invalid.", "Input Error", JOptionPane.ERROR_MESSAGE);
				} else {
					System.exit(0);
				}
			}
		}
		if(analysisChoice.equals("Friends")){
			if(!average){
				for(int x = 0; x < numPeople; x++){
					Person person = new Person(x + 1);
					people.add(person);
				}
				for(String networkType : networks){
					if(networkType == "SW"){
						methods.befriendSmallWorld(people, minFriends, maxFriends, new Random(), hubNumber);
					}
					if(networkType == "Rand"){
						methods.befriendRandom(people, minFriends, maxFriends, new Random(), hubNumber);
					}
					if(networkType == "SF"){
						methods.befriendScaleFree(people, minFriends, maxFriends, new Random());
					}
					makeHistogram(people, numPeople + networkType);
					for(Person person : people){
						person.clearFriends();
					}
				}
			}
			else{
				for(String networkType : networks){
					File histogram = makeHistogramAverage(networkType, numPeople, minFriends, maxFriends, hubNumber, numRepeats, open);
					if(open){
						Desktop.getDesktop().open(histogram);
					}
				}
			}
		}
		else{
			
		}
	}
//-----------------------------------------------------------------------MAKE HISTOGRAM METHODS--------------------------------------------------------------------------------------------------
    public static void makeHistogram(ArrayList<Person> people, String filename) throws IOException{
    	HistogramDataset dataset = new HistogramDataset();
    	dataset.setType(HistogramType.FREQUENCY);
    	ArrayList<Double> friendNumbers = new ArrayList<Double>();
    	double[] friendNumbersDoubleList = new double[people.size()];
    	for(Person person: people){
    		friendNumbers.add((double)(person.getFriends().size()));
    	}
    	for(int i = 0; i < friendNumbers.size(); i++){
    		friendNumbersDoubleList [i] = friendNumbers.get(i);
    	}
    	//System.out.println(friendNumbersDoubleList);
    	Collections.sort(friendNumbers);
    	dataset.addSeries("Histogram", friendNumbersDoubleList, (friendNumbers.get(friendNumbers.size() - 1)).intValue()*2, 0, friendNumbers.get(friendNumbers.size() - 1) + 0.5);   
    	JFreeChart histogramObject = ChartFactory.createHistogram("FriendNumberAnalysis", "NumberOfFriends", "NumberOfPeople", dataset, PlotOrientation.VERTICAL, true, true, false);
    	
    	int width = 640;
        int height = 480;

    	File histogram = new File(filename + ".png");
    	ChartUtilities.saveChartAsPNG(histogram, histogramObject, width, height);
    }
    public static File makeHistogramAverage(String networkType, int numPeople, int minFriends, int maxFriends, int hubNumber, int numRepeats, boolean open) throws IOException{
    	DefaultStatisticalCategoryDataset dataset = new DefaultStatisticalCategoryDataset();

    	ArrayList<Network> networksArray = new ArrayList<Network>();
    	for(int i = 0; i < numRepeats; i++){
    		Network network = new Network(networkType, numPeople, minFriends, maxFriends, hubNumber);
    		networksArray.add(network);
    	}
    	ArrayList<ArrayList<ArrayList<Integer>>> tablesList = new ArrayList<ArrayList<ArrayList<Integer>>>();
    	ArrayList<Integer> maxFriendsNums = new ArrayList<Integer>();
    	for(Network network : networksArray){
    		ArrayList<ArrayList<Integer>> table = network.createFriendTable();
    		tablesList.add(table);
    		maxFriendsNums.add(table.get(table.size()-1).get(0));
    	}
    	
    	Collections.sort(maxFriendsNums);
    	int maxFriendNum = maxFriendsNums.get(maxFriendsNums.size()-1);
    	
    	//Creation of Meta Table
    	ArrayList<ArrayList<Integer>> metaTableUnsorted = new ArrayList<ArrayList<Integer>>();
    	for(int number = 0; number <= maxFriendNum; number++){
    		ArrayList<Integer> newList = new ArrayList<Integer>();
    		newList.add(number);
    		for(ArrayList<ArrayList<Integer>> table : tablesList){
    			for(ArrayList<Integer> list : table){
    				if(list.get(0) == number){
    					newList.add(list.get(1));
    					break;
    				}
    			}
    		}
    		metaTableUnsorted.add(newList);
    	}
    	System.out.println(metaTableUnsorted);
    	// Creation of Meta Table with SD and Mean
    	ArrayList<ArrayList<Float>> metaTable = new ArrayList<ArrayList<Float>>();
    	for(ArrayList<Integer> row : metaTableUnsorted){
    		ArrayList<Float> newRow = new ArrayList<Float>();
    		newRow.add((float)(row.get(0)));
    		row.remove(0);
    		newRow.add(mean(row));
    		newRow.add(standardDeveation(row));
    		metaTable.add(newRow);
    	}
    	System.out.println(metaTable);
    	for(ArrayList<Float> row : metaTable){
    		System.out.println(row);
    		dataset.add(row.get(1), row.get(2), "Series1", Float.toString(row.get(0)));
    	}
    	
    	CategoryItemRenderer renderer = new StatisticalBarRenderer();
    	
    	CategoryAxis xAxis = new CategoryAxis("Friend Numbers");
    	xAxis.setLowerMargin(0.01d); // percentage of space before first bar
        xAxis.setUpperMargin(0.01d); // percentage of space after last bar
        xAxis.setCategoryMargin(0.05d); // percentage of space between categories
        ValueAxis yAxis = new NumberAxis("Amount of People");
        
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
        
        JFreeChart chart = new JFreeChart(networkType + numPeople + "AVG", plot);

    	//JFreeChart chart = ChartFactory.createBarChart(networkType + numPeople + "AVG", "FriendNumbers", "Amount of People", dataset);
    	
    	int width = 640;
        int height = 480;
        
    	File histogram = new File(numPeople + " people " + numRepeats + " Repeats " + networkType +  ".png");
    	ChartUtilities.saveChartAsPNG(histogram, chart, width, height);
    	
    	return histogram;
    }
    //---------------------------------------------------------------------------------------------------------------------------------------STATITSTICS MATH METHODS-------------------------------------------------------------------------------------------------------------------------------------------------
    public static float mean(ArrayList<Integer> numbers){
		float result = 0;
		for(int i: numbers){
			result = result + i;
		}
		return result/numbers.size();
	}	

	public static float standardDeveation(ArrayList<Integer> numbers){
		float mean = mean(numbers);
		float result = 0;
		for(int i : numbers){
			result = (float) (result + Math.pow((mean - i), 2));		
		}
		return (float)(Math.sqrt((result/numbers.size())));
	}
}