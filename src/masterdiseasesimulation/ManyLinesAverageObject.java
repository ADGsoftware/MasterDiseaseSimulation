package masterdiseasesimulation;

import java.awt.Dimension;
import java.awt.GridLayout;
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

import datacontainers.DayStat;
import datacontainers.InfoStorage;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import moremethods.MoreMethods;

import org.jfree.data.category.DefaultCategoryDataset;

public class ManyLinesAverageObject {
    public static void run () throws IOException {
        MoreMethods methods = new MoreMethods();

        // Initialize variables
        int numPeople = 0;
        int hubNumber = -1;
        int minFriends = -1;
        int maxFriends = -1;
        String layoutString = "";
        String networkSelectString = "";
        //String graphString = "";
        boolean drawJung = true;
        boolean doFwF = true;

        boolean done = false; // Controls when while loop stops

        // Gets input from user before graph
        while (!done) {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(10, 0));
            JTextField numPeopleAnswer = new JTextField("100", 10);
            JTextField minFriendsAnswer = new JTextField("2", 10);
            JTextField hubNumberAnswer = new JTextField("0", 10);
            JTextField maxFriendsAnswer = new JTextField("5", 10);
            String[] possibilities = {"Circle", "FR", "ISOM", "Spring"};
            JComboBox layoutAnswer = new JComboBox(possibilities);
            String[] possibilitiesNetwork = {"Small World", "Random", "Scale-Free"};
            JComboBox comboBoxNetwork = new JComboBox(possibilitiesNetwork);
            JCheckBox checkBoxGraph = new JCheckBox();
            JCheckBox checkBoxFwF = new JCheckBox();

            panel.add(new JLabel("PEOPLE SETUP:"));
            panel.add(new JLabel("----------------------------------------------"));
            panel.add(new JLabel("How many people?"));
            panel.add(numPeopleAnswer);
            panel.add(new JLabel("What is the minimum amount of friends?"));
            panel.add(minFriendsAnswer);
            panel.add(new JLabel("What is the maximum amount of friends?"));
            panel.add(maxFriendsAnswer);
            panel.add(new JLabel("How many hubs (random) or crossover lines (small world)?   "));
            panel.add(hubNumberAnswer);
            panel.add(new JLabel("Which type of network?"));
            panel.add(comboBoxNetwork);
            panel.add(new JLabel("GRAPH SETUP:"));
            panel.add(new JLabel("----------------------------------------------"));
            panel.add(new JLabel("Draw the jung diagram?"));
            panel.add(checkBoxGraph);
            panel.add(new JLabel("Perform Friends w/ Friends analysis?"));
            panel.add(checkBoxFwF);
            panel.add(new JLabel("Which layout?"));
            panel.add(layoutAnswer);

            int result = JOptionPane.showConfirmDialog(null, panel, "Before-Graph Configuration", JOptionPane.OK_CANCEL_OPTION);

            if (result != JOptionPane.OK_OPTION) {
                System.exit(0);
            }

            try {
                String numPeopleAnswerString = numPeopleAnswer.getText();
                numPeople = Integer.parseInt(numPeopleAnswerString);
                if (numPeople < 1) {
                    throw new NumberFormatException();
                }

                String hubNumberString = hubNumberAnswer.getText();
                hubNumber = Integer.parseInt(hubNumberString);
                if (hubNumber < 0 || hubNumber > numPeople) {
                    throw new NumberFormatException();
                }

                String minFriendsAnswerString = minFriendsAnswer.getText();
                minFriends = Integer.parseInt(minFriendsAnswerString);
                if (minFriends < 0 || minFriends > numPeople) {
                    throw new NumberFormatException();
                }

                String maxFriendsAnswerString = maxFriendsAnswer.getText();
                maxFriends = Integer.parseInt(maxFriendsAnswerString);
                if (minFriends > maxFriends || maxFriends > numPeople) {
                    throw new NumberFormatException();
                }

                layoutString = (String) layoutAnswer.getSelectedItem();
                networkSelectString = (String) comboBoxNetwork.getSelectedItem();
                if (!checkBoxGraph.isSelected()) {
                    drawJung = false;
                }
                if (!checkBoxFwF.isSelected()) {
                    doFwF = false;
                }

                done = true; // Only done when go through try without errors
            } catch (NumberFormatException e) {
                if (result == JOptionPane.OK_OPTION) {
                    JOptionPane.showMessageDialog(new JFrame(), "ERROR: Input is invalid.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    System.exit(0);
                }
            }
        }
        // ARAYLIST OF PEOPLE THAT WE WILL BE WORKING WITH!!! IMPORTANT IMPORTANT IMPORTANT!!!!!
        ArrayList<Person> people = new ArrayList<Person>();
        JFrame frame = new JFrame("ManyLinesAverage");
        for (int i = 1; i <= numPeople; i++) { // Start with 1 so we don't have a number 0 which is extra
            Person person = new Person(i);
            people.add(person);
        }
        if (networkSelectString.equals("Random")) {
            methods.befriendRandom(people, minFriends, maxFriends, new Random(), hubNumber);
        }
        else if (networkSelectString.equals("Small World")) {
            methods.befriendSmallWorld(people, minFriends, maxFriends, new Random(), hubNumber);
        }
        else if (networkSelectString.equals("Scale-Free")) {
            methods.befriendScaleFree(people, minFriends, maxFriends, new Random());
        }
        if (drawJung) {
            // Creating the graph, vertices and frame
            UndirectedSparseMultigraph<Person, String> graph = new UndirectedSparseMultigraph<Person, String>();
            methods.drawVerticies(graph, people);

            // Create vv and layout
            int xDim = 1000;
            int yDim = 600;
            //LAYOUT STUFF THAT SHOULD WORK BUT PROBABLY WON"T
            Layout<Person, String> layout = methods.chooseLayout(graph, layoutString);
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
            methods.drawJung(graph, vv, people);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(vv);
            frame.pack();
            frame.setVisible(true);
        }

        // Initialize variables
        int getWellDays = 0;
        int discovery = 0;
        int newGetWell = 0;
        ArrayList<Integer> infectedPeople = new ArrayList<Integer>();
        int percentSick = 0;
        int getVac = -1;
        int percentTeenagers = 0;
        int percentCurfewed = -1;
        int curfewDays = -1;
        ArrayList<Integer> vaccinatedPeople = new ArrayList<Integer>();
        ArrayList<Person> teens = new ArrayList<Person>();
        String fileName = "simResults";
        int runTimes = 0;

        done = false; // Controls when while loop stops

        // Gets input from user after graph
        while (!done) {
            JPanel panel2 = new JPanel();
            panel2.setLayout(new GridLayout(19, 0));

            JTextField getWellDaysAnswer = new JTextField("10", 10);
            JTextField discoveryAnswer = new JTextField("10000", 10);
            JTextField newGetWellAnswer = new JTextField("5", 10);
            JTextField initiallySickAnswer = new JTextField("10", 10);
            JTextField vaccinatedPeopleAnswer = new JTextField("", 10);
            JTextField percentSickAnswer = new JTextField("10", 10);
            JTextField getVacAnswer = new JTextField("0", 10);
            JTextField fileAnswer = new JTextField("simResults", 10);
            JTextField runTimesAnswer = new JTextField("10", 10);
            JTextField percentTeenagersAnswer = new JTextField("10", 10);
            JTextField percentCurfewedAnswer = new JTextField("20", 10);
            JTextField curfewDaysAnswer = new JTextField("10", 10);

            panel2.add(new JLabel("INITIAL CONDITIONS SETUP:"));
            panel2.add(new JLabel("----------------------------------------------"));
            panel2.add(new JLabel("What people will be initially sick? (Ex. 2,3,1,6)"));
            panel2.add(initiallySickAnswer);
            panel2.add(new JLabel("What people will be initially vaccinated or immune? (Ex. 8,9,20)"));
            panel2.add(vaccinatedPeopleAnswer);

            panel2.add(new JLabel("TRANSMISSION SETUP:"));
            panel2.add(new JLabel("----------------------------------------------"));
            panel2.add(new JLabel("Amount of get well days before the cure is invented?"));
            panel2.add(getWellDaysAnswer);
            panel2.add(new JLabel("What chance does each person have of catching the disease (___ %)?"));
            panel2.add(percentSickAnswer);

            panel2.add(new JLabel("RUNNING TO HOSPITAL SETUP:"));
            panel2.add(new JLabel("----------------------------------------------"));
            panel2.add(new JLabel("What percent of people run to get vaccinated when their friend gets sick?   "));
            panel2.add(getVacAnswer);

            panel2.add(new JLabel("CURE SETUP:"));
            panel2.add(new JLabel("----------------------------------------------"));
            panel2.add(new JLabel("How long will it take until a cure is discovered?"));
            panel2.add(discoveryAnswer);
            panel2.add(new JLabel("Amount of get well days after the cure is invented?"));
            panel2.add(newGetWellAnswer);

            panel2.add(new JLabel("TEENAGERS SETUP:"));
            panel2.add(new JLabel("----------------------------------------------"));
            panel2.add(new JLabel("What percent of teenagers should there be?"));
            panel2.add(percentTeenagersAnswer);
            panel2.add(new JLabel("What percent of teenagers should be curfewed?"));
            panel2.add(percentCurfewedAnswer);
            panel2.add(new JLabel("How long should the teenagers' curfew last?"));
            panel2.add(curfewDaysAnswer);

            panel2.add(new JLabel("RESULTS SETUP:"));
            panel2.add(new JLabel("----------------------------------------------"));
            panel2.add(new JLabel("How many times should the simulation run?"));
            panel2.add(runTimesAnswer);
            panel2.add(new JLabel("What should be the file-name of the output graph?"));
            panel2.add(fileAnswer);

            int result = JOptionPane.showConfirmDialog(null, panel2, "After-Graph Configuration", JOptionPane.OK_CANCEL_OPTION);

            if (result != JOptionPane.OK_OPTION) {
                System.exit(0);
            }

            try {
                String getWellDaysString = getWellDaysAnswer.getText();
                getWellDays = Integer.parseInt(getWellDaysString);
                if (getWellDays < 1) {
                    throw new NumberFormatException();
                }

                String discoveryString = discoveryAnswer.getText();
                discovery = Integer.parseInt(discoveryString);
                if (discovery < 1) {
                    throw new NumberFormatException();
                }

                String newGetWellString = newGetWellAnswer.getText();
                newGetWell = Integer.parseInt(newGetWellString);
                if (newGetWell < 1) {
                    throw new NumberFormatException();
                }

                String initiallySickString = initiallySickAnswer.getText();
                infectedPeople = MoreMethods.commaListToArrayList(initiallySickString);
                methods.removeDuplicate(infectedPeople);
                for (int i : infectedPeople) {
                    if (i > numPeople) {
                        throw new NumberFormatException();
                    }
                }

                String percentSickString = percentSickAnswer.getText();
                percentSick = Integer.parseInt(percentSickString);
                if (percentSick <= 0 || percentSick > 100) {
                    throw new NumberFormatException();
                }

                String getVacString = getVacAnswer.getText();
                getVac = Integer.parseInt(getVacString);
                if (getVac < 0 || getVac > 100) {
                    throw new NumberFormatException();
                }

                String vaccinatedPeopleString = vaccinatedPeopleAnswer.getText();
                vaccinatedPeople = MoreMethods.commaListToArrayList(vaccinatedPeopleString);
                methods.removeDuplicate(vaccinatedPeople);
                for (int i : vaccinatedPeople) {
                    if (i > numPeople) {
                        throw new NumberFormatException();
                    }
                }

                fileName = fileAnswer.getText();

                String runTimesString = runTimesAnswer.getText();
                runTimes = Integer.parseInt(runTimesString);
                if (runTimes <= 0) {
                    throw new NumberFormatException();
                }

                String percentTeenagersString = percentTeenagersAnswer.getText();
                percentTeenagers = Integer.parseInt(percentTeenagersString);
                if (percentTeenagers < 0 || percentTeenagers > 100) {
                    throw new NumberFormatException();
                }
                teens = methods.getAndSetTeenagers(people, percentTeenagers);

                String percentCurfewedString = percentCurfewedAnswer.getText();
                percentCurfewed = Integer.parseInt(percentCurfewedString);
                if (percentCurfewed < 0 || percentCurfewed > 100) {
                    throw new NumberFormatException();
                }

                String curfewDaysString = curfewDaysAnswer.getText();
                curfewDays = Integer.parseInt(curfewDaysString);
                if (curfewDays < 0) {
                    throw new NumberFormatException();
                }

                for (int i : infectedPeople){
                    for (int j : vaccinatedPeople){
                        if (i == j){
                            throw new NumberFormatException();
                        }
                    }
                }

                done = true; // Only done when go through try without errors
            } catch (NumberFormatException e) {
                if (result == JOptionPane.OK_OPTION) {
                    JOptionPane.showMessageDialog(new JFrame(), "ERROR: Input is invalid.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    System.exit(0);
                }
            }
        }

        Collections.sort(people, Person.orderByID);

        for (int i : infectedPeople) {
            people.get(i - 1).setSick(true);
        }
        for (int i : vaccinatedPeople) {
            people.get(i - 1).setImmune(true);
        }

        //System.out.println(people);

        double estimatedTime = Math.floor(0.447 * runTimes + 2768.902);
        int estimatedTimeInt = (int) estimatedTime;
        System.out.println("Estimated time: " + estimatedTimeInt + " milliseconds.");
        Long startTime =  System.currentTimeMillis();
        ArrayList<ArrayList<InfoStorage>> results = MoreMethods.simulate(people, teens, getWellDays, infectedPeople.size(), vaccinatedPeople.size(), discovery, newGetWell, percentSick, getVac, curfewDays, runTimes, percentCurfewed); //Meh I don't know how to do it better
        Long endTime =  System.currentTimeMillis();
        System.out.println("Completed " + runTimes + " simulations in " + ((endTime - startTime)) + " milliseconds.");
        boolean display = false;
        int maxDays = 100;

        ArrayList<DayStat> days = new ArrayList<DayStat>();

        //Initialize daystat array
        for (int k = 0; k < maxDays; k++) {
            days.add(new DayStat(k, 0, 0));
        }

        //Add totals for each day
        for (int i = 0; i < runTimes; i ++) {
            for (int j = 0; j < maxDays; j ++) {
                //System.out.println(results.get(i).size() + " " + j);
                if (results.get(i).size() > j + 1) { //If this day is existent
                    days.get(j).setCurrentSick(days.get(j).getCurrentSick() + results.get(i).get(j).getNumSick());
                    days.get(j).setCost(days.get(j).getCost() + results.get(i).get(j).getCost());
                } else {
                    //Add 0 to numSick and cost, which is the same as doing nothing
                }
            }
        }

        //Get averages for each day
        for (DayStat dayStat : days) {
            dayStat.setCurrentSick(dayStat.getCurrentSick() / runTimes);
            dayStat.setCost(dayStat.getCost() / runTimes);
        }

        if (display) {
            for (ArrayList<InfoStorage> alis : results) {
                System.out.println("NEW RUNTIME_________________________________________________________________________");
                for (InfoStorage is : alis) {
                    System.out.println("Welcome to day " + is.getDay() + ". Sick: " + is.getNumSick() + ". Cost: " + is.getCost() + ".");
                }
            }
        }

        //Make a graph
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (DayStat day : days) {
            dataset.addValue(day.getCurrentSick(), "Infected People", Integer.toString(day.getDay()));
        }

        MoreMethods.makeChart(dataset, fileName, "Average Number of Sick People (" + runTimes + " runs) - " + networkSelectString + " Network", "Days", "Infected People");

        if (doFwF) {
            //Friends with Friends stuff---------------------------------------
            methods.calculateConnectivityRatios(people);
            UserInterface.displayMessage("The Connectivity Of this Network is: " + methods.averageConnectivityPercentage(people) + "\n"
                    + "Standard Deviation: " + methods.standardDeviation(people) + "\n"
                    + "Median: " + methods.medianConnectivityPrecentage(people));
        }

        if (!drawJung) { // If no graph was drawn, exit
            System.exit(0);
        }
    }
}