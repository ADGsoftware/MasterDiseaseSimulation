package masterdiseasesimulation;

import javax.swing.*;
import java.util.ArrayList;

public class Main {
	//private static boolean music = Math.random() < 1; //Random chance of music!

	public static void main(String[] args) throws Exception {
		//Play music
		//if (music) Audio.main(args);

		Object[] options = {"Simulate", "Analyze", "Draw Histograms", "Many Lines Average"};
		int selection = JOptionPane.showOptionDialog(null, "What program to run?", "Program Choice", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
		if (selection ==3){
			ManyLinesAverageObject.run();
		}
		if (selection == 2) {
			HistogramGenerator.run();
		} else if (selection == 1) {
			while (true) {
				ArrayList<Integer> best = UserInterface.analyze();
				if (best == null) {
					JOptionPane.showMessageDialog(new JFrame(), "ERROR: No valid solution found. Please try again with valid input.", "No Solution Error", JOptionPane.ERROR_MESSAGE);
				} else {
					UserInterface.displayMessage("The best option for the entered input is: " + best + "\nnumPeople: " + best.get(0) + "\nminFriends: " + best.get(1) + "\nmaxFriends: " + best.get(2) + "\nhubNumber: " + best.get(3) + "\ngetWellDays: " + best.get(4) + "\ndiscovery: " + best.get(5) + "\nnewGetWellDays: " + best.get(6) + "\ninitiallySick: " + best.get(7) + "\ninitiallyVacc: " + best.get(8) + "\npercentSick: " + best.get(9) + "\ngetVac: " + best.get(10) + "\ncurfewDays: " + best.get(11) + "\npercentTeens: " + best.get(12) + "\npercentCurfew: " + best.get(13) + "\n\ndays: " + best.get(14) + "\ncost: " + best.get(15) + "\ntotalSick: " + best.get(16));
				}
			}
		} else if (selection == 0) {
			MasterManySimsObject.run();
		} else {
			System.exit(0);
		}
	}
}