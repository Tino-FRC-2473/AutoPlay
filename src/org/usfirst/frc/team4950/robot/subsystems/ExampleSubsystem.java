package org.usfirst.frc.team4950.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.usfirst.frc.team4950.robot.Database;
import org.usfirst.frc.team4950.robot.Robot;
import org.usfirst.frc.team4950.robot.RobotMap;
import org.usfirst.frc.team4950.robot.commands.ExampleCommand;

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
public class ExampleSubsystem extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public static CANTalon leftMotor;
	private static double power;
	private static ReentrantReadWriteLock readWriteLock;
	public ExampleSubsystem() {
		leftMotor = new CANTalon(RobotMap.leftMotor);
		power = 0;
		readWriteLock = new ReentrantReadWriteLock();
	}
	public void initDefaultCommand() {
		if(Robot.isRecordingForAutoPlay)
			setDefaultCommand(new ExampleCommand());
	}
	
	public void setPower(double p) {
		leftMotor.set(p);
	}
	public static void autoPower() {
		readWriteLock.readLock().lock();
		try {
			leftMotor.set(power);
		}
		finally {
			readWriteLock.readLock().unlock();
		}
	}
	
	public static void power(double p) {
		readWriteLock.writeLock().lock();
		try {
			power = p;
		}
		finally {
			readWriteLock.writeLock().unlock();
		}
	}
}
