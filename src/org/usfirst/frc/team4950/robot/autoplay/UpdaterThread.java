package org.usfirst.frc.team4950.robot.autoplay;

import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import org.usfirst.frc.team4950.robot.Database;
import org.usfirst.frc.team4950.robot.Robot;
import org.usfirst.frc.team4950.robot.Database.Value;

import edu.wpi.first.wpilibj.command.Command;

public class UpdaterThread extends Thread {
	Map<String, Supplier<Command>> commandMap;
	boolean alive = true;

	public UpdaterThread() {
		commandMap = new HashMap<>();
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
				str += "0.0 0.0 0 0 ";
				
				boolean b1 = Robot.buttonCommand.isRunning, b2 = Robot.timedCommand.isRunning;
				
				System.out.println(b1 + " " + b2);
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
