package masterdiseasesimulation;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import moremethods.MoreMethods;

import org.jfree.data.category.DefaultCategoryDataset;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class MasterManySimsObject
{
	@SuppressWarnings("static-access")
	public static void run () throws java.io.IOException, jxl.write.WriteException, BiffException {    	
		MoreMethods methods = new MoreMethods();
		Random random = new Random();
		
		ArrayList<Object> inputs = UserInterface.getInput(); // Get inputs from user
		System.out.println(inputs);
		
		// Separate input into variables
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
		boolean saveResults = (Boolean)inputs.get(46);
		boolean openResults = (Boolean)inputs.get(47);
		String graphFileName = inputs.get(43).toString();
		boolean saveGraph = (Boolean)inputs.get(48);
		boolean openGraph = (Boolean)inputs.get(49);

		String xAxis = inputs.get(44).toString();
		String yAxis = inputs.get(45).toString();
		
		String networkType = inputs.get(50).toString();

		//int totalRuns = (((numPeopleMax - numPeople) / numPeopleStep) + 1) * (((minFriendsMax - minFriends) / minFriendsStep) + 1) * (((maxFriendsMax - maxFriends) / maxFriendsStep) + 1) * (((hubNumberMax - hubNumber) / hubNumberStep) + 1) * (((getWellDaysMax - getWellDays) / getWellDaysStep) + 1) * (((discoveryMax - discovery) / discoveryStep) + 1) * (((newGetWellDaysMax - newGetWellDays) / newGetWellDaysStep) + 1) * (((initiallySickMax - initiallySick) / initiallySickStep) + 1) * (((initiallyVaccMax - initiallyVacc) / initiallyVaccStep) + 1) * (((percentSickMax - percentSick) / percentSickStep) + 1) * (((getVacMax - getVac) / getVacStep) + 1);
		//System.out.println(totalRuns);

		// Calculate totalRuns for progress bar
		int totalRuns = 0;

		for (int numPeopleI = numPeople; numPeopleI <= numPeopleMax; numPeopleI += numPeopleStep){
			for (int minFriendsI = minFriends; minFriendsI <= minFriendsMax; minFriendsI += minFriendsStep){
				for (int maxFriendsI = maxFriends; maxFriendsI <= maxFriendsMax; maxFriendsI += maxFriendsStep){
					for (int hubNumberI = hubNumber; hubNumberI <= hubNumberMax; hubNumberI += hubNumberStep){
						for (int getWellDaysI = getWellDays; getWellDaysI <= getWellDaysMax; getWellDaysI += getWellDaysStep){
							for (int discoveryI = discovery; discoveryI <= discovery; discoveryI += discoveryStep){
								for (int newGetWellDaysI = newGetWellDays; newGetWellDaysI <= newGetWellDaysMax; newGetWellDaysI += newGetWellDaysStep){
									for (int initiallySickI = initiallySick; initiallySickI <= initiallySickMax; initiallySickI += initiallySickStep){
										for (int initiallyVaccI = initiallyVacc; initiallyVaccI <= initiallyVaccMax; initiallyVaccI += initiallyVaccStep){
											for (int percentSickI = percentSick; percentSickI <= percentSickMax; percentSickI += percentSickStep){
												for (int getVacI = getVac; getVacI <= getVacMax; getVacI += getVacStep){
													for (int percentTeensI = percentTeens; percentTeensI <= percentTeensMax; percentTeensI += percentTeensStep){
														for (int percentCurfewI = percentCurfew; percentCurfewI <= percentCurfewMax; percentCurfewI += percentCurfewStep){
															for (int curfewDaysI = curfewDays; curfewDaysI <= curfewDaysMax; curfewDaysI += curfewDaysStep){
																if (!((minFriendsI > numPeopleI) || (maxFriendsI > numPeopleI) || (minFriendsI > maxFriendsI) || (hubNumberI > numPeopleI) || (initiallySickI + initiallyVaccI > numPeople))) {
																	totalRuns++;
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if (totalRuns <= 0) {
			JOptionPane.showMessageDialog(new JFrame(), "ERROR: No valid runs detected. Please try again with valid parameters. Exiting program...", "Heap Space Exceeded Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		else if (totalRuns > 100000) {
			int answer = JOptionPane.showConfirmDialog(null, "WARNING: Given parameters could exceed java heap space. Continue?", "Warning", JOptionPane.YES_NO_OPTION);
			if (answer != JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}

		int i = 1;
		int progress = 1;
		ArrayList<Integer> results;
		ArrayList<Person> people = new ArrayList<Person>();
		
		// Initialize spreadsheet
		File file = new File(fileName);
		WritableWorkbook workbook = Workbook.createWorkbook(file);
		int sheetIndex = 0;
		workbook.createSheet("Sheet" + sheetIndex, sheetIndex);
		WritableSheet sheet = workbook.getSheet(sheetIndex);
		if (saveResults) {
			sheet.insertRow(0);
			sheet.insertColumn(0);
			sheet.addCell(new Label(0, 0, "numPeople"));
			sheet.addCell(new Label(1, 0, "minFriends"));
			sheet.addCell(new Label(2, 0, "maxFriends"));
			sheet.addCell(new Label(3, 0, "hubNumber"));
			sheet.addCell(new Label(4, 0, "getWellDays"));
			sheet.addCell(new Label(5, 0, "discovery"));
			sheet.addCell(new Label(6, 0, "newGetWellDays"));
			sheet.addCell(new Label(7, 0, "initiallySick"));
			sheet.addCell(new Label(8, 0, "initiallyVacc"));
			sheet.addCell(new Label(9, 0, "percentSick"));
			sheet.addCell(new Label(10, 0, "getVac"));
			sheet.addCell(new Label(11, 0, "curfewDays"));
			sheet.addCell(new Label(12, 0, "percentTeens"));
			sheet.addCell(new Label(13, 0, "percentCurfew"));
			sheet.addCell(new Label(14, 0, ""));
			sheet.addCell(new Label(15, 0, "days"));
			sheet.addCell(new Label(16, 0, "cost"));
			sheet.addCell(new Label(17, 0, "totalSick"));
		}
		
		// Create data map
		LinkedHashMap<Integer, Double> data = new LinkedHashMap<Integer, Double>();
		HashMap<Integer, Double> runTimes = new HashMap<Integer, Double>();
		int resultIndex = 0;
		if (saveGraph) {
			int init = 0;
			int step = 0;
			int max = 0;
			if (xAxis.equals("numPeople")) {
				init = numPeople; step = numPeopleStep; max = numPeopleMax;
			}
			else if (xAxis.equals("minFriends")) {
				init = minFriends; step = minFriendsStep; max = minFriendsMax;
			}
			else if (xAxis.equals("maxFriends")) {
				init = maxFriends; step = maxFriendsStep; max = maxFriendsMax;
			}
			else if (xAxis.equals("hubNumber")) {
				init = hubNumber; step = hubNumberStep; max = hubNumberMax;
			}
			else if (xAxis.equals("getWellDays")) {
				init = getWellDays; step = getWellDaysStep; max = getWellDaysMax;
			}
			else if (xAxis.equals("percentSick")) {
				init = percentSick; step = percentSickStep; max = percentSickMax;
			}
			else if (xAxis.equals("initiallySick")) {
				init = initiallySick; step = initiallySickStep; max = initiallySickMax;
			}
			else if (xAxis.equals("initiallyVacc")) {
				init = initiallyVacc; step = initiallyVaccStep; max = initiallyVaccMax;
			}
			else if (xAxis.equals("getVacc")) {
				init = getVac; step = getVacStep; max = getVacMax;
			}
			else if (xAxis.equals("discovery")) {
				init = discovery; step = discoveryStep; max = discoveryMax;
			}
			else if (xAxis.equals("newGetWellDays")) {
				init = newGetWellDays; step = newGetWellDaysStep; max = newGetWellDaysMax;
			}
			else if (xAxis.equals("percentTeens")) {
				init = percentTeens; step = percentTeensStep; max = percentTeensMax;
			}
			else if (xAxis.equals("curfewDays")) {
				init = curfewDays; step = curfewDaysStep; max = curfewDaysMax;
			}
			else if (xAxis.equals("percentCurfew")) {
				init = percentCurfew; step = percentCurfewStep; max = percentCurfewMax;
			}
			for (int j = init; j <= max; j += step) {
				data.put(j, 0.0);
				runTimes.put(j, 0.0);
			}

			if (yAxis.equals("Cost")) {
				resultIndex = 1;
			}
			else if (yAxis.equals("Days")) {
				resultIndex = 0;
			}
			else if (yAxis.equals("TotalSick")) {
				resultIndex = 2;
			}
		}

		// Make progress bar
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,0));
		panel.add(new JLabel("Running simulations..."));
		JFrame frame = new JFrame();
		JProgressBar progressBar = new JProgressBar(0, totalRuns);
		progressBar.setValue(0);
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		panel.add(progressBar);
		panel.add(new JLabel("Estimated remaining time:"));
		JLabel remainingTime = new JLabel("Calculating remaining time...                   ");
		panel.add(remainingTime);
		panel.setBorder(new EmptyBorder(5, 10, 5, 10));
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Start timer
		double startTime = System.currentTimeMillis();
		double execTime = 0;
		double endTime = startTime;
		String estDisplay = "";
		
		for (int numPeopleI = numPeople; numPeopleI <= numPeopleMax; numPeopleI += numPeopleStep){
			for (int minFriendsI = minFriends; minFriendsI <= minFriendsMax; minFriendsI += minFriendsStep){
				for (int maxFriendsI = maxFriends; maxFriendsI <= maxFriendsMax; maxFriendsI += maxFriendsStep){
					for (int hubNumberI = hubNumber; hubNumberI <= hubNumberMax; hubNumberI += hubNumberStep){
						for (int getWellDaysI = getWellDays; getWellDaysI <= getWellDaysMax; getWellDaysI += getWellDaysStep){
							for (int discoveryI = discovery; discoveryI <= discovery; discoveryI += discoveryStep){
								for (int newGetWellDaysI = newGetWellDays; newGetWellDaysI <= newGetWellDaysMax; newGetWellDaysI += newGetWellDaysStep){
									for (int initiallySickI = initiallySick; initiallySickI <= initiallySickMax; initiallySickI += initiallySickStep){
										for (int initiallyVaccI = initiallyVacc; initiallyVaccI <= initiallyVaccMax; initiallyVaccI += initiallyVaccStep){
											for (int percentSickI = percentSick; percentSickI <= percentSickMax; percentSickI += percentSickStep){
												for (int getVacI = getVac; getVacI <= getVacMax; getVacI += getVacStep){
													for (int percentTeensI = percentTeens; percentTeensI <= percentTeensMax; percentTeensI += percentTeensStep){
														for (int percentCurfewI = percentCurfew; percentCurfewI <= percentCurfewMax; percentCurfewI += percentCurfewStep){
															for (int curfewDaysI = curfewDays; curfewDaysI <= curfewDaysMax; curfewDaysI += curfewDaysStep){
																if ((minFriendsI > numPeopleI) || (maxFriendsI > numPeopleI) || (minFriendsI > maxFriendsI) || (hubNumberI > numPeopleI) || (initiallySickI + initiallyVaccI > numPeople)) {
																	//Do nothing
																}
																else {
																	i++;
																	progress++;
																	people = methods.getPeople(numPeople);
																	if (networkType.equals("Small World")) {
																		methods.befriendSmallWorld(people, minFriendsI, maxFriendsI, random, hubNumberI);
																	}
																	else if (networkType.equals("Random")) {
																		methods.befriendRandom(people, minFriendsI, maxFriendsI, random, hubNumberI);
																	}
																	else if (networkType.equals("Scale-Free")) {
																		methods.befriendScaleFree(people, minFriendsI, maxFriendsI, random);
																	}
																	else {
																		JOptionPane.showMessageDialog(new JFrame(), "ERROR: Network selection error. Shutting down program..." + networkType, "Input Error", JOptionPane.ERROR_MESSAGE);
																		System.exit(0);
																	}
																	//ArrayList<Person> infected = methods.infectRandom(people, initiallySickI);
																	//ArrayList<Person> vaccinnated = methods.vaccRandom(people, initiallyVaccI);
																	//ArrayList<Person> teens = methods.getAndSetTeenagers(people, percentTeensI);
																	
																	methods.infectRandom(people, initiallySickI);
																	methods.vaccRandom(people, initiallyVaccI);
																	methods.getAndSetTeenagers(people, percentTeensI);
																	
																	results = methods.simulate(people, getWellDaysI, initiallySickI, initiallyVaccI, discoveryI, newGetWellDaysI, percentSickI, getVacI, curfewDaysI, 10);
																	//System.out.println(numPeopleI + " " + minFriendsI  + " " +  maxFriendsI + " " + hubNumberI + " " + getWellDaysI + " " + discoveryI + " " + newGetWellDaysI + " " + initiallySickI + " " + initiallyVaccI + " " + percentSickI + " " + getVacI);
																	//System.out.println(results);

																	// Add value to graph
																	if (saveGraph) {
																		if (xAxis.equals("numPeople")) {
																			data.put(numPeopleI, data.get(numPeopleI) + results.get(resultIndex));
																			runTimes.put(numPeopleI, runTimes.get(numPeopleI) + 1);
																		}
																		else if (xAxis.equals("minFriends")) {
																			data.put(minFriendsI, data.get(minFriendsI) + results.get(resultIndex));
																			runTimes.put(minFriendsI, runTimes.get(minFriendsI) + 1);
																		}
																		else if (xAxis.equals("maxFriends")) {
																			data.put(maxFriendsI, data.get(maxFriends) + results.get(resultIndex));
																			runTimes.put(maxFriends, runTimes.get(maxFriends) + 1);
																		}
																		else if (xAxis.equals("hubNumber")) {
																			data.put(hubNumberI, data.get(hubNumberI) + results.get(resultIndex));
																			runTimes.put(hubNumberI, runTimes.get(hubNumberI) + 1);
																		}
																		else if (xAxis.equals("getWellDays")) {
																			data.put(getWellDaysI, data.get(getWellDaysI) + results.get(resultIndex));
																			runTimes.put(getWellDaysI, runTimes.get(getWellDaysI) + 1);
																		}
																		else if (xAxis.equals("percentSick")) {
																			data.put(percentSickI, data.get(percentSickI) + results.get(resultIndex));
																			runTimes.put(percentSickI, runTimes.get(percentSickI) + 1);
																		}
																		else if (xAxis.equals("initiallySick")) {
																			data.put(initiallySickI, data.get(initiallySickI) + results.get(resultIndex));
																			runTimes.put(initiallySickI, runTimes.get(initiallySickI) + 1);
																		}
																		else if (xAxis.equals("initiallyVacc")) {
																			data.put(initiallyVaccI, data.get(initiallyVaccI) + results.get(resultIndex));
																			runTimes.put(initiallyVaccI, runTimes.get(initiallyVaccI) + 1);
																		}
																		else if (xAxis.equals("getVacc")) {
																			data.put(getVacI, data.get(getVacI) + results.get(resultIndex));
																			runTimes.put(getVacI, runTimes.get(getVacI) + 1);
																		}
																		else if (xAxis.equals("discovery")) {
																			data.put(discoveryI, data.get(getVacI) + results.get(resultIndex));
																			runTimes.put(getVacI, runTimes.get(getVacI) + 1);
																		}
																		else if (xAxis.equals("newGetWellDays")) {
																			data.put(newGetWellDaysI, data.get(newGetWellDaysI) + results.get(resultIndex));
																			runTimes.put(newGetWellDaysI, runTimes.get(newGetWellDaysI) + 1);
																		}
																		else if (xAxis.equals("percentTeens")) {
																			data.put(percentTeensI, data.get(percentTeensI) + results.get(resultIndex));
																			runTimes.put(percentTeensI, runTimes.get(percentTeensI) + 1);
																		}
																		else if (xAxis.equals("curfewDays")) {
																			data.put(curfewDaysI, data.get(percentTeensI) + results.get(resultIndex));
																			runTimes.put(percentTeensI, runTimes.get(percentTeensI) + 1);
																		}
																		else if (xAxis.equals("percentCurfew")) {
																			data.put(percentCurfewI, data.get(percentCurfewI) + results.get(resultIndex));
																			runTimes.put(percentCurfewI, runTimes.get(percentCurfewI) + 1);
																		}
																	}
																	if (saveResults) {
																		try {
																			sheet.insertRow(i);
																			sheet.addCell(new Label(0, i, Integer.toString(numPeopleI)));
																		}
																		catch (jxl.write.biff.RowsExceededException e) {
																			sheetIndex++;
																			i = 1;
																			workbook.createSheet("Sheet" + sheetIndex, sheetIndex);
																			sheet = workbook.getSheet(sheetIndex);

																			sheet.insertRow(0);
																			sheet.insertColumn(0);
																			sheet.addCell(new Label(0, 0, "numPeople"));
																			sheet.addCell(new Label(1, 0, "minFriends"));
																			sheet.addCell(new Label(2, 0, "maxFriends"));
																			sheet.addCell(new Label(3, 0, "hubNumber"));
																			sheet.addCell(new Label(4, 0, "getWellDays"));
																			sheet.addCell(new Label(5, 0, "discovery"));
																			sheet.addCell(new Label(6, 0, "newGetWellDays"));
																			sheet.addCell(new Label(7, 0, "initiallySick"));
																			sheet.addCell(new Label(8, 0, "initiallyVacc"));
																			sheet.addCell(new Label(9, 0, "percentSick"));
																			sheet.addCell(new Label(10, 0, "getVac"));
																			sheet.addCell(new Label(11, 0, "curfewDays"));
																			sheet.addCell(new Label(12, 0, "percentTeens"));
																			sheet.addCell(new Label(13, 0, "percentCurfew"));
																			sheet.addCell(new Label(14, 0, ""));
																			sheet.addCell(new Label(15, 0, "days"));
																			sheet.addCell(new Label(16, 0, "cost"));
																			sheet.addCell(new Label(17, 0, "totalSick"));

																			sheet.addCell(new Label(0, i, Integer.toString(numPeopleI)));
																		}
																		sheet.addCell(new Label(1, i, Integer.toString(minFriendsI)));
																		sheet.addCell(new Label(2, i, Integer.toString(maxFriendsI)));
																		sheet.addCell(new Label(3, i, Integer.toString(hubNumberI)));
																		sheet.addCell(new Label(4, i, Integer.toString(getWellDaysI)));
																		sheet.addCell(new Label(5, i, Integer.toString(discoveryI)));
																		sheet.addCell(new Label(6, i, Integer.toString(newGetWellDaysI)));
																		sheet.addCell(new Label(7, i, Integer.toString(initiallySickI)));
																		sheet.addCell(new Label(8, i, Integer.toString(initiallyVaccI)));
																		sheet.addCell(new Label(9, i, Integer.toString(percentSickI)));
																		sheet.addCell(new Label(10, i, Integer.toString(getVacI)));
																		sheet.addCell(new Label(11, i, Integer.toString(curfewDaysI)));
																		sheet.addCell(new Label(12, i, Integer.toString(percentTeensI)));
																		sheet.addCell(new Label(13, i, Integer.toString(percentCurfewI)));
																		sheet.addCell(new Label(14, i, ""));
																		sheet.addCell(new Label(15, i, Integer.toString(results.get(0))));
																		sheet.addCell(new Label(16, i, Integer.toString(results.get(1))));
																		sheet.addCell(new Label(17, i, Integer.toString(results.get(2))));
																	}

																	// Update progress bar
																	if (execTime + 500.0 < System.currentTimeMillis() - startTime){
																		progressBar.setIndeterminate(false);
																		endTime = System.currentTimeMillis();
																		execTime = endTime - startTime;
																		double estTime = ((execTime / progress) * (totalRuns - progress)) / 1000.0;
																		estDisplay = MoreMethods.timeString(estTime) + " remaining...";
																		remainingTime.setText(estDisplay);
																		panel.remove(remainingTime);
																		panel.add(remainingTime);
																	}
																	progressBar.setValue(progress);
																	frame.repaint();
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		// Display total execution time
		frame.dispose();
		endTime = System.currentTimeMillis();
		execTime = endTime - startTime;
		double estTime = execTime / 1000.0;
		estDisplay = "Total execution time:\n" + MoreMethods.timeString(estTime);
		//UserInterface.displayMessage(estDisplay);
		
		// Create progress bar
		// Make progress bar
		panel = new JPanel();
		panel.setLayout(new GridLayout(2, 0));
		panel.add(new JLabel("Finalizing...                                      "));
		frame = new JFrame();
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		panel.add(progressBar);
		panel.add(new JLabel("Current task:"));
		JLabel status = new JLabel("");
		panel.setBorder(new EmptyBorder(5, 10, 5, 10));
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Graph
		if (saveGraph) {
			status.setText("Saving graph...");
			panel.remove(status);
			panel.add(status);
			
			int key;
			double value;
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			DefaultCategoryDataset derivatives = new DefaultCategoryDataset();
			//DefaultCategoryDataset derivativeData = new DefaultCategoryDataset();
			//DefaultCategoryDataset secondDerivativeData = new DefaultCategoryDataset();
			LinkedHashMap<Integer, Double> averages = new LinkedHashMap<Integer, Double>();
			for (Entry<Integer, Double> entry : data.entrySet()) {
				key = entry.getKey();
				dataset.addValue((1.0 * data.get(key)) / runTimes.get(key), xAxis, Integer.toString(key));
				averages.put(key, (1.0 * data.get(key)) / runTimes.get(key));
			}
			System.out.println(averages);
			LinkedHashMap<Integer, Double> derivative = MoreMethods.getDerivative(averages);
			
			LinkedHashMap<Integer, Double> sine = new LinkedHashMap<Integer, Double>();
			sine.put(-1, -0.841);
			sine.put(0, 0.0);
			sine.put(1, 0.841);
			sine.put(2, 0.909);
			sine.put(3, 0.141);
			sine.put(4, -0.757);
			sine.put(5, -0.959);
			sine.put(6, -0.279);
			sine.put(7, 0.657);
			sine.put(8, 0.989);
			
			//derivative = MoreMethods.getDerivative(sine);
			
			System.out.println(derivative);
			for (Entry<Integer, Double> entry : derivative.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				derivatives.addValue(value, "Derivative", Integer.toString(key));
			}
			LinkedHashMap<Integer, Double> secondDerivative = MoreMethods.getDerivative(derivative);
			System.out.println(secondDerivative);
			for (Entry<Integer, Double> entry : secondDerivative.entrySet()) {
				key = entry.getKey();
				value = entry.getValue();
				derivatives.addValue(value, "2nd Derivative", Integer.toString(key));
			}

			File lineChart = MoreMethods.makeChart(dataset, graphFileName, yAxis + " vs. " + xAxis, xAxis, yAxis);
			File derivativeChart = MoreMethods.makeChart(derivatives, graphFileName + " (derivatives)", yAxis + " vs. " + xAxis, xAxis, yAxis);
			
			if (openGraph) {
				status.setText("Opening graph...");
				panel.remove(status);
				panel.add(status);
				
				Desktop.getDesktop().open(lineChart);
				Desktop.getDesktop().open(derivativeChart);
			}
		}

		// Results spreadsheet
		if (saveResults) {
			status.setText("Saving results spreadsheet...");
			panel.remove(status);
			panel.add(status);
			
			try {
				workbook.write();
			}
			catch (java.lang.OutOfMemoryError e) {
				JOptionPane.showMessageDialog(new JFrame(), "ERROR: Java heap space exceeded. Please try again with smaller parameters.", "Heap Space Exceeded Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			workbook.close();
			
			if (openResults) {
				status.setText("Opening results...");
				panel.remove(status);
				panel.add(status);
				
				Desktop.getDesktop().open(file);
			}
		}
		else {
			file.delete();
		}
		
		status.setText("Cleaning up...");
		panel.remove(status);
		panel.add(status);
		frame.dispose();
		
		while (true) {
			ArrayList<Integer> best = UserInterface.analyze(inputs);
			if (best == null) {
				JOptionPane.showMessageDialog(new JFrame(), "ERROR: No valid solution found. Please try again valid input.", "No Solution Error", JOptionPane.ERROR_MESSAGE);
			}
			else {
				UserInterface.displayMessage("The best option for the entered input is: " + best + "\nnumPeople: " + best.get(0) + "\nminFriends: " + best.get(1) + "\nmaxFriends: " + best.get(2) + "\nhubNumber: " + best.get(3) + "\ngetWellDays: " + best.get(4) + "\ndiscovery: " + best.get(5) + "\nnewGetWellDays: " + best.get(6) + "\ninitiallySick: " + best.get(7) + "\ninitiallyVacc: " + best.get(8) + "\npercentSick: " + best.get(9) + "\ngetVac: " + best.get(10) + "\ncurfewDays: " + best.get(11) + "\npercentTeens: " + best.get(12) + "\npercentCurfew: " + best.get(13) + "\n\ndays: " + best.get(14) + "\ncost: " + best.get(15) + "\ntotalSick: " + best.get(16));
			}
		}
	}
}