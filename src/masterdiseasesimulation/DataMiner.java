package masterdiseasesimulation;

import javax.swing.*;

import moremethods.MoreMethods;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class DataMiner {
	public void run() throws Exception {
		String splitBy = ",";
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Irochka\\workspace\\and\\PROEKT12\\files\\hospitals\\Outpatient Procedures - Volume.csv"));

		//Ask for type
		int type = requestType();

		//Order: Provider_ID,HOSPITAL_NAME,Measure_ID,Gastrointestinal,Eye,Nervous_system,Musculoskeletal,Skin,Genitourinary,Cardiovascular,START_DATE,END_DATE
		String line = br.readLine();
		ArrayList<String> hospitals = new ArrayList<String>();
		ArrayList<Integer> hospitalResults = new ArrayList<Integer>();
		while ((line = br.readLine()) != null) {
			String[] b = line.split(splitBy);

			//Number
			String result = b[type];
			result = result.replaceAll("[^\\d.]", "");
			if (result.equals("")) {
				hospitalResults.add(0);
			} else {
				hospitalResults.add(Integer.parseInt(result));
			}

			//Hospitals
			hospitals.add(b[1]);
		}
		br.close();


		//Begin analysis
		System.out.println(hospitals);
		int max = Collections.max(hospitalResults);

		MoreMethods.alert(max + "", "max");

		int hospitalNumber = hospitalResults.indexOf(max);

		String mostVolumeHospital = hospitals.get(hospitalNumber);

		MoreMethods.alert("The hospital with the most " + getTypeByColumn(type) + " volume is " + mostVolumeHospital.toLowerCase() + ".", "Results");
	}

	private int requestType () {
		String[] possibilities = {"Gastrointestinal", "Eye", "Nervous System", "Musculoskeletal", "Skin", "Genitourinary", "Cardiovascular"};
		JComboBox layoutAnswer = new JComboBox(possibilities);
		JPanel question = new JPanel();
		question.add(layoutAnswer);

		JOptionPane.showConfirmDialog(null, question, "Settings", JOptionPane.OK_CANCEL_OPTION);

		int type = layoutAnswer.getSelectedIndex();

		type = MoreMethods.getColumnByType(type);

		return type;
	}

	private String getTypeByColumn (int type) {
		if (type == 3) {
			return "gastrointestinal";
		}
		if (type == 4) {
			return "eye";
		}
		if (type == 5) {
			return "nervous system";
		}
		if (type == 6) {
			return "muscoskeletal";
		}
		if (type == 7) {
			return "skin";
		}
		if (type == 8) {
			return "genitourinary";
		}
		if (type == 9) {
			return "cardiovascular";
		}
		return "N/A";
	}
}