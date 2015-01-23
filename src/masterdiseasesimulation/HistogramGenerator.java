package masterdiseasesimulation;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

public class HistogramGenerator {
	public static void run () throws IOException {
		boolean done = false;

		int numPeople = 0;
		ArrayList<Person> people = new ArrayList<Person>();
		MoreMethods methods = new MoreMethods();
		ArrayList<String> networks = new ArrayList<String>();

		int maxFriends = 5;
		int minFriends = 2;
		int hubNumber = 0;
		boolean open = false;

		while (!done){
			//Interface SHtuff

			JPanel panel = new JPanel(new GridLayout(7, 0));
			JTextField numPeopleField = new JTextField("100", 10);
			JTextField minFriendsField = new JTextField("2", 10);
			JTextField maxFriendsField = new JTextField("5", 10);
			JCheckBox checkBoxSW = new JCheckBox();
			JCheckBox checkBoxRand = new JCheckBox();
			JCheckBox checkBoxSF = new JCheckBox();
			JCheckBox openBox = new JCheckBox();

			panel.add(new JLabel("How Many People Should This Run For?   "));
			panel.add(numPeopleField);
			panel.add(new JLabel("minFriends:"));
			panel.add(minFriendsField);
			panel.add(new JLabel("maxFriends"));
			panel.add(maxFriendsField);
			panel.add(new JLabel("Small World Network"));
			panel.add(checkBoxSW);
			panel.add(new JLabel("Random Network"));
			panel.add(checkBoxRand);
			panel.add(new JLabel("Scale Free Network"));
			panel.add(checkBoxSF);
			panel.add(new JLabel("Open histogram?"));
			panel.add(openBox);

			int result = JOptionPane.showConfirmDialog(null, panel, "Configuration", JOptionPane.OK_CANCEL_OPTION);

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

				if (checkBoxSW.isSelected()){
					networks.add("SW");
				}
				if (checkBoxRand.isSelected()){
					networks.add("Rand");
				}
				if (checkBoxSF.isSelected()){
					networks.add("SF");
				}

				if (openBox.isSelected()) {
					open = true;
				}
				done = true;
			}
			catch(NumberFormatException e){
				if (result == JOptionPane.OK_OPTION) {
					JOptionPane.showMessageDialog(new JFrame(), "ERROR: Input is invalid.", "Input Error", JOptionPane.ERROR_MESSAGE);
				} else {
					System.exit(0);
				}
			}
		}

		for (int x = 0; x < numPeople; x++){
			Person person = new Person(x + 1);
			people.add(person);
		}

		// Make progress bar
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		panel.add(new JLabel("Making histograms..."));
		JFrame frame = new JFrame();
		JProgressBar progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		panel.add(progressBar);
		panel.setBorder(new EmptyBorder(5, 10, 5, 10));
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		for (String networkType : networks){
			if (networkType == "SW"){
				methods.befriendSmallWorld(people, minFriends, maxFriends, new Random(), hubNumber);
			}
			if (networkType == "Rand"){
				methods.befriendRandom(people, minFriends, maxFriends, new Random(), hubNumber);
			}
			if (networkType == "SF"){
				methods.befriendScaleFree(people, minFriends, maxFriends, new Random());
			}

			File histogram = makeHistogram(people, numPeople, maxFriends, networkType);
			if (open) {
				Desktop.getDesktop().open(histogram);
			}

			for (Person person : people){
				person.clearFriends();
			}
		}

		frame.dispose();
	}

	public static File makeHistogram(ArrayList<Person> people, int numPeople, int maxFriends, String networkType) throws IOException{
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

		JFreeChart histogramObject = ChartFactory.createHistogram("FriendNumberAnalysis - " + networkType, "NumberOfFriends", "NumberOfPeople", dataset, PlotOrientation.VERTICAL, true, true, false);

		int width = 640;
		int height = 480;

		XYBarRenderer br = (XYBarRenderer) histogramObject.getXYPlot().getRenderer();

		File histogram = new File(numPeople + networkType + ".png");
		ChartUtilities.saveChartAsPNG(histogram, histogramObject, width, height);

		return histogram;
	}
}