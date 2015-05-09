package moremethods;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GetData {
	private static String agesVars;
	private static String householdSizeVars;
	private static String householdAgeVars;
	
	private static JFrame progressFrame;
	public static JProgressBar progressBar;
	
	public static int[] hs;
	public static double[] ha;
	public static double[] ages;
	
	public static String[] locInfo;

	public static boolean run () throws MalformedURLException, IOException {
		ArrayList<String> zips = getInput();
		if (zips == null) {
			clear();
			return false;
		}
		int numZips = zips.size();
		createProgressBar(numZips);
		generateVars();
		System.out.println(zips);

		ArrayList<Integer[]> hsAll = new ArrayList<Integer[]>();
		ArrayList<Double[]> haAll = new ArrayList<Double[]>();
		ArrayList<Double[]> agesAll = new ArrayList<Double[]>();
		
		String zip = null;
		for (int i = 0; i < numZips; i++) {
			zip = zips.get(i);
			setProgressBar(i);
			
			String[] householdSizeList = getVars(householdSizeVars, zip);
			if (householdSizeList == null) {
				continue;
			}
			int[] hsInt = strToIntArray(householdSizeList);
			Integer[] hs = fixHouseholdSize(hsInt);
			hsAll.add(hs);

			String[] householdAgeList = getVars(householdAgeVars, zip);
			int[] haInt = strToIntArray(householdAgeList);
			Double[] ha = fixHouseholdAge(haInt);
			haAll.add(ha);

			String[] agesList = getVars(agesVars, zip);
			int[] agesInt = strToIntArray(agesList);
			Double[] ages = fixAges(agesInt);
			agesAll.add(ages);
		}
		
		setProgressBar(numZips);
		//System.out.println(hsAll);
		//System.out.println(haAll);
		//System.out.println(agesAll);
		
		hs = sumInt(hsAll);
		ha = sumDouble(haAll);
		ages = sumDouble(agesAll);
		
		printArray(hs);
		printArray(ha);
		printArray(ages);
		
		closeProgressBar();
		
		return true;
	}
	
	// SUM UP ALL ARRAYS IN ARRAYLIST ---------------------------------
	public static int[] sumInt (ArrayList<Integer[]> list) {
		int len = list.get(0).length;
		
		int[] sum = new int[len];
		
		for (Integer[] array : list) {
			for (int i = 0; i < len; i++) {
				sum[i] += array[i];
			}
		}
		
		return sum;
	}
	
	public static double[] sumDouble (ArrayList<Double[]> list) {
		int len = list.get(0).length;
		
		double[] sum = new double[len];
		
		for (Double[] array : list) {
			for (int i = 0; i < len; i++) {
				sum[i] += array[i];
			}
		}
		
		for (int i = 0; i < len; i++) {
			sum[i] = Math.round(10.0 * sum[i]) / 10.0;
		}
		
		return sum;
	}
	
	public static void clear () {
		hs = null;
		ha = null;
		ages = null;
	}

	// FIX ARRAYS TO MATCH WANTED -------------------------------
	public static Double[] fixHouseholdAge (int[] ha) {
		int[] newHa = new int[8];

		newHa[0] = ha[2];
		newHa[1] = ha[3];
		newHa[2] = ha[4];
		newHa[3] = ha[5];
		newHa[4] = ha[6] + ha[7];
		newHa[5] = ha[8];
		newHa[6] = ha[9];
		newHa[7] = ha[10];

		Double[] finalHa = new Double[8];
		int total = ha[1];
		for (int i = 0; i < newHa.length; i++) {
			finalHa[i] = Math.round(10.0 * (100.0 * newHa[i]) / total) / 10.0;
		}

		return finalHa;
	}

	public static Integer[] fixHouseholdSize (int[] hs) {
		Integer[] newHs = new Integer[7];

		for (int i = 0; i < 7; i++) {
			newHs[i] = hs[i];
		}

		return newHs;
	}

	public static Double[] fixAges (int[] ages) {
		int[] newAges = new int[18];
		newAges[0] = ages[1] + ages[25];
		newAges[1] = ages[2] + ages[26];
		newAges[2] = ages[3] + ages[27];
		newAges[3] = ages[4] + ages[5] + ages[28] + ages[29];
		newAges[4] = ages[6] + ages[7] + ages[8] + ages[30] + ages[31] + ages[32];
		newAges[5] = ages[9] + ages[33];
		newAges[6] = ages[10] + ages[34];
		newAges[7] = ages[11] + ages[35];
		newAges[8] = ages[12] + ages[36];
		newAges[9] = ages[13] + ages[37];
		newAges[10] = ages[14] + ages[38];
		newAges[11] = ages[15] + ages[39];
		newAges[12] = ages[16] + ages[17] + ages[40] + ages[41];
		newAges[13] = ages[18] + ages[19] + ages[42] + ages[43];
		newAges[14] = ages[20] + ages[44];
		newAges[15] = ages[21] + ages[45];
		newAges[16] = ages[22] + ages[46];
		newAges[17] = ages[23] + ages[47];

		Double[] finalAges = new Double[18];
		int total = ages[0];
		for (int i = 0; i < newAges.length; i++) {
			finalAges[i] = Math.round(10.0 * (100.0 * newAges[i]) / total) / 10.0;
		}

		return finalAges;
	}

	public static String[] getVars (String vars, String zipCode) throws MalformedURLException, IOException {
		int stateNum = getState(zipCode);
		String URL = "http://api.census.gov/data/2010/sf1?get=" + vars + "&for=zip+code+tabulation+area:" + zipCode + "&in=state:" + stateNum + "&key=b7b88c5008e4a630c83569b46c3b89bb576e4f63";
		InputStream is = new URL(URL).openStream();

		String online = "";
		while (online.equals("")) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = reader.readLine();
			while (line != null) {
				online += line + "\n";
				line = reader.readLine();
			}

			if (online.equals("") || stateNum == 0) {
				reader.close();
				return null;
			}
		}

		online = online.substring(online.indexOf("]") + 4, online.length() - 3);
		//System.out.println(online);
		online = online.replaceAll("\"", "");
		String[] list = online.split(",");

		return list;
	}

	public static ArrayList<String> getInput () throws MalformedURLException, IOException {
		GridBagLayout layout = new GridBagLayout();
		JPanel panel = new JPanel(layout);
		GridBagConstraints c = new GridBagConstraints();
		layout.setConstraints(panel, c);

		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 20;
		
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(new JLabel("Location (City/Town, State Abbr.):   "), c);
		c.ipady = 3;
		c.gridy = 1;
		c.gridx = 0;
		JTextField loc = new JTextField(10);
		loc.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		panel.add(loc, c);

		String locInput = "";
		ArrayList<String> zips = new ArrayList<String>();
		boolean again = true;
		while (again) {
			again = false;
			int result = JOptionPane.showConfirmDialog(null, panel, "Input", JOptionPane.OK_CANCEL_OPTION);
			if (result != JOptionPane.OK_OPTION) {
				return null;
			}

			locInput = loc.getText().trim().replaceAll(", ", ",").replaceAll(" ", "+");

			if (locInput.equals("") || !locInput.contains(",")) {
				loc.setBorder(BorderFactory.createLineBorder(Color.RED));
				again = true;
			}
			else {
				try {
					locInfo = locInput.split(",", 2);
					locInfo[0] = locInfo[0].substring(0, 1).toUpperCase() + locInfo[0].substring(1).toLowerCase();
					locInfo[1] = locInfo[1].toUpperCase();
					printArray(locInfo);

					zips = getZips(locInfo[0], locInfo[1]);
					if (zips == null) {
						throw new NumberFormatException();
					}

					loc.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				}
				catch (NumberFormatException e) {
					loc.setBorder(BorderFactory.createLineBorder(Color.RED));
					again = true;
				}
			}
		}

		return zips;
	}

	public static ArrayList<String> getZips (String town, String state) throws MalformedURLException, IOException {
		String URL = "http://getzips.com/cgi-bin/ziplook.exe?What=2&City=" + town + "&State=" + state + "&Submit=Look+It+Up";

		InputStream is = new URL(URL).openStream();

		String online = "";
		while (online.equals("")) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = reader.readLine();
			while (line != null) {
				online += line + "\n";
				line = reader.readLine();
			}
		}

		ArrayList<String> zipsStr = new ArrayList<String>();

		if (online.contains("No matching cities found.") || !online.contains("CITY AND STATE")) {
			return null;
		}

		//System.out.println(online);
		int index = online.indexOf("<TD WIDTH=\"10%\" VALIGN=TOP><P><B>AREA</B></TD></TR>\n<TR><TD WIDTH=\"15%\" VALIGN=TOP><P>");
		online = online.substring(index + 86);
		while (online.length() > 12) {
			zipsStr.add(online.substring(0, 5));
			index = online.indexOf("</TD></TR>\n<TR><TD WIDTH=\"15%\" VALIGN=TOP><P>");
			if (index == -1) {
				break;
			}
			online = online.substring(index + 45);
		}

		removeDuplicate(zipsStr);
		return zipsStr;
	}

	public static int getState (String zipStr) {
		int zip = Integer.parseInt(zipStr.substring(0, 3));
		//System.out.println(zip);
		if ((000 <= zip && 004 >= zip) || (006 <= zip && 9 >= zip) || (90 <= zip && 99 >= zip) || 213 == zip || 269 == zip || 340 == zip || 343 == zip || 345 == zip || 348 == zip || 353 == zip || 419 == zip || 428 == zip || 429 == zip || (517 <= zip && 519 >= zip) || 529 == zip || 533 == zip || 536 == zip || 552 == zip || 568 == zip || 578 == zip || 579 == zip || 589 == zip || 621 == zip || 632 == zip || 642 == zip || 643 == zip || 659 == zip || 663 == zip || 682 == zip || (694 <= zip && 699 >= zip) || 702 == zip || 709 == zip || 715 == zip || 732 == zip || 742 == zip || 771 == zip || (817 <= zip && 819 >= zip) || 839 == zip || 848 == zip || 849 == zip || 854 == zip || 858 == zip || 861 == zip || 862 == zip || (866 <= zip && 869 >= zip) || 876 == zip || (886 <= zip && 888 >= zip) || 892 == zip || 896 == zip || 899 == zip || 909 == zip || 929 == zip || (962 <= zip && 966 >= zip) || 987 == zip) { // Invalid
			return 0;
		}
		else if (005 == zip || (100 <= zip && 149 >= zip)) { //NY
			return 36;
		}
		else if ((10 <= zip && 27 >= zip) || zip == 55) { //MA
			return 25;
		}
		else if (28 <= zip && 29 >= zip) { //RI
			return 44;
		}
		else if (30 <= zip && 38 >= zip) { //NH
			return 33;
		}
		else if (39 <= zip && 49 >= zip) { //ME
			return 23;
		}
		else if ((50 <= zip && 54 >= zip) || (56 <= zip && 59 >= zip)) { //VT
			return 50;
		}
		else if (60 <= zip && 69 >= zip) { //CT
			return 9;
		}
		else if (70 <= zip && 89 >= zip) { //NJ
			return 34;
		}
		else if (150 <= zip && 196 >= zip) { //PA
			return 42;
		}
		else if (197 <= zip && 199 >= zip) { //DE
			return 10;
		}
		else if (200 == zip || (202 <= zip && 205 >= zip) || zip == 569) { //DC
			return 11;
		}
		else if (201 == zip || (220 <= zip && 246 >= zip)) { //VA
			return 51;
		}
		else if ((206 <= zip && 212 >= zip) || (214 <= zip && 219 >= zip)) { //MD
			return 24;
		}
		else if (250 <= zip && 268 >= zip) { //WV
			return 54;
		}
		else if (270 <= zip && 289 >= zip) { //NC
			return 37;
		}
		else if (290 <= zip && 299 >= zip) { //SC
			return 45;
		}
		else if ((300 <= zip && 319 >= zip) || 398 == zip || 399 == zip) { //GA
			return 13;
		}
		else if ((320 <= zip && 339 >= zip) || 341 == zip || 342 == zip || 344 == zip || 346 == zip || 347 == zip || 349 == zip) { //FL
			return 12;
		}
		else if ((350 <= zip && 352 >= zip) || (354 <= zip && 369 >= zip)) { //AL
			return 1;
		}
		else if (370 <= zip && 385 >= zip) { //TN
			return 47;
		}
		else if (386 <= zip && 397 >= zip) { //MS
			return 28;
		}
		else if ((400 <= zip && 418 >= zip) || (420 <= zip && 427 >= zip)) { //KY
			return 21;
		}
		else if (430 <= zip && 459 >= zip) { //OH
			return 39;
		}
		else if (460 <= zip && 479 >= zip) { //IN
			return 18;
		}
		else if (480 <= zip && 499 >= zip) { //MI
			return 26;
		}
		else if ((500 <= zip && 516 >= zip) || (520 <= zip && 528 >= zip)) { //IA
			return 19;
		}
		else if ((530 <= zip && 532 >= zip) || 534 == zip || 535 == zip || (537 <= zip && 549 >= zip)) { //WI
			return 55;
		}
		else if (550 == zip || 551 == zip || (553 <= zip && 567 >= zip)) { //MN
			return 27;
		}
		else if (570 <= zip && 577 >= zip) { //SD
			return 46;
		}
		else if (580 <= zip && 588 >= zip) { //ND
			return 38;
		}
		else if (590 <= zip && 599 >= zip) { //MT
			return 30;
		}
		else if ((600 <= zip && 620 >= zip) || (622 <= zip && 629 >= zip)) { //IL
			return 17;
		}
		else if (630 == zip || 631 == zip || (633 <= zip && 641 >= zip) || (644 <= zip && 658 >= zip)) { //MO
			return 29;
		}
		else if ((660 <= zip && 662 >= zip) || (664 <= zip && 679 >= zip)) { //KS
			return 20;
		}
		else if (680 == zip || 681 == zip || (683 <= zip && 693 >= zip)) { //NE
			return 31;
		}
		else if (700 == zip || 701 == zip || (703 <= zip && 708 >= zip) || (710 <= zip && 714 >= zip)) { //LA
			return 22;
		}
		else if (716 <= zip && 729 >= zip) { //AR
			return 5;
		}
		else if (730 == zip || 731 == zip || (734 <= zip && 741 >= zip) || (743 <= zip && 749 >= zip)) { //OK
			return 40;
		}
		else if (733 == zip || (750 <= zip && 770 >= zip) || (772 <= zip && 799 >= zip) || 885 == zip) { //TX
			return 48;
		}
		else if (800 <= zip && 816 >= zip) { //CO
			return 8;
		}
		else if (820 <= zip && 831 >= zip) { //WY
			return 56;
		}
		else if (832 <= zip && 838 >= zip) { //ID
			return 16;
		}
		else if (840 <= zip && 847 >= zip) { //UT
			return 49;
		}
		else if ((850 <= zip && 853 >= zip) || (855 <= zip && 857 >= zip) || 859 == zip || 860 == zip || (863 <= zip && 865 >= zip)) { //AZ
			return 4;
		}
		else if ((870 <= zip && 875 >= zip) || (877 <= zip && 884 >= zip)) { //NM
			return 35;
		}
		else if ((889 <= zip && 891 >= zip) || (893 <= zip && 895 >= zip) || 897 == zip || 898 == zip) { //NV
			return 32;
		}
		else if ((900 <= zip && 908 >= zip) || (910 <= zip && 919 >= zip) || (921 <= zip && 928 >= zip) || (930 <= zip && 961 >= zip)) { //CA
			return 6;
		}
		else if (967 == zip || 968 == zip) { //HI
			return 15;
		}
		else if (970 <= zip && 979 >= zip) { //OR
			return 41;
		}
		else if ((980 <= zip && 986 >= zip) || (988 <= zip && 994 >= zip)) { //WA
			return 53;
		}
		else if (995 <= zip && 999 >= zip) { //AK
			return 2;
		}
		else {
			return 0;
		}
	}

	// WORKING WITH ARRAYS AND ARRAYLISTS ---------------------------------------------
	public static void printArray (int[] array) {
		if (array == null) {
			return;
		}
		
		for (int i = 0; i < array.length - 1; i++) {
			System.out.print(array[i] + ",");
		}
		System.out.print(array[array.length - 1]);
		System.out.println();
	}
	
	public static void printArray (String[] array) {
		if (array == null) {
			return;
		}
		
		for (int i = 0; i < array.length - 1; i++) {
			System.out.print(array[i] + ",");
		}
		System.out.print(array[array.length - 1]);
		System.out.println();
	}
	
	public static void printArray (double[] array) {
		if (array == null) {
			return;
		}
		
		for (int i = 0; i < array.length - 1; i++) {
			System.out.print(array[i] + ",");
		}
		System.out.print(array[array.length - 1]);
		System.out.println();
	}

	public static int[] strToIntArray (String[] array) {
		int[] intArray = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			intArray[i] = Integer.parseInt(array[i]);
		}
		return intArray;
	}

	public static void removeDuplicate(ArrayList<String> arlList) {// Removes duplicates from given ArrayList
		ArrayList<String> newList = new ArrayList<String>();
		for (String item : arlList) {
			if (newList.contains(item)) {
				//Do Nothing
			} else {
				newList.add(item);
			}
		}
		arlList.clear();
		arlList.addAll(newList);
	}

	// INITIALIZATION METHODS ------------------------
	public static void generateVars () {
		agesVars = "P0120001";
		for (int i = 3; i <= 49; i++) {
			if (i < 10) {
				agesVars += ",P012000" + i;
			}
			else {
				agesVars += ",P01200" + i;
			}
		}
		//System.out.println(agesVars);
		householdSizeVars = "H0160003";
		for (int i = 4; i <= 9; i++) {
			householdSizeVars += ",H016000" + i;
		}
		//System.out.println(householdSizeVars);
		householdAgeVars = "H0170003";
		for (int i = 2; i <= 11; i++) {
			if (i < 10) {
				householdAgeVars += ",H017000" + i;
			}
			else {
				householdAgeVars += ",H01700" + i;
			}
		}
		//System.out.println(householdAgeVars);
	}
	
	public static void createProgressBar (int upper) {
		final JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		panel.add(new JLabel("Obtaining info..."));
		progressFrame = new JFrame();
		progressBar = new JProgressBar(0, upper);
		progressBar.setValue(0);
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		panel.add(progressBar);
		panel.setBorder(new EmptyBorder(5, 10, 5, 10));
//		progressFrame.add(panel);
//		progressFrame.pack();
//		progressFrame.setLocationRelativeTo(null);
//		progressFrame.setResizable(false);
//		progressFrame.setVisible(true);
//		progressFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//		progressFrame.setAlwaysOnTop(true);
//		new Thread(new Runnable() {
//		    public void run() {
//		    	JOptionPane.showMessageDialog(null, panel, "Progress", JOptionPane.OK_OPTION);
//		    }
//		}).start();
	}
	
	public static void setProgressBar (int val) {
		progressBar.setIndeterminate(false);
		progressBar.setValue(val);
	}
	
	public static void closeProgressBar () {
		progressFrame.dispose();
	}
}