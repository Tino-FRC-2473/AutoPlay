package org.usfirst.frc.team4950.robot.autoplay;

import org.usfirst.frc.team4950.robot.Robot;
import org.usfirst.frc.team4950.robot.subsystems.ExampleSubsystem;

import edu.wpi.first.wpilibj.buttons.Trigger.ButtonScheduler;

import org.usfirst.frc.team4950.robot.subsystems.ButtonSubsystem;


public class ReplayerThread extends Thread {
	int x;
	boolean alive = true;

	public ReplayerThread() {
		x = 0;
	}
	public void run() {
		System.out.println("in thread started run");
		while (alive) {
			try {
				Reading r = Moments.getReading(x);
				System.out.println(r);
				Reading p = null;
				if(x-1 >= 0)
					p = Moments.getReading(x-1);
				
				Robot.exampleSubsystem.power(r.getLeftPow());
				if (r.getGearMech() && (p == null || !p.getGearMech())) {
					Robot.buttonCommand.start();
				}
				if (r.getShooter() && (p == null || !p.getShooter())) {
					Robot.timedCommand.start();
				}
				
				if (!r.getGearMech() && x-1 >= 0) {
					if (p != null && p.getGearMech())
						Robot.buttonCommand.cancel();
				}
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
