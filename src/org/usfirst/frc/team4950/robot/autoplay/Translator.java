package org.usfirst.frc.team4950.robot.autoplay;

import org.usfirst.frc.team4950.robot.Robot;
import org.usfirst.frc.team4950.robot.subsystems.DriveTrain;

public class Translator extends Thread{

	Moments m;
	int index;
	boolean alive = true;

	public Translator() {
		m = new Moments();
		index = 0;
	}
	public void run() {
		while (alive) {
			Reading r = m.getReading(index);
			Robot.driveTrain.drive(r.getLeftPow(),r.getRightPow());
			index++;
			if (index >= m.getSize()) {
				alive = false;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void end() {
		alive = false;
	}
}
