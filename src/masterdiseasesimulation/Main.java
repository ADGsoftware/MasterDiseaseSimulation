package masterdiseasesimulation;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;

public class Main {
    public static void main(String[] args) throws WriteException, BiffException, IOException {
		/*
		JPanel panel = new JPanel();
		
		String[] programs = {"ManyLinesAverage", "MasterManySims"};
		JComboBox programChoice = new JComboBox(programs);
		
		panel.add(new JLabel("What program to run?"));
		panel.add(programChoice);
		
		int result = JOptionPane.showConfirmDialog(null, panel, "Program Choice", JOptionPane.OK_CANCEL_OPTION);

		if (result != JOptionPane.OK_OPTION) { // If user clicked something other than OK
			System.exit(0);
		}
		
		String programString = (String) programChoice.getSelectedItem();
		
		if (programString.equals("ManyLinesAverage")) {
			ManyLinesAverageObject.run();
		}
		else {
			MasterManySimsObject.run();
		}
		*/

        //while (true) { // Uncomment to loop program
        Object[] options = {"MasterManySims", "ManyLinesAverage", "Analyze", "Draw Histograms"};
        int selection = JOptionPane.showOptionDialog(null, "What program to run?", "Program Choice", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);

        if (selection == 3) {
            HistogramGenerator.run();
        }
        else if (selection == 2) {
            while (true) {
                ArrayList<Integer> best = UserInterface.analyze();
                if (best == null) {
                    JOptionPane.showMessageDialog(new JFrame(), "ERROR: No valid solution found. Please try again with valid input.", "No Solution Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    UserInterface.displayMessage("The best option for the entered input is: " + best + "\nnumPeople: " + best.get(0) + "\nminFriends: " + best.get(1) + "\nmaxFriends: " + best.get(2) + "\nhubNumber: " + best.get(3) + "\ngetWellDays: " + best.get(4) + "\ndiscovery: " + best.get(5) + "\nnewGetWellDays: " + best.get(6) + "\ninitiallySick: " + best.get(7) + "\ninitiallyVacc: " + best.get(8) + "\npercentSick: " + best.get(9) + "\ngetVac: " + best.get(10) + "\ncurfewDays: " + best.get(11) + "\npercentTeens: " + best.get(12) + "\npercentCurfew: " + best.get(13) + "\n\ndays: " + best.get(14) + "\ncost: " + best.get(15) + "\ntotalSick: " + best.get(16));
                }
            }
        }
        else if (selection == 1) {
            ManyLinesAverageObject.run();
        }
        else if (selection == 0) {
            MasterManySimsObject.run();
        }
        else {
            System.exit(0);
        }
        //}
    }
}