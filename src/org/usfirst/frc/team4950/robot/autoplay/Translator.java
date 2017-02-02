package org.usfirst.frc.team4950.robot.autoplay;

import org.usfirst.frc.team4950.robot.subsystems.ExampleSubsystem;

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
			ExampleSubsystem.power(r.getLeftPow());
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
