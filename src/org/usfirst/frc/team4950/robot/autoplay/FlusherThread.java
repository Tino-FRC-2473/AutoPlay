package org.usfirst.frc.team4950.robot.autoplay;

import java.io.PrintStream;
import java.util.ArrayList;

import org.usfirst.frc.team4950.robot.Robot;

public class FlusherThread extends Thread {
	boolean alive;

	PrintStream out;
	
	public FlusherThread(PrintStream out) {
		this.out = out;
		alive = true;
		super.setDaemon(false);
	}

	public void end() {
		alive = false;
	}

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
