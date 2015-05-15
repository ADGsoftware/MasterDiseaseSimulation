package masterdiseasesimulation;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import datacontainers.StringStorage;
import moremethods.Feedback;
import moremethods.GetData;
import moremethods.MoreMethods;
import jxl.read.biff.BiffException;

public class UserInterface {
	/**
	 * INPUT METHODS
	 */

	public static ArrayList<Integer> analyze () throws BiffException, IOException {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		int padding = 15;

		JTextField numPeopleAnswer = new JTextField(20);
		JTextField minFriendsAnswer = new JTextField(5);
		JTextField maxFriendsAnswer = new JTextField(5);
		JTextField hubNumberAnswer = new JTextField(5);
		JTextField getWellDaysAnswer = new JTextField(5);
		JTextField percentSickAnswer = new JTextField(5);
		JTextField initiallySickAnswer = new JTextField(5);
		JTextField initiallyVaccAnswer = new JTextField(5);
		JTextField getVaccAnswer = new JTextField(5);
		JTextField discoveryAnswer = new JTextField(5);
		JTextField newGetWellDaysAnswer = new JTextField(5);
		JTextField curfewDaysAnswer = new JTextField(5);
		JTextField percentTeensAnswer = new JTextField(5);
		JTextField percentCurfewAnswer = new JTextField(5);
		JTextField fileNameAnswer = new JTextField("results", 5);

		File file2 = new File("results.xls");
		if (file2.exists()) {
			ArrayList<ArrayList<Integer>> data = MoreMethods.getAllData("results.xls");
			ArrayList<Integer> row1 = data.get(0);

			numPeopleAnswer.setText(Integer.toString(row1.get(0)));
			minFriendsAnswer.setText(Integer.toString(row1.get(1)));
			maxFriendsAnswer.setText(Integer.toString(row1.get(2)));
			hubNumberAnswer.setText(Integer.toString(row1.get(3)));
			getWellDaysAnswer.setText(Integer.toString(row1.get(4)));
			discoveryAnswer.setText(Integer.toString(row1.get(5)));
			newGetWellDaysAnswer.setText(Integer.toString(row1.get(6)));
			initiallySickAnswer.setText(Integer.toString(row1.get(7)));
			initiallyVaccAnswer.setText(Integer.toString(row1.get(8)));
			percentSickAnswer.setText(Integer.toString(row1.get(9)));
			getVaccAnswer.setText(Integer.toString(row1.get(10)));
			curfewDaysAnswer.setText(Integer.toString(row1.get(11)));
			percentTeensAnswer.setText(Integer.toString(row1.get(12)));
			percentCurfewAnswer.setText(Integer.toString(row1.get(13)));
		}

		JTextField[] fields = {numPeopleAnswer, minFriendsAnswer, maxFriendsAnswer, hubNumberAnswer, getWellDaysAnswer, percentSickAnswer, initiallySickAnswer, initiallyVaccAnswer, getVaccAnswer, discoveryAnswer, newGetWellDaysAnswer, curfewDaysAnswer, percentTeensAnswer, percentCurfewAnswer};

		c.ipady = padding;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		panel.add(new JLabel("PEOPLE SETUP:"), c);
		c.gridx = 1;
		c.gridwidth = 4;
		panel.add(new JLabel(" ------------------------------"), c);
		c.gridwidth = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("numPeople:"), c);
		c.gridx = 1;
		panel.add(numPeopleAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("minFriends:"), c);
		c.gridx = 1;
		panel.add(minFriendsAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("maxFriends:   "), c);
		c.gridx = 1;
		panel.add(maxFriendsAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("hubNumber:"), c);
		c.gridx = 1;
		panel.add(hubNumberAnswer, c);

		c.ipady = padding;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("TRANSMISSION SETUP:"), c);
		c.gridx = 1;
		c.gridwidth = 4;
		panel.add(new JLabel(" ------------------------------"), c);
		c.gridwidth = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("getWellDays:"), c);
		c.gridx = 1;
		panel.add(getWellDaysAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("percentSick:"), c);
		c.gridx = 1;
		panel.add(percentSickAnswer, c);

		c.ipady = padding;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("INITIAL CONDITIONS SETUP:"), c);
		c.gridx = 1;
		c.gridwidth = 4;
		panel.add(new JLabel(" ------------------------------"), c);
		c.gridwidth = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("intiallySick:"), c);
		c.gridx = 1;
		panel.add(initiallySickAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("intiallyVacc:"), c);
		c.gridx = 1;
		panel.add(initiallyVaccAnswer, c);

		c.ipady = padding;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("RUNNING TO HOSPITAL SETUP:"), c);
		c.gridx = 1;
		c.gridwidth = 4;
		panel.add(new JLabel(" ------------------------------"), c);
		c.gridwidth = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("getVacc:"), c);
		c.gridx = 1;
		panel.add(getVaccAnswer, c);

		c.ipady = padding;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("CURE SETUP:"), c);
		c.gridx = 1;
		c.gridwidth = 4;
		panel.add(new JLabel(" ------------------------------"), c);
		c.gridwidth = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("discovery:"), c);
		c.gridx = 1;
		panel.add(discoveryAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("newGetWellDays:"), c);
		c.gridx = 1;
		panel.add(newGetWellDaysAnswer, c);

		c.ipady = padding;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("TEENAGERS SETUP:"), c);
		c.gridx = 1;
		c.gridwidth = 4;
		panel.add(new JLabel(" ------------------------------"), c);
		c.gridwidth = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("curfewDays:"), c);
		c.gridx = 1;
		panel.add(curfewDaysAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("percentTeens:"), c);
		c.gridx = 1;
		panel.add(percentTeensAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("percentCurfew:"), c);
		c.gridx = 1;
		panel.add(percentCurfewAnswer, c);

		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("Minimize:"), c);
		c.gridx = 1;
		String[] possibilities = {"Days", "Cost", "Total Sick"};
		JComboBox minimize = new JComboBox(possibilities);
		panel.add(minimize, c);

		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("fileName (.xls added automatically):   "), c);
		c.gridx = 1;
		panel.add(fileNameAnswer, c);

		JPanel header = new JPanel (new GridBagLayout());
		c = new GridBagConstraints();
		c.ipady = 5;
		c.gridx = 0;
		c.gridy = 0;
		header.add(new JLabel("Enter acceptable values for each (leave blank for all values):"), c);

		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		//System.out.println(screenSize.width);

		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Make scroll bars on the panel
		scrollPane.setBounds(0, 0, 500, 500);
		scrollPane.setPreferredSize(new Dimension(515, maxOut((int)(screenSize.height / 1.6), 500))); //515
		//500
		
		JPanel contentPane = new JPanel(new GridBagLayout());
		c = new GridBagConstraints();
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 0;
		contentPane.add(header, c);
		c.gridy = 1;
		contentPane.add(scrollPane, c);

		ArrayList<ArrayList<Integer>> input = new ArrayList<ArrayList<Integer>>();

		String dependent;
		String fileName;
		boolean error = false;

		while (true) {
			int result = JOptionPane.showConfirmDialog(null, contentPane, "Configuration", JOptionPane.OK_CANCEL_OPTION);
			dependent = (String)minimize.getSelectedItem();
			fileName = fileNameAnswer.getText();
			fileNameAnswer.setText(fileName);
			fileName += ".xls";
			file2 = new File(fileName);
			if (!file2.exists()) {
				error = true;
				fileNameAnswer.setText("");
			}

			if (result != JOptionPane.OK_OPTION) {
				error = true;
				System.exit(0);
			}

			String text;
			ArrayList<Integer> list = new ArrayList<Integer>();

			for (JTextField field : fields) {
				text = field.getText();
				field.setText(text);
				try {
					list = MoreMethods.commaListToArrayList(text);
					//System.out.println(list);
					input.add(list);
				}
				catch (NumberFormatException e) {
					error = true;
					field.setText("");
				}
			}

			if (error) {
				JOptionPane.showMessageDialog(new JFrame(), "ERROR: Input is invalid. Invalid inputs have been cleared.\nPossible reasons include non-integer input, or input not in range of experiment.", "Input Error", JOptionPane.ERROR_MESSAGE);
				error = false;
			}
			else {
				break;
			}
		}

		ArrayList<Integer> numPeopleList = input.get(0);
		ArrayList<Integer> minFriendsList = input.get(1);
		ArrayList<Integer> maxFriendsList = input.get(2);
		ArrayList<Integer> hubNumberList = input.get(3);
		ArrayList<Integer> getWellDaysList = input.get(4);
		ArrayList<Integer> percentSickList = input.get(5);
		ArrayList<Integer> initiallySickList = input.get(6);
		ArrayList<Integer> initiallyVaccList = input.get(7);
		ArrayList<Integer> getVaccList = input.get(8);
		ArrayList<Integer> discoveryList = input.get(9);
		ArrayList<Integer> newGetWellDaysList = input.get(10);
		ArrayList<Integer> curfewDaysList = input.get(11);
		ArrayList<Integer> percentTeensList = input.get(12);
		ArrayList<Integer> percentCurfewList = input.get(13);

		ArrayList<ArrayList<Integer>> data = MoreMethods.getAllData(fileName);
		ArrayList<Integer> best = null;
		int index = data.get(0).size() - 3;

		for (ArrayList<Integer> list : data) {
			if (numPeopleList.contains(list.get(0)) && minFriendsList.contains(list.get(1)) && maxFriendsList.contains(list.get(2)) && hubNumberList.contains(list.get(3)) && getWellDaysList.contains(list.get(4)) && percentSickList.contains(list.get(9)) && initiallySickList.contains(list.get(7)) && initiallyVaccList.contains(list.get(8)) && getVaccList.contains(list.get(10)) && discoveryList.contains(list.get(5)) && newGetWellDaysList.contains(list.get(6)) && curfewDaysList.contains(list.get(11)) && percentTeensList.contains(list.get(12)) && percentCurfewList.contains(list.get(13))) {
				if (best == null) {
					best = list;
				}
				else {
					if (dependent.equals("Days")) {
						index = list.size() - 3;
					}
					else if (dependent.equals("Cost")) {
						index = list.size() - 2;
					}
					else if (dependent.equals("Total Sick")) {
						index = list.size() - 1;
					}

					if (best.get(index) > list.get(index)) {
						best = list;
					}
				}
			}
		}

		return best;
	}


	@SuppressWarnings("unchecked")
	public static ArrayList<Integer> analyze (ArrayList<Object> inputs) throws BiffException, IOException {
		int numPeople = Integer.parseInt(inputs.get(0).toString());
		int numPeopleMax = Integer.parseInt(inputs.get(2).toString());
		int numPeopleStep = Integer.parseInt(inputs.get(1).toString());

		int minFriends = Integer.parseInt(inputs.get(3).toString());
		int minFriendsMax = Integer.parseInt(inputs.get(5).toString());
		int minFriendsStep = Integer.parseInt(inputs.get(4).toString());

		int maxFriends = Integer.parseInt(inputs.get(6).toString());
		int maxFriendsMax = Integer.parseInt(inputs.get(8).toString());
		int maxFriendsStep = Integer.parseInt(inputs.get(7).toString());

		int hubNumber = Integer.parseInt(inputs.get(9).toString());
		int hubNumberMax = Integer.parseInt(inputs.get(11).toString());
		int hubNumberStep = Integer.parseInt(inputs.get(10).toString());

		int getWellDays = Integer.parseInt(inputs.get(12).toString());
		int getWellDaysMax = Integer.parseInt(inputs.get(14).toString());
		int getWellDaysStep = Integer.parseInt(inputs.get(13).toString());

		int discovery = Integer.parseInt(inputs.get(27).toString());
		int discoveryMax = Integer.parseInt(inputs.get(29).toString());
		int discoveryStep = Integer.parseInt(inputs.get(28).toString());

		int newGetWellDays = Integer.parseInt(inputs.get(30).toString());
		int newGetWellDaysMax = Integer.parseInt(inputs.get(32).toString());
		int newGetWellDaysStep = Integer.parseInt(inputs.get(31).toString());

		int initiallySick = Integer.parseInt(inputs.get(18).toString());
		int initiallySickMax = Integer.parseInt(inputs.get(20).toString());
		int initiallySickStep = Integer.parseInt(inputs.get(19).toString());

		int initiallyVacc = Integer.parseInt(inputs.get(21).toString());
		int initiallyVaccMax = Integer.parseInt(inputs.get(23).toString());
		int initiallyVaccStep = Integer.parseInt(inputs.get(22).toString());

		int percentSick = Integer.parseInt(inputs.get(15).toString());
		int percentSickMax = Integer.parseInt(inputs.get(17).toString());
		int percentSickStep = Integer.parseInt(inputs.get(16).toString());

		int getVac = Integer.parseInt(inputs.get(24).toString());
		int getVacMax = Integer.parseInt(inputs.get(26).toString());
		int getVacStep = Integer.parseInt(inputs.get(25).toString());

		int curfewDays = Integer.parseInt(inputs.get(33).toString());
		int curfewDaysMax = Integer.parseInt(inputs.get(35).toString());
		int curfewDaysStep = Integer.parseInt(inputs.get(34).toString());

		int percentTeens = Integer.parseInt(inputs.get(36).toString());
		int percentTeensMax = Integer.parseInt(inputs.get(38).toString());
		int percentTeensStep = Integer.parseInt(inputs.get(37).toString());

		int percentCurfew = Integer.parseInt(inputs.get(39).toString());
		int percentCurfewMax = Integer.parseInt(inputs.get(41).toString());
		int percentCurfewStep = Integer.parseInt(inputs.get(40).toString());

		String fileName = inputs.get(42).toString() + ".xls";

		ArrayList<Integer> numPeopleList = MoreMethods.arrayListFromTo(numPeople, numPeopleStep, numPeopleMax);
		//numPeopleList.add(100);
		ArrayList<Integer> minFriendsList = MoreMethods.arrayListFromTo(minFriends, minFriendsStep, minFriendsMax);
		//minFriendsList.add(2);
		ArrayList<Integer> maxFriendsList = MoreMethods.arrayListFromTo(maxFriends, maxFriendsStep, maxFriendsMax);
		//maxFriendsList.add(2);
		ArrayList<Integer> hubNumberList = MoreMethods.arrayListFromTo(hubNumber, hubNumberStep, hubNumberMax);
		ArrayList<Integer> getWellDaysList = MoreMethods.arrayListFromTo(getWellDays, getWellDaysStep, getWellDaysMax);
		ArrayList<Integer> percentSickList = MoreMethods.arrayListFromTo(percentSick, percentSickStep, percentSickMax);
		ArrayList<Integer> initiallySickList = MoreMethods.arrayListFromTo(initiallySick, initiallySickStep, initiallySickMax);
		ArrayList<Integer> initiallyVaccList = MoreMethods.arrayListFromTo(initiallyVacc, initiallyVaccStep, initiallyVaccMax);
		ArrayList<Integer> getVaccList = MoreMethods.arrayListFromTo(getVac, getVacStep, getVacMax);
		ArrayList<Integer> discoveryList = MoreMethods.arrayListFromTo(discovery, discoveryStep, discoveryMax);
		ArrayList<Integer> newGetWellDaysList = MoreMethods.arrayListFromTo(newGetWellDays, newGetWellDaysStep, newGetWellDaysMax);
		ArrayList<Integer> curfewDaysList = MoreMethods.arrayListFromTo(curfewDays, curfewDaysStep, curfewDaysMax);
		ArrayList<Integer> percentTeensList = MoreMethods.arrayListFromTo(percentTeens, percentTeensStep, percentTeensMax);
		ArrayList<Integer> percentCurfewList = MoreMethods.arrayListFromTo(percentCurfew, percentCurfewStep, percentCurfewMax);
		String dependent = "Days";

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		int padding = 15;

		JTextField numPeopleAnswer = new JTextField(Integer.toString(numPeople), 20);
		JTextField minFriendsAnswer = new JTextField(Integer.toString(minFriends), 5);
		JTextField maxFriendsAnswer = new JTextField(Integer.toString(maxFriends), 5);
		JTextField hubNumberAnswer = new JTextField(Integer.toString(hubNumber), 5);
		JTextField getWellDaysAnswer = new JTextField(Integer.toString(getWellDays), 5);
		JTextField percentSickAnswer = new JTextField(Integer.toString(percentSick), 5);
		JTextField initiallySickAnswer = new JTextField(Integer.toString(initiallySick), 5);
		JTextField initiallyVaccAnswer = new JTextField(Integer.toString(initiallyVacc), 5);
		JTextField getVaccAnswer = new JTextField(Integer.toString(getVac), 5);
		JTextField discoveryAnswer = new JTextField(Integer.toString(discovery), 5);
		JTextField newGetWellDaysAnswer = new JTextField(Integer.toString(newGetWellDays), 5);
		JTextField curfewDaysAnswer = new JTextField(Integer.toString(curfewDays), 5);
		JTextField percentTeensAnswer = new JTextField(Integer.toString(percentTeens), 5);
		JTextField percentCurfewAnswer = new JTextField(Integer.toString(percentCurfew), 5);
		JTextField fileNameAnswer = new JTextField("results", 5);

		JTextField[] fields = {numPeopleAnswer, minFriendsAnswer, maxFriendsAnswer, hubNumberAnswer, getWellDaysAnswer, percentSickAnswer, initiallySickAnswer, initiallyVaccAnswer, getVaccAnswer, discoveryAnswer, newGetWellDaysAnswer, curfewDaysAnswer, percentTeensAnswer, percentCurfewAnswer};
		ArrayList<?>[] lists = {numPeopleList, minFriendsList, maxFriendsList, hubNumberList, getWellDaysList, percentSickList, initiallySickList, initiallyVaccList, getVaccList, discoveryList, newGetWellDaysList, curfewDaysList, percentTeensList, percentCurfewList};

		c.ipady = padding;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		panel.add(new JLabel("PEOPLE SETUP:"), c);
		c.gridx = 1;
		c.gridwidth = 4;
		panel.add(new JLabel(" ------------------------------"), c);
		c.gridwidth = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("numPeople:"), c);
		c.gridx = 1;
		panel.add(numPeopleAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("minFriends:"), c);
		c.gridx = 1;
		panel.add(minFriendsAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("maxFriends:   "), c);
		c.gridx = 1;
		panel.add(maxFriendsAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("hubNumber:"), c);
		c.gridx = 1;
		panel.add(hubNumberAnswer, c);

		c.ipady = padding;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("TRANSMISSION SETUP:"), c);
		c.gridx = 1;
		c.gridwidth = 4;
		panel.add(new JLabel(" ------------------------------"), c);
		c.gridwidth = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("getWellDays:"), c);
		c.gridx = 1;
		panel.add(getWellDaysAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("percentSick:"), c);
		c.gridx = 1;
		panel.add(percentSickAnswer, c);

		c.ipady = padding;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("INITIAL CONDITIONS SETUP:"), c);
		c.gridx = 1;
		c.gridwidth = 4;
		panel.add(new JLabel(" ------------------------------"), c);
		c.gridwidth = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("intiallySick:"), c);
		c.gridx = 1;
		panel.add(initiallySickAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("intiallyVacc:"), c);
		c.gridx = 1;
		panel.add(initiallyVaccAnswer, c);

		c.ipady = padding;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("RUNNING TO HOSPITAL SETUP:"), c);
		c.gridx = 1;
		c.gridwidth = 4;
		panel.add(new JLabel(" ------------------------------"), c);
		c.gridwidth = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("getVacc:"), c);
		c.gridx = 1;
		panel.add(getVaccAnswer, c);

		c.ipady = padding;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("CURE SETUP:"), c);
		c.gridx = 1;
		c.gridwidth = 4;
		panel.add(new JLabel(" ------------------------------"), c);
		c.gridwidth = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("discovery:"), c);
		c.gridx = 1;
		panel.add(discoveryAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("newGetWellDays:"), c);
		c.gridx = 1;
		panel.add(newGetWellDaysAnswer, c);

		c.ipady = padding;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("TEENAGERS SETUP:"), c);
		c.gridx = 1;
		c.gridwidth = 4;
		panel.add(new JLabel(" ------------------------------"), c);
		c.gridwidth = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("curfewDays:"), c);
		c.gridx = 1;
		panel.add(curfewDaysAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("percentTeens:"), c);
		c.gridx = 1;
		panel.add(percentTeensAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("percentCurfew:"), c);
		c.gridx = 1;
		panel.add(percentCurfewAnswer, c);

		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("Minimize:"), c);
		c.gridx = 1;
		String[] possibilities = {"Days", "Cost", "Total Sick"};
		JComboBox minimize = new JComboBox(possibilities);
		panel.add(minimize, c);

		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("fileName (.xls added automatically):   "), c);
		c.gridx = 1;
		fileNameAnswer.setText(fileName.replace(".xls", ""));
		panel.add(fileNameAnswer, c);

		JPanel header = new JPanel (new GridBagLayout());
		c = new GridBagConstraints();
		c.ipady = 5;
		c.gridx = 0;
		c.gridy = 0;
		header.add(new JLabel("Enter acceptable values for each (leave blank for all values):"), c);

		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		//System.out.println(screenSize.width);

		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Make scroll bars on the panel
		scrollPane.setBounds(0, 0, 500, 500);
		scrollPane.setPreferredSize(new Dimension(515, maxOut((int)(screenSize.height / 1.6), 500))); //515

		JPanel contentPane = new JPanel(new GridBagLayout());
		c = new GridBagConstraints();
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 0;
		contentPane.add(header, c);
		c.gridy = 1;
		contentPane.add(scrollPane, c);

		ArrayList<ArrayList<Integer>> input = new ArrayList<ArrayList<Integer>>();

		File file2;
		boolean error = false;
		while (true) {
			int result = JOptionPane.showConfirmDialog(null, contentPane, "Configuration", JOptionPane.OK_CANCEL_OPTION);
			dependent = (String)minimize.getSelectedItem();
			fileName = fileNameAnswer.getText();
			fileNameAnswer.setText(fileName);
			fileName += ".xls";
			file2 = new File(fileName);
			if (!file2.exists()) {
				error = true;
				fileNameAnswer.setText("");
			}

			if (result != JOptionPane.OK_OPTION) {
				error = true;
				System.exit(0);
			}

			String text;
			ArrayList<Integer> list = new ArrayList<Integer>();

			int j = 0;
			for (JTextField field : fields) {
				text = field.getText();
				field.setText(text);
				try {
					list = MoreMethods.commaListToArrayList(text);
					if (list == null) {
						input.add((ArrayList<Integer>)lists[j]);
					}
					else {
						/*
	for (int item : list) {
	if (!((ArrayList<Integer>)lists[j]).contains(item)) {
	throw new NumberFormatException();
	}
	}
						 */
						//System.out.println(list);
						input.add(list);
					}
				}
				catch (NumberFormatException e) {
					error = true;
					field.setText("");
				}
				j++;
			}

			if (error) {
				JOptionPane.showMessageDialog(new JFrame(), "ERROR: Input is invalid. Invalid inputs have been cleared.\nPossible reasons include non-integer input, or input not in range of experiment.", "Input Error", JOptionPane.ERROR_MESSAGE);
				inputs.clear();
				error = false;
			}
			else {
				break;
			}
		}

		numPeopleList = input.get(0);
		minFriendsList = input.get(1);
		maxFriendsList = input.get(2);
		hubNumberList = input.get(3);
		getWellDaysList = input.get(4);
		percentSickList = input.get(5);
		initiallySickList = input.get(6);
		initiallyVaccList = input.get(7);
		getVaccList = input.get(8);
		discoveryList = input.get(9);
		newGetWellDaysList = input.get(10);
		curfewDaysList = input.get(11);
		percentTeensList = input.get(12);
		percentCurfewList = input.get(13);

		ArrayList<ArrayList<Integer>> data = MoreMethods.getAllData(fileName);
		ArrayList<Integer> best = null;
		int index = data.get(0).size() - 3;

		for (ArrayList<Integer> list : data) {
			if (numPeopleList.contains(list.get(0)) && minFriendsList.contains(list.get(1)) && maxFriendsList.contains(list.get(2)) && hubNumberList.contains(list.get(3)) && getWellDaysList.contains(list.get(4)) && percentSickList.contains(list.get(9)) && initiallySickList.contains(list.get(7)) && initiallyVaccList.contains(list.get(8)) && getVaccList.contains(list.get(10)) && discoveryList.contains(list.get(5)) && newGetWellDaysList.contains(list.get(6)) && curfewDaysList.contains(list.get(11)) && percentTeensList.contains(list.get(12)) && percentCurfewList.contains(list.get(13))) {
				if (best == null) {
					best = list;
				}
				else {
					if (dependent.equals("Days")) {
						index = list.size() - 3;
					}
					else if (dependent.equals("Cost")) {
						index = list.size() - 2;
					}
					else if (dependent.equals("Total Sick")) {
						index = list.size() - 1;
					}

					if (best.get(index) > list.get(index)) {
						best = list;
					}
				}
			}
		}

		return best;
	}

	public static ArrayList<Object> getInput () {
		ArrayList<Object> inputs = new ArrayList<Object>();

		final JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		int padding = 15;

		final JTextField numPeopleMinAnswer = new JTextField("100", 10);
		final JTextField numPeopleStepAnswer = new JTextField("1", 10);
		final JTextField numPeopleMaxAnswer = new JTextField("100", 10);

		final JTextField minFriendsMinAnswer = new JTextField("2", 5);
		final JTextField minFriendsStepAnswer = new JTextField("1", 5);
		final JTextField minFriendsMaxAnswer = new JTextField("3", 5);

		final JTextField maxFriendsMinAnswer = new JTextField("2", 5);
		final JTextField maxFriendsStepAnswer = new JTextField("1", 5);
		final JTextField maxFriendsMaxAnswer = new JTextField("2", 5);

		final JTextField hubNumberMinAnswer = new JTextField("0", 5);
		final JTextField hubNumberStepAnswer = new JTextField("1", 5);
		final JTextField hubNumberMaxAnswer = new JTextField("0", 5);

		JTextField getWellDaysMinAnswer = new JTextField("3", 5);
		JTextField getWellDaysStepAnswer = new JTextField("1", 5);
		JTextField getWellDaysMaxAnswer = new JTextField("3", 5);

		JTextField percentSickMinAnswer = new JTextField("5", 5);
		JTextField percentSickStepAnswer = new JTextField("1", 5);
		JTextField percentSickMaxAnswer = new JTextField("40", 5);

		JTextField initiallySickMinAnswer = new JTextField("1", 5);
		JTextField initiallySickStepAnswer = new JTextField("1", 5);
		JTextField initiallySickMaxAnswer = new JTextField("1", 5);

		JTextField initiallyVaccMinAnswer = new JTextField("0", 5);
		JTextField initiallyVaccStepAnswer = new JTextField("5", 5);
		JTextField initiallyVaccMaxAnswer = new JTextField("95", 5);

		JTextField getVaccMinAnswer = new JTextField("0", 5);
		JTextField getVaccStepAnswer = new JTextField("1", 5);
		JTextField getVaccMaxAnswer = new JTextField("50", 5);

		JTextField discoveryMinAnswer = new JTextField("1000000", 5);
		JTextField discoveryStepAnswer = new JTextField("1", 5);
		JTextField discoveryMaxAnswer = new JTextField("1000000", 5);

		JTextField newGetWellDaysMinAnswer = new JTextField("10", 5);
		JTextField newGetWellDaysStepAnswer = new JTextField("1", 5);
		JTextField newGetWellDaysMaxAnswer = new JTextField("10", 5);

		JTextField curfewDaysMinAnswer = new JTextField("5", 5);
		JTextField curfewDaysStepAnswer = new JTextField("1", 5);
		JTextField curfewDaysMaxAnswer = new JTextField("5", 5);

		JTextField percentTeensMinAnswer = new JTextField("10", 5);
		JTextField percentTeensStepAnswer = new JTextField("2", 5);
		JTextField percentTeensMaxAnswer = new JTextField("10", 5);

		JTextField percentCurfewMinAnswer = new JTextField("30", 5);
		JTextField percentCurfewStepAnswer = new JTextField("1", 5);
		JTextField percentCurfewMaxAnswer = new JTextField("30", 5);

		JTextField runTimesAnswer = new JTextField("100", 5);

		JPanel contentPane = new JPanel(new GridBagLayout());
		final JTextField[] fields = {numPeopleMinAnswer, numPeopleStepAnswer, numPeopleMaxAnswer, minFriendsMinAnswer, minFriendsStepAnswer, minFriendsMaxAnswer, maxFriendsMinAnswer, maxFriendsStepAnswer, maxFriendsMaxAnswer, hubNumberMinAnswer, hubNumberStepAnswer, hubNumberMaxAnswer, getWellDaysMinAnswer, getWellDaysStepAnswer, getWellDaysMaxAnswer, percentSickMinAnswer, percentSickStepAnswer, percentSickMaxAnswer, initiallySickMinAnswer, initiallySickStepAnswer, initiallySickMaxAnswer, initiallyVaccMinAnswer, initiallyVaccStepAnswer, initiallyVaccMaxAnswer, getVaccMinAnswer, getVaccStepAnswer, getVaccMaxAnswer, discoveryMinAnswer, discoveryStepAnswer, discoveryMaxAnswer, newGetWellDaysMinAnswer, newGetWellDaysStepAnswer, newGetWellDaysMaxAnswer, curfewDaysMinAnswer, curfewDaysStepAnswer, curfewDaysMaxAnswer, percentTeensMinAnswer, percentTeensStepAnswer, percentTeensMaxAnswer, percentCurfewMinAnswer, percentCurfewStepAnswer, percentCurfewMaxAnswer};

		String[] possibilitiesNetwork = {"Small World", "Random", "Scale-Free"};
		final JComboBox comboBoxNetwork = new JComboBox(possibilitiesNetwork);

		JButton openButton = new JButton("Open config...");
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				FileFilter filter = new FileNameExtensionFilter("ADG file", new String[] {"adg"});
				fileChooser.addChoosableFileFilter(filter);
				fileChooser.setFileFilter(filter);
				int result = fileChooser.showOpenDialog(new JFrame());
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					//System.out.println(selectedFile.getAbsolutePath());
					BufferedReader reader;
					try {
						reader = new BufferedReader(new FileReader(selectedFile.getAbsolutePath()));
					}
					catch (FileNotFoundException e) {
						return;
					}
					String restoredStr;
					try {
						restoredStr = reader.readLine();
						comboBoxNetwork.setSelectedItem(reader.readLine());
						reader.close();
					}
					catch (IOException e) {
						return;
					}
					//System.out.println(restoredStr);
					ArrayList<Integer> restored = MoreMethods.commaListToArrayList(restoredStr);
					//System.out.println(restored);

					for (int i = 0; i < fields.length; i++) {
						fields[i].setText(Integer.toString(restored.get(i)));
					}
				}
			}
		});

		JButton saveButton = new JButton("Save config...");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				FileFilter filter = new FileNameExtensionFilter("ADG file", new String[] {"adg"});
				fileChooser.addChoosableFileFilter(filter);
				fileChooser.setFileFilter(filter);
				int result = fileChooser.showSaveDialog(new JFrame());
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					String path = selectedFile.getAbsolutePath();
					//System.out.println(path);
					if (!path.substring(path.length() - 4).equals(".adg")) {
						path += ".adg";
					}
					PrintWriter writer;
					try {
						writer = new PrintWriter(path, "UTF-8");
					}
					catch (FileNotFoundException e) {
						return;
					}
					catch (UnsupportedEncodingException e) {
						return;
					}

					String toSave = fields[0].getText();
					for (int i = 1; i < fields.length; i++) {
						toSave += ", " + fields[i].getText();
					}
					toSave += "\n" + comboBoxNetwork.getSelectedItem().toString();
					//System.out.println(toSave);
					writer.print(toSave);
					writer.close();
				}
			}
		});

		final StringStorage filePath = new StringStorage("results");
		final JButton fileButton = new JButton("results");
		fileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFileChooser fileChooser = new JFileChooser();
				//System.out.println(fileButton.getText());
				fileChooser.setCurrentDirectory(new File(fileButton.getText()));
				FileFilter filter = new FileNameExtensionFilter("XLS file", new String[] {"xls"});
				fileChooser.addChoosableFileFilter(filter);
				fileChooser.setFileFilter(filter);
				int result = fileChooser.showSaveDialog(new JFrame());
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					String path = selectedFile.getAbsolutePath();
					//System.out.println(path);
					if (path.substring(path.length() - 4).equals(".xls")) {
						path = path.substring(0, path.length() - 4);
					}

					filePath.set(path);

					while (true) {
						int index = path.indexOf('/');
						//System.out.println(index);
						if (path.length() - index < 15) {
							path = "..." + path.substring(index);
							break;
						}
						else if (index == -1) {
							path = "..." + path.substring(path.length() - 15);
							break;
						}
						else {
							path = path.substring(index + 1);
						}
					}

					fileButton.setText(path);
				}
			}
		});

		final StringStorage graphFilePath = new StringStorage("graph");
		final JButton graphFileButton = new JButton("graph");
		graphFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JFileChooser fileChooser = new JFileChooser();
				//System.out.println(graphFileButton.getText());
				fileChooser.setCurrentDirectory(new File(graphFileButton.getText()));
				FileFilter filter = new FileNameExtensionFilter("PNG file", new String[] {"png"});
				fileChooser.addChoosableFileFilter(filter);
				fileChooser.setFileFilter(filter);
				int result = fileChooser.showSaveDialog(new JFrame());
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					String path = selectedFile.getAbsolutePath();
					//System.out.println(path);
					if (path.substring(path.length() - 4).equals(".png")) {
						path = path.substring(0, path.length() - 4);
					}

					graphFilePath.set(path);

					while (true) {
						int index = path.indexOf('/');
						//System.out.println(index);
						if (path.length() - index < 15) {
							path = "..." + path.substring(index);
							break;
						}
						else if (index == -1) {
							path = "..." + path.substring(path.length() - 15);
							break;
						}
						else {
							path = path.substring(index + 1);
						}
					}

					graphFileButton.setText(path);
				}
			}
		});

		final String[] xVars = {"numPeople", "minFriends", "maxFriends", "hubNumber", "getWellDays", "percentSick", "initiallySick", "initiallyVacc", "getVacc", "discovery", "newGetWellDays", "percentTeens", "curfewDays", "percentCurfew"};
		final JComboBox xAxisChoice = new JComboBox(xVars);
		JPanel costPanel = new JPanel(new GridLayout(1, 2));
		final JCheckBox costBox = new JCheckBox();
		costBox.setSelected(true);
		costPanel.add(new JLabel("Cost: "));
		costPanel.add(costBox);
		JPanel daysPanel = new JPanel(new GridLayout(1, 2));
		final JCheckBox daysBox = new JCheckBox();
		daysBox.setSelected(true);
		daysPanel.add(new JLabel("Days: "));
		daysPanel.add(daysBox);
		JPanel totalSickPanel = new JPanel(new GridLayout(1, 2));
		final JCheckBox totalSickBox = new JCheckBox();
		totalSickBox.setSelected(true);
		totalSickPanel.add(new JLabel("TotalSick: "));
		totalSickPanel.add(totalSickBox);
		final JCheckBox allY = new JCheckBox();
		allY.setSelected(true);
		allY.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (allY.isSelected()) {
					costBox.setSelected(true);
					daysBox.setSelected(true);
					totalSickBox.setSelected(true);
				}
				else {
					costBox.setSelected(false);
					daysBox.setSelected(false);
					totalSickBox.setSelected(false);
				}
			}
		});

		JPanel fileName = new JPanel(new GridLayout(1, 2));
		JTextField fileNameAnswer = new JTextField("results", 5);
		fileName.add(fileNameAnswer);
		fileName.add(new JLabel(".xls"));
		JPanel saveResults = new JPanel(new GridLayout(1, 2));
		final JCheckBox saveResultsBox = new JCheckBox();
		final JCheckBox openResultsBox = new JCheckBox();
		openResultsBox.setEnabled(false);
		saveResultsBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (saveResultsBox.isSelected()) {
					openResultsBox.setEnabled(true);
				}
				else {
					openResultsBox.setEnabled(false);
					openResultsBox.setSelected(false);
				}
			}
		});
		saveResults.add(new JLabel("Save: "));
		saveResults.add(saveResultsBox);
		JPanel openResults = new JPanel(new GridLayout(1, 2));
		openResults.add(new JLabel("Open: "));
		openResults.add(openResultsBox);

		JPanel graphFileName = new JPanel(new GridLayout(1, 2));
		JTextField graphFileNameAnswer = new JTextField("graph", 5);
		graphFileName.add(graphFileNameAnswer);
		graphFileName.add(new JLabel(".png"));
		JPanel saveGraph = new JPanel(new GridLayout(1, 2));
		final JCheckBox saveGraphBox = new JCheckBox();
		final JCheckBox openGraphBox = new JCheckBox();
		saveGraphBox.setSelected(true);
		openGraphBox.setSelected(true);
		saveGraphBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (saveGraphBox.isSelected()) {
					costBox.setEnabled(true);
					daysBox.setEnabled(true);
					totalSickBox.setEnabled(true);
					allY.setEnabled(true);
					openGraphBox.setEnabled(true);
					xAxisChoice.setEnabled(true);
				}
				else {
					costBox.setEnabled(false);
					costBox.setSelected(false);
					daysBox.setEnabled(false);
					daysBox.setSelected(false);
					totalSickBox.setEnabled(false);
					totalSickBox.setSelected(false);
					allY.setEnabled(false);
					allY.setSelected(false);
					openGraphBox.setEnabled(false);
					openGraphBox.setSelected(false);
					xAxisChoice.setEnabled(false);
				}
			}
		});
		saveGraph.add(new JLabel("Save: "));
		saveGraph.add(saveGraphBox);
		JPanel openGraph = new JPanel(new GridLayout(1, 2));
		openGraph.add(new JLabel("Open: "));
		openGraph.add(openGraphBox);

		final JCheckBox drawJung = new JCheckBox();
		drawJung.setSelected(false);
		String[] layoutChoices = {"Circle", "ISOM", "FR"};
		final JComboBox comboBoxLayout = new JComboBox(layoutChoices);
		comboBoxLayout.setEnabled(false);

		JPanel drawJungPanel = new JPanel(new GridLayout(1, 2));
		drawJungPanel.add(drawJung);
		final JLabel layoutLabel = new JLabel("Layout:");
		drawJungPanel.add(layoutLabel);
		drawJung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (drawJung.isSelected()) {
					comboBoxLayout.setEnabled(true);
				}
				else {
					comboBoxLayout.setEnabled(false);
				}
			}
		});
		
		final JLabel townName = new JLabel();
		
		final JButton getTownData = new JButton();
		getTownData.setText("Enter Town");
		

		/*
	JPanel drawJungPanel = new JPanel(new GridLayout(1, 2));
	drawJungPanel.add(drawJung);
	final JLabel delayLabel = new JLabel("Delay: 500");
	drawJungPanel.add(delayLabel);
	final JSlider delay = new JSlider(JSlider.HORIZONTAL, 0, 3000, 500);
	delay.addChangeListener(new ChangeListener() {
	public void stateChanged(ChangeEvent arg0) {
	delayLabel.setText("Delay: " + delay.getValue());
	}
	});
	//Turn on labels at major tick marks.
	delay.setMajorTickSpacing(500);
	delay.setMinorTickSpacing(100);
	delay.setPaintTicks(true);
	delay.setPaintLabels(true);
		 */

		c.ipady = padding;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(new JLabel("PEOPLE SETUP:"), c);
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 4;
		panel.add(separatorJLabel(), c);
		c.gridwidth = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("numPeople:"), c);
		c.gridx = 1;
		panel.add(numPeopleMinAnswer, c);
		c.gridx = 2;
		panel.add(numPeopleStepAnswer, c);
		c.gridx = 3;
		panel.add(numPeopleMaxAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("minFriends:"), c);
		c.gridx = 1;
		panel.add(minFriendsMinAnswer, c);
		c.gridx = 2;
		panel.add(minFriendsStepAnswer, c);
		c.gridx = 3;
		panel.add(minFriendsMaxAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("maxFriends:   "), c);
		c.gridx = 1;
		panel.add(maxFriendsMinAnswer, c);
		c.gridx = 2;
		panel.add(maxFriendsStepAnswer, c);
		c.gridx = 3;
		panel.add(maxFriendsMaxAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("hubNumber:"), c);
		c.gridx = 1;
		panel.add(hubNumberMinAnswer, c);
		c.gridx = 2;
		panel.add(hubNumberStepAnswer, c);
		c.gridx = 3;
		panel.add(hubNumberMaxAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("networkType:"), c);
		c.gridx = 1;
		panel.add(comboBoxNetwork, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("Town Sim:"), c);
		c.gridx = 1;
		panel.add(getTownData, c);
		c.gridx = 2;
		panel.add(townName, c);
		//c.gridx = 3;
		//GetData.createProgressBar(1);
		//panel.add(GetData.progressBar, c);

		c.ipady = padding;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("TRANSMISSION SETUP:"), c);
		c.gridx = 1;
		c.gridwidth = 4;
		panel.add(separatorJLabel(), c);
		c.gridwidth = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("getWellDays:"), c);
		c.gridx = 1;
		panel.add(getWellDaysMinAnswer, c);
		c.gridx = 2;
		panel.add(getWellDaysStepAnswer, c);
		c.gridx = 3;
		panel.add(getWellDaysMaxAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("percentSick:"), c);
		c.gridx = 1;
		panel.add(percentSickMinAnswer, c);
		c.gridx = 2;
		panel.add(percentSickStepAnswer, c);
		c.gridx = 3;
		panel.add(percentSickMaxAnswer, c);

		c.ipady = padding;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("INITIAL CONDITIONS SETUP:"), c);
		c.gridx = 1;
		c.gridwidth = 4;
		panel.add(separatorJLabel(), c);
		c.gridwidth = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("intiallySick:"), c);
		c.gridx = 1;
		panel.add(initiallySickMinAnswer, c);
		c.gridx = 2;
		panel.add(initiallySickStepAnswer, c);
		c.gridx = 3;
		panel.add(initiallySickMaxAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("intiallyVacc:"), c);
		c.gridx = 1;
		panel.add(initiallyVaccMinAnswer, c);
		c.gridx = 2;
		panel.add(initiallyVaccStepAnswer, c);
		c.gridx = 3;
		panel.add(initiallyVaccMaxAnswer, c);

		c.ipady = padding;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("RUNNING TO HOSPITAL SETUP:   "), c);
		c.gridx = 1;
		c.gridwidth = 4;
		panel.add(separatorJLabel(), c);
		c.gridwidth = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("getVacc:"), c);
		c.gridx = 1;
		panel.add(getVaccMinAnswer, c);
		c.gridx = 2;
		panel.add(getVaccStepAnswer, c);
		c.gridx = 3;
		panel.add(getVaccMaxAnswer, c);

		c.ipady = padding;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("CURE SETUP:"), c);
		c.gridx = 1;
		c.gridwidth = 4;
		panel.add(separatorJLabel(), c);
		c.gridwidth = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("discovery:"), c);
		c.gridx = 1;
		panel.add(discoveryMinAnswer, c);
		c.gridx = 2;
		panel.add(discoveryStepAnswer, c);
		c.gridx = 3;
		panel.add(discoveryMaxAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("newGetWellDays:"), c);
		c.gridx = 1;
		panel.add(newGetWellDaysMinAnswer, c);
		c.gridx = 2;
		panel.add(newGetWellDaysStepAnswer, c);
		c.gridx = 3;
		panel.add(newGetWellDaysMaxAnswer, c);

		c.ipady = padding;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("TEENAGERS SETUP:"), c);
		c.gridx = 1;
		c.gridwidth = 4;
		panel.add(separatorJLabel(), c);
		c.gridwidth = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("curfewDays:"), c);
		c.gridx = 1;
		panel.add(curfewDaysMinAnswer, c);
		c.gridx = 2;
		panel.add(curfewDaysStepAnswer, c);
		c.gridx = 3;
		panel.add(curfewDaysMaxAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("percentTeens:"), c);
		c.gridx = 1;
		panel.add(percentTeensMinAnswer, c);
		c.gridx = 2;
		panel.add(percentTeensStepAnswer, c);
		c.gridx = 3;
		panel.add(percentTeensMaxAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("percentCurfew:"), c);
		c.gridx = 1;
		panel.add(percentCurfewMinAnswer, c);
		c.gridx = 2;
		panel.add(percentCurfewStepAnswer, c);
		c.gridx = 3;
		panel.add(percentCurfewMaxAnswer, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("runTimes:"), c);
		c.gridx = 1;
		panel.add(runTimesAnswer, c);

		c.ipady = padding;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel(""), c);

		c.ipady = padding;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("RESULT OUTPUT SETUP:"), c);
		c.gridx = 1;
		c.gridwidth = 4;
		panel.add(separatorJLabel(), c);
		c.gridwidth = 1;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("fileName:"), c);
		c.gridx = 1;
		//panel.add(fileName, c);
		panel.add(fileButton, c);
		c.gridx = 2;
		panel.add(saveResults, c);
		c.gridx = 3;
		panel.add(openResults, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("graphFileName:"), c);
		c.gridx = 1;
		//panel.add(graphFileName, c);
		panel.add(graphFileButton, c);
		c.gridx = 2;
		panel.add(saveGraph, c);
		c.gridx = 3;
		panel.add(openGraph, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("x-axis:"), c);
		c.gridx = 1;
		panel.add(xAxisChoice, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("y-axis:"), c);
		c.gridx = 1;
		//panel.add(yAxisChoice, c);
		panel.add(costPanel, c);
		c.gridx = 2;
		panel.add(daysPanel, c);
		c.gridx = 3;
		panel.add(totalSickPanel, c);
		c.gridx = 4;
		panel.add(allY, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("Run Jung?"), c);
		c.gridx = 1;
		//panel.add(yAxisChoice, c);
		panel.add(drawJungPanel, c);
		c.gridx = 2;
		panel.add(comboBoxLayout, c);
		c.gridx = 0;
		c.gridy += 1;
		panel.add(new JLabel("Restore config:"), c);
		c.gridx = 1;
		panel.add(saveButton, c);
		c.gridx = 2;
		panel.add(openButton, c);

		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

		int[] moveOver = {1, 1, 1, 1, 4, 1, 2, 1, 2, 2, 1, 2, 1, 1};

		final ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();

		c.gridx = 4;
		c.gridy = 1;
		for (int i = 1; i < fields.length; i += 3) {
			final int iF = i;
			final JCheckBox box = new JCheckBox();
			checkBoxes.add(box);
			box.setSelected(true);
			box.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if (!box.isSelected()) {
						for (int j = iF; j < iF + 2; j++) {
							fields[j].setBackground(Color.LIGHT_GRAY);
							fields[j].setEnabled(false);
						}
						fields[iF].setText("1");
						fields[iF + 1].setText(fields[iF - 1].getText());
					}
					else {
						for (int j = iF; j < iF + 2; j++) {
							fields[j].setBackground(Color.WHITE);
							fields[j].setEnabled(true);
						}
						xAxisChoice.setSelectedItem(xVars[iF / 3]);
					}
				}
			});
			//System.out.println(i);
			c.gridy += moveOver[i / 3];
			panel.add(box, c);
		}
		
		getTownData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					if (getTownData.getText().equals("Enter Town")) {
						townName.setText("  Working...");
						boolean worked = GetData.run();
						if (worked) {
							townName.setText("  " + capitalizeEachWord(GetData.locInfo[0].replace("+", " ")) + ", " + GetData.locInfo[1]);
							
							numPeopleMinAnswer.setEnabled(false);
							numPeopleStepAnswer.setEnabled(false);
							numPeopleMaxAnswer.setEnabled(false);
							
							checkBoxes.get(0).setEnabled(false);
							checkBoxes.get(0).setSelected(false);
							
							numPeopleMinAnswer.setBackground(Color.LIGHT_GRAY);
							numPeopleStepAnswer.setBackground(Color.LIGHT_GRAY);
							numPeopleMaxAnswer.setBackground(Color.LIGHT_GRAY);
							
							getTownData.setText("Clear");
						}
						else if (!worked && townName.getText().equals("  Working...")) {
							townName.setText("");
						}
					}
					else {
						townName.setText("");
						
						numPeopleMinAnswer.setEnabled(true);
						numPeopleStepAnswer.setEnabled(true);
						numPeopleMaxAnswer.setEnabled(true);
						
						checkBoxes.get(0).setEnabled(true);
						checkBoxes.get(0).setSelected(true);
						
						numPeopleMinAnswer.setBackground(Color.WHITE);
						numPeopleStepAnswer.setBackground(Color.WHITE);
						numPeopleMaxAnswer.setBackground(Color.WHITE);
						
						GetData.clear();
						
						getTownData.setText("Enter Town");
					}
					System.out.println(GetData.hs);
				} catch (MalformedURLException e) {
				} catch (IOException e) {
				}
			}
		});

		final JCheckBox allBox = new JCheckBox();
		allBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (allBox.isSelected()) {
					for (JCheckBox box : checkBoxes) {
						if (getTownData.getText().equals("Clear") && checkBoxes.indexOf(box) == 0) {
							continue;
						}
						box.setSelected(true);
						int i = checkBoxes.indexOf(box) * 3 + 1;
						for (int j = i; j < i + 2; j++) {
							fields[j].setBackground(Color.WHITE);
							fields[j].setEnabled(true);
						}
					}
				}
				else {
					for (JCheckBox box : checkBoxes) {
						box.setSelected(false);
						int i = checkBoxes.indexOf(box) * 3 + 1;
						if (fields[i].isEnabled() == false) {
							continue;
						}
						for (int j = i; j < i + 2; j++) {
							fields[j].setBackground(Color.LIGHT_GRAY);
							fields[j].setEnabled(false);
						}
						fields[i].setText("1");
						fields[i + 1].setText(fields[i - 1].getText());
					}
				}
			}
		});
		allBox.setSelected(true);
		c.gridy = 1;
		panel.add(allBox, c);
		
		String OS = System.getProperty("os.name").toLowerCase();
		
		JPanel header = new JPanel (new GridBagLayout());
		c = new GridBagConstraints();
		c.ipady = 5;
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.CENTER;
		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Make scroll bars on the panel
		if (OS.indexOf("win") >= 0) { // If Windows
			header.add(new JLabel("                                                                         "), c);
			c.gridx = 1;
			header.add(new JLabel("Min                 |"), c);
			c.gridx = 2;
			header.add(new JLabel("             Step               |"), c);
			c.gridx = 3;
			header.add(new JLabel("                Max               "), c);
	
			c.gridy = 0;
			c.gridx = 4;
			header.add(allBox, c);
	
			scrollPane.setBounds(0, 0, 500, 500);
			scrollPane.setPreferredSize(new Dimension(590, maxOut((int)(screenSize.height / 1.6), 500))); //640
			//690
		}
		else { // If Mac or other
			header.add(new JLabel("                                                                  "), c);
			c.gridx = 1;
			header.add(new JLabel("Min               |"), c);
			c.gridx = 2;
			header.add(new JLabel("           Step              |"), c);
			c.gridx = 3;
			header.add(new JLabel("            Max               "), c);
	
			c.gridy = 0;
			c.gridx = 4;
			header.add(allBox, c);
	
			scrollPane.setBounds(0, 0, 500, 500);
			scrollPane.setPreferredSize(new Dimension(690, maxOut((int)(screenSize.height / 1.6), 500))); //640
			//690
		}
		
		JButton feedback = new JButton("Feedback");
		feedback.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Feedback.run();
			}
		});
		JLabel copyrightLabel = new JLabel("© ADG Research 2014-2015");
		
		c = new GridBagConstraints();
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 0;
		contentPane.add(header, c);
		c.gridy = 1;
		contentPane.add(scrollPane, c);
		c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		contentPane.add(feedback, c);
		c.anchor = GridBagConstraints.EAST;
		contentPane.add(copyrightLabel, c);

		// Restore previous configuration from previousConfig.adg
		try {
			BufferedReader reader = new BufferedReader(new FileReader("previousConfig.adg"));

			try {
				String restoredStr = reader.readLine();
				comboBoxNetwork.setSelectedItem(reader.readLine());
				reader.close();

				ArrayList<Integer> restored = MoreMethods.commaListToArrayList(restoredStr);

				for (int i = 0; i < fields.length; i++) {
					fields[i].setText(Integer.toString(restored.get(i)));
				}
			}
			catch (IOException e) {
				// Do nothing
			}

		}
		catch (FileNotFoundException e) {
			// Do Nothing
		}


		boolean error = false;
		while (true) {
			int result = JOptionPane.showConfirmDialog(null, contentPane, "Configuration", JOptionPane.OK_CANCEL_OPTION);

			if (result != JOptionPane.OK_OPTION) {
				System.exit(0);
			}

			String text;
			int integer;

			for (int i = 0; i < fields.length; i++) {
				JTextField field = fields[i];
				text = field.getText();
				field.setText(text);
				try {
					if (!field.isEnabled() && (i + 2) % 3 == 0) {
						integer = 1;
					}
					else if (!field.isEnabled() && (i + 1) % 3 == 0) {
						integer = Integer.parseInt(fields[i - 2].getText());
						if (integer < 0) {
							throw new NumberFormatException();
						}
					}
					else {
						integer = Integer.parseInt(text);
						if (integer < 0) {
							throw new NumberFormatException();
						}
					}
					inputs.add(integer);
				}
				catch (NumberFormatException e) {
					error = true;
					field.setText("");
				}
			}

			int runTimes = 100;
			try {
				String runTimesStr = runTimesAnswer.getText();

				runTimes = Integer.parseInt(runTimesStr);

				if (runTimes < 1) {
					throw new NumberFormatException();
				}
			}
			catch (NumberFormatException e) {
				error = true;
				runTimesAnswer.setText("");
			}

			inputs.add(filePath.get());
			//inputs.add(fileNameAnswer.getText());
			inputs.add(graphFilePath.get());
			//inputs.add(graphFileNameAnswer.getText());
			inputs.add(xAxisChoice.getSelectedItem());
			inputs.add(costBox.isSelected());
			inputs.add(daysBox.isSelected());
			inputs.add(totalSickBox.isSelected());
			inputs.add(saveResultsBox.isSelected());
			inputs.add(openResultsBox.isSelected());
			inputs.add(saveGraphBox.isSelected());
			inputs.add(openGraphBox.isSelected());
			inputs.add(comboBoxNetwork.getSelectedItem());
			inputs.add(drawJung.isSelected());
			inputs.add(comboBoxLayout.getSelectedItem());
			inputs.add(runTimes);
			inputs.add(GetData.hs);
			inputs.add(GetData.ha);
			inputs.add(GetData.ages);

			if (error) {
				JOptionPane.showMessageDialog(new JFrame(), "ERROR: Input is invalid. Invalid inputs have been cleared.", "Input Error", JOptionPane.ERROR_MESSAGE);
				inputs.clear();
				error = false;
			}
			else {
				// Save inputs into previousConfig.adg file
				try {
					PrintWriter writer = new PrintWriter("previousConfig.adg", "UTF-8");

					String toSave = Integer.toString((Integer)inputs.get(0));
					for (int i = 1; i < fields.length; i++) {
						toSave += ", " + Integer.toString((Integer)inputs.get(i));
					}
					toSave += "\n" + comboBoxNetwork.getSelectedItem().toString();
					writer.print(toSave);
					writer.close();
				}
				catch (FileNotFoundException e) {
					// Do nothing
				}
				catch (UnsupportedEncodingException e) {
					// Do nothing
				}

				return inputs;
			}
		}
	}

	private static JLabel separatorJLabel () {
		JLabel label = new JLabel(" ----------------------------------------------------");
		//label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		return label;
	}

	private static int maxOut (int largeNum, int max) {
		if (largeNum > max) {
			return max;
		}
		return largeNum;
	}
	
	private static String capitalizeEachWord (String source) {
		StringBuffer res = new StringBuffer();
		String[] strArr = source.split(" ");
	    for (String str : strArr) {
	        char[] stringArray = str.trim().toCharArray();
	        stringArray[0] = Character.toUpperCase(stringArray[0]);
	        str = new String(stringArray);

	        res.append(str).append(" ");
	    }
	    
	    return res.toString().trim();
	}

	/**
	 * OUTPUT METHODS
	 */

	public static void displayMessage (String message) { // Displays message to user
		JTextArea outputMessage = new JTextArea(message);
		outputMessage.setLineWrap(true);
		outputMessage.setWrapStyleWord(true);
		outputMessage.setEditable(false);
		outputMessage.setSize(500, 500);
		outputMessage.setRows(8);
		JScrollPane scrollPane = new JScrollPane(outputMessage, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JOptionPane.showMessageDialog(null, scrollPane);
	}
}