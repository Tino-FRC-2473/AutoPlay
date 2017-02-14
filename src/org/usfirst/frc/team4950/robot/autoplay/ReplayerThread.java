package org.usfirst.frc.team4950.robot.autoplay;

import org.usfirst.frc.team4950.robot.Robot;
import org.usfirst.frc.team4950.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team4950.robot.subsystems.ButtonSubsystem;


public class ReplayerThread extends Thread {
	int x;
	boolean alive = true;

	public ReplayerThread() {
		x = 0;
	}
	public void run() {
		while (alive) {
			try {
				Reading r = Moments.getReading(x);
				Robot.exampleSubsystem.power(r.getLeftPow());
				/*if (r.getGearMech()) {
					Robot.buttonSubsystem.printStart();
				}
				if (!r.getGearMech() && x-1 >= 0) {
					Reading prev = Moments.getReading(x-1);
					if (prev.getGearMech())
						Robot.buttonSubsystem.printEnd();
				}*/
				x++;
				if (x >= Moments.getSize()) {
					alive = false;
				}
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
