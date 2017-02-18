package org.usfirst.frc.team4950.robot.autoplay;

import java.util.Map;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import org.usfirst.frc.team4950.robot.Database;
import org.usfirst.frc.team4950.robot.Robot;
import org.usfirst.frc.team4950.robot.Database.Value;

import edu.wpi.first.wpilibj.command.Command;

public class UpdaterThread extends Thread {
	Map<String, Supplier<Command>> commandMap;
	Map<String, DoubleSupplier> motorMaker; //unused??
	boolean alive = true;

	public UpdaterThread(Map<String, Supplier<Command>> systems) {
		commandMap = systems;
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
					if(!v.equals(Value.THROTTLE_VALUE) && !v.equals(Value.WHEEL_TWIST))
						str += (Database.getInstance().getValue(v) + " ");
				}

				for (String s : commandMap.keySet()) {
					boolean b = commandMap.get(s).get() != null;
					str += (b + " ");
				}
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
