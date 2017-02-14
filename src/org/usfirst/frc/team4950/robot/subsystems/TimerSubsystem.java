package org.usfirst.frc.team4950.robot.subsystems;

import org.usfirst.frc.team4950.robot.commands.ButtonCommand;


import edu.wpi.first.wpilibj.command.Subsystem;

public class TimerSubsystem extends Subsystem{
	
	boolean started = false;
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new ButtonCommand());		
	}

	public void printStart() {
		if (!started) {
			System.out.println("Timer Command Started");
			started = true;
		}
	}
	
	public void printEnd() {
		System.out.println("Timer Command Ended");
		started = false;
	}
	
	public void printCancel() {
		System.out.println("Timer Command Canceled");
		started = false;
	}
}