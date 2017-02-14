package org.usfirst.frc.team4950.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
	
import org.usfirst.frc.team4950.robot.OI;
import org.usfirst.frc.team4950.robot.Robot;

/**
 *
 */
public class ButtonCommand extends Command {
	public boolean isRunning;
	public ButtonCommand() {
		requires(Robot.buttonSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}


	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	public void start() {
		isRunning = true;
		Robot.buttonSubsystem.printStart();
	}
	
	// Called once after isFinished returns true
	@Override
	protected void end() {
		System.out.print("e");
		Robot.buttonSubsystem.printEnd();
	}
	
	@Override
	public void cancel() {
		System.out.print("c");
		Robot.buttonSubsystem.printCancel();
		isRunning = false;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
