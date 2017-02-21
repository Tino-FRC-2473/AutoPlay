package org.usfirst.frc.team4950.robot.autoplay;

import org.usfirst.frc.team4950.robot.Robot;

//Takes the Readings from Moments and uses the values to replay the actions of the robot.
public class ReplayerThread extends Thread {
	//index in Moments that the Thread is to read
	int x;
	//boolean determining whether the Thread should continue running
	boolean alive = true;

	//initializes x to 0
	public ReplayerThread() {
		x = 0;
		super.setDaemon(true);
	}
	
	//Every 10 ms, sets motor powers to that of the Reading which is being read
	public void run() {
		System.out.println("replayer start");
		while (alive) {
			try {
				Reading read = Moments.getReading(x);
				double l = read.getLeftPow(), r = read.getRightPow();
				System.out.println("outer: " + l + ", " + r);
				Robot.driveTrain.tankDrive(l, r);
				x++;
				if (x >= Moments.getSize()) {
					alive = false;
				}
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("replayer dead");
	}
	
	public void end() {
		alive = false;
	}
}
