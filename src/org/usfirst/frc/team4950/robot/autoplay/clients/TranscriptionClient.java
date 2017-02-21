package org.usfirst.frc.team4950.robot.autoplay.clients;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.usfirst.frc.team4950.robot.autoplay.Reading;

//Takes the values from the server and writes them to a text file
public class TranscriptionClient {
	//writes java file
	public static void main(String[] args) {
		TranscriptionClient t = new TranscriptionClient(new File("moments.txt"));
		t.writeJavaFile();
	}
	
	File f;
	Scanner in;

	//initializes the file and scanner to read from the file
	public TranscriptionClient(File data) {
		try {
			f = data;
			in = new Scanner(f);
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

	//writes Moments.java as an ArrayList of Readings
	public void writeJavaFile() {
		ArrayList<Reading> readings = getReadingsFromFile();
		
		FileWriter writer;
		try {
			//change team # when necessary
			String path = "src/org/usfirst/frc/team4950/robot/autoplay/Moments.java";
			File f = new File(path);
			f.createNewFile();
			writer = new FileWriter(f);
			
			//change team # when necessary
			writer.write("package org.usfirst.frc.team4950.robot.autoplay;" + "\n");
			writer.write("import java.util.*;" + "\n");
			writer.write("\n");
			writer.write("//Class that contains all Readings that are parsed using TranscriptionClient on moments.txt");
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
				writer.write(readings.get(i).getRightEnc() + ", ");
				writer.write(readings.get(i).getGearMech() + ", ");
				writer.write(readings.get(i).getShooter() + ")");
				if(i < readings.size()-1) {
					writer.write(",");
				}
				writer.write("\n");
			}
			
			writer.write("        )" + "\n");
			writer.write("    );" + "\n");
			writer.write("\n");
			writer.write("    public static Reading getReading(int i) { return readings.get(i); }" + "\n");
			writer.write("    public static int getSize() { return readings.size(); }" + "\n");
			writer.write("}" + "\n");
			
			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//returns an ArrayList of Readings based off each line of the file
	private ArrayList<Reading> getReadingsFromFile() {
		ArrayList<Reading> arr = new ArrayList<Reading>();

		while(in.hasNextLine()) {
			arr.add(getReadingFromLine(in.nextLine()));
		}

		return arr;
	}
	
	//parses a string, that is a line in the file, into a Reading
	private Reading getReadingFromLine(String str) {
		int i = 0;
		String n1 = "", n2 = "";
		while(str.charAt(i) != ' ') {
			n1 += str.charAt(i);
			i++;
		}
		i++;
		while(str.charAt(i) != ' ') {
			n2 += str.charAt(i);
			i++;
		}
		i++;
		
		return new Reading(Double.parseDouble(n1), Double.parseDouble(n2), 0, 0, 0, false, false);
	}
}
