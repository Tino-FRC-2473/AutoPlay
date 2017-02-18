package org.usfirst.frc.team4950.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
	
import org.usfirst.frc.team4950.robot.OI;
import org.usfirst.frc.team4950.robot.Robot;

/**
 *
 */
public class TimedCommand extends Command {
	double timeOut;
	public boolean isRunning = false;
	public TimedCommand(int duration) {
		requires(Robot.timerSubsystem);
		timeOut = duration;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}
	
	@Override
	public void start() {
		isRunning = true;
		Robot.timerSubsystem.printStart();
		setTimeout(timeOut);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.timerSubsystem.printEnd();
		isRunning = false;
	}
	
	@Override
	public void cancel() {
		Robot.timerSubsystem.printCancel();
		isRunning = false;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
