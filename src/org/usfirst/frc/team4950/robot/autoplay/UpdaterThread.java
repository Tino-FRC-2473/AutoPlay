package org.usfirst.frc.team4950.robot.autoplay;

import org.usfirst.frc.team4950.robot.Database;
import org.usfirst.frc.team4950.robot.Robot;
import org.usfirst.frc.team4950.robot.Database.Value;

public class UpdaterThread extends Thread {
	boolean alive = true;

	public UpdaterThread() {
		super.setDaemon(true);
	}

	public void end() {
		alive = false;
	}

	@Override
	public void run() {
		while(alive) {
			try {
				String str = "";
				for (Value v : Value.values()) {
					str += (Database.getInstance().getValue(v) + " ");
				}
				
				boolean b1 = Robot.buttonCommand.isRunning, b2 = Robot.timedCommand.isRunning;
				
				str += (b1 + " " + b2 + " ");
				//System.out.println("Updater added - " + str);
				//System.out.println("Size: " + Robot.tempData.size());
				Robot.tempData.add(str);
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
