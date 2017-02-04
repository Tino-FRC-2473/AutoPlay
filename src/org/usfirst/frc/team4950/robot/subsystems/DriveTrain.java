package org.usfirst.frc.team4950.robot.subsystems;

import org.usfirst.frc.team4950.robot.Database;
import org.usfirst.frc.team4950.robot.Robot;
import org.usfirst.frc.team4950.robot.RobotMap;
import org.usfirst.frc.team4950.robot.commands.Drive;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {
    
	private CANTalon leftFrontCAN;
	private CANTalon rightFrontCAN;
	private CANTalon leftBackCAN;
	private CANTalon rightBackCAN;

	private RobotDrive drive;
	
	private ReentrantReadWriteLock rwLock;
	
	public DriveTrain() {
		super();
		
		leftFrontCAN = new CANTalon(RobotMap.leftFrontMotor);
		rightFrontCAN = new CANTalon(RobotMap.rightFrontMotor);
		leftBackCAN = new CANTalon(RobotMap.leftBackMotor);
		rightBackCAN = new CANTalon(RobotMap.rightBackMotor);
		
		drive = new RobotDrive(leftFrontCAN, leftBackCAN, rightFrontCAN, rightBackCAN);
		
		drive.setMaxOutput(.70);
		drive.setInvertedMotor(MotorType.kFrontLeft, true);
		drive.setInvertedMotor(MotorType.kRearLeft, true);
		drive.setInvertedMotor(MotorType.kFrontRight, true);
		drive.setInvertedMotor(MotorType.kRearRight, true);
		
		rwLock = new ReentrantReadWriteLock();
	}
	
	@Override
    public void initDefaultCommand() {
         setDefaultCommand(new Drive());
    }
    
    public void drive(double left, double right) {
    	rwLock.readLock().lock();
    	try {
    		drive.tankDrive(left, right);
    	} finally {
    		rwLock.writeLock().unlock();
    	}
	}

    public void driveArcade(double speed, double rotate) {
    	drive.arcadeDrive(speed, rotate);
    }
    
    public double getLPower() {
    	return leftFrontCAN.get();
    }
    
    public double getRPower() {
    	return rightFrontCAN.get();
    }
}

