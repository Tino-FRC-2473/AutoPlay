package org.usfirst.frc.team4950.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4950.robot.Database;
import org.usfirst.frc.team4950.robot.Database.Value;
import org.usfirst.frc.team4950.robot.OI;
import org.usfirst.frc.team4950.robot.Robot;

/**
 *
 */
public class RecordForAutoPlay extends Command {
	public static final double SPEED_TURNING_MULTIPLICATION_CONSTANT = 0.30;
	public static final double SPEED_TURNING_ADDING_CONSTANT = 0.70;
	public static final double DEADZONE_AREA = 0.04;
	public static final double MAX_TURN = 0.8;
	
	double throttleZ;
	double wheelX;
	
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
		throttleZ = Database.getInstance().getValue(Value.THROTTLE_VALUE);
		wheelX = Database.getInstance().getValue(Value.WHEEL_TWIST);
		
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
	
	private double shapeWheel(double rawIn, double speed) {
    	double sign = Math.signum(rawIn);
    	double result = rawIn;
    	rawIn *= .8;
    	
    	if(Math.abs(rawIn) < DEADZONE_AREA) return 0;
    	
    	result =  (SPEED_TURNING_ADDING_CONSTANT + SPEED_TURNING_MULTIPLICATION_CONSTANT * Math.abs(speed)/2) * Math.abs(sqrtWithSign(rawIn)) + (1 - Math.abs(speed)/2) * SPEED_TURNING_MULTIPLICATION_CONSTANT;
    	
    	
    	if(result > MAX_TURN) result = MAX_TURN;
    	
    	//System.out.println(result);
    	
    	return result * sign;
    }
	
	private double sqrtWithSign(double in) {
    	return (in > 0)?Math.sqrt(in):-Math.sqrt(-in) ;
    }
}
