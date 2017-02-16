package org.usfirst.frc.team4950.robot.autoplay.clients;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.usfirst.frc.team4950.robot.autoplay.Reading;

public class TranscriptionClient {
	public static void main(String[] args) {
		TranscriptionClient.writeJavaFile(new File("moments.txt"));
	}

	public static void writeJavaFile(File data) {
		ArrayList<Reading> readings = getReadingsFromFile(data);
		
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
	
	private static ArrayList<Reading> getReadingsFromFile(File f) {
		ArrayList<Reading> arr = new ArrayList<Reading>();
		
		try {
			Scanner in = new Scanner(f);
			while(in.hasNextLine()) {
				arr.add(getReadingFromLine(in.nextLine()));
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return arr;
	}
	
	private static Reading getReadingFromLine(String str) {
		ArrayList<String> items = new ArrayList<String>();
		String item = "";
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) == ' ') {
				items.add(item);
				item = "";
			} else {
				item += str.charAt(i);
			}
		}
		
		if(items.size() == 7) {
			return new Reading (
				Double.parseDouble(items.get(0)),	//l_pow
				Double.parseDouble(items.get(1)),	//r_pow
				Double.parseDouble(items.get(2)),	//gyro
				Integer.parseInt(items.get(3)),		//l_enc
				Integer.parseInt(items.get(4)),		//r_enc
				Boolean.getBoolean(items.get(5)),	//g_mech
				Boolean.getBoolean(items.get(6))	//shoot
			);
		} else {
			System.out.println("Trying to parse line, but it has " + items.size() + " items.");
			return new Reading();
		}
	}
}
