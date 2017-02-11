package org.usfirst.frc.team4950.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4950.robot.OI;
import org.usfirst.frc.team4950.robot.Robot;

/**
 *
 */
public class RecordForAutoPlay extends Command {
	public RecordForAutoPlay() {
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.driveTrain.driveArcade(-sqrtWithSign(throttleZ*.75), shapeWheel(-wheelX,throttleZ));
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.updater.end();
		Robot.flusher.end();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
