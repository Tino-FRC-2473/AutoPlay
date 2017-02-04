package org.usfirst.frc.team4950.robot.autoplay;

import org.usfirst.frc.team4950.robot.Robot;
import org.usfirst.frc.team4950.robot.subsystems.DriveTrain;

public class Translator extends Thread{

	Moments m;
	int x;
	boolean alive = true;

	public Translator() {
		m = new Moments();
		x = 0;
	}
	public void run() {
		while (alive) {
			Reading r = m.getReading(x);
			Robot.driveTrain.setPow(r.getLeftPow(),r.getRightPow());
			x++;
			if (x >= m.getReadings().size()) {
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
