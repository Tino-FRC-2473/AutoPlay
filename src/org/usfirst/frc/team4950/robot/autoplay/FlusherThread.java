package org.usfirst.frc.team4950.robot.autoplay;

import java.io.PrintStream;
import java.util.ArrayList;

import org.usfirst.frc.team4950.robot.Robot;

//This class flushes out the ArrayBlockingQueue tempData from Robot,
//and prints the data to the given PrintStream.
public class FlusherThread extends Thread {
	//boolean that determines whether the thread should continue running
	boolean alive;

	//PrintStream that has all values printed out to
	PrintStream out;
	
	public FlusherThread(PrintStream out) {
		this.out = out;
		alive = true;
		super.setDaemon(false);
	}

	public void end() {
		alive = false;
	}

	//Drains all data in the ArrayBlockingQueue in Robot to an
	//ArrayList, which then is printed out to the PrintStream out.
	@Override
	public void run() {
		while(alive) {
			try {
				ArrayList<String> arr = new ArrayList<>();
				Robot.tempData.drainTo(arr);
				
				for(int i = 0; i < arr.size(); i++) {
					out.println(arr.get(i));
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
