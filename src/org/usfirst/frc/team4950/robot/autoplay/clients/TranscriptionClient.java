package org.usfirst.frc.team4950.robot.autoplay.clients;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.usfirst.frc.team4950.robot.autoplay.Reading;

public class TranscriptionClient {
	public static void main(String[] args) {
		TranscriptionClient t = new TranscriptionClient(new File("moments.txt"));
		t.writeJavaFile();
	}
	
	File f;
	Scanner in;

	public TranscriptionClient(File data) {
		try {
			f = data;
			in = new Scanner(f);
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void writeJavaFile() {
		ArrayList<Reading> readings = getReadingsFromFile();
		
		FileWriter writer;
		try {
			//change team # when necessary
			String path = "src/org/usfirst/frc/team4950/robot/extras/Moments.java";
			File f = new File(path);
			f.createNewFile();
			writer = new FileWriter(f);
			
			//change team # when necessary
			writer.write("package org.usfirst.frc.team4950.robot;" + "\n");
			writer.write("import java.util.*;" + "\n");
			writer.write("\n");
			writer.write("public class Moments {" + "\n");
			writer.write("    private static ArrayList<Reading> readings = new ArrayList<Reading>(" + "\n");
			writer.write("        Arrays.asList(" + "\n");
			
			for(int i = 0; i < readings.size(); i++) {
				writer.write("            ");
				writer.write("new Reading(");
				writer.write(readings.get(i).getLeftPow() + ", ");
				writer.write(readings.get(i).getRightPow() + ", ");
				writer.write(readings.get(i).getGyro() + ", ");
				writer.write(readings.get(i).getLeftEnc() + ", ");
				writer.write(readings.get(i).getRightEnc() + ")");
				if(i < readings.size()-1) {
					writer.write(",");
				}
				writer.write("\n");
			}
			
			writer.write("        )" + "\n");
			writer.write("    );" + "\n");
			writer.write("\n");
			writer.write("    public ArrayList<Reading> getReadings() { return readings; }" + "\n");
			writer.write("    public Reading getReading(int i) { return readings.get(i); }" + "\n");
			writer.write("}" + "\n");
			
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	private ArrayList<Reading> getReadingsFromFile() {
		ArrayList<Reading> arr = new ArrayList<Reading>();

		while(in.hasNextLine()) {
			arr.add(getReadingFromLine(in.nextLine()));
		}

		return arr;
	}
	
	
	
	private Reading getReadingFromLine(String str) {
		int i = 0;
		String n = "";
		while(str.charAt(i) != ' ') {
			n += str.charAt(i);
			i++;
		}
		
		return new Reading(Double.parseDouble(n), 0, 0, 0, 0, false, false);
		
		/*ArrayList<Integer> spaces = new ArrayList<Integer>();
		for(int j = 0; j < str.length(); j++) {
			if(str.charAt(j) == ' ') {
				spaces.add(j);
			}
		}
		
		if(spaces.size() < 7) {
			System.out.println("not enough spaces to account for 7 readings");
			return new Reading();
			
		} else {
			for(int j = 0; j < spaces.size(); j++) {
				String data;
				switch(j) {
				case 0:
					data = str.substring(0, spaces.get(i));
					break;
				case 6:
					data = str.substring(spaces.get(i)+1, str.length());
					break;
				default:
					data = str.substring(spaces.get(i)+1, spaces.get(i+1));
					break;
				}
				
				
			}
			
			
			return new Reading(lp, rp, g, le, re, gm, s);
		}*/
	}
}
