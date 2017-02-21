package org.usfirst.frc.team4950.robot;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleSupplier;

import org.usfirst.frc.team4950.robot.Database.Value;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;


public class SensorThread extends Thread {
	//things to record
	AnalogGyro gyro;
	CANTalon leftEncoder, rightEncoder;
	
	//boolean that determines whether this thread runs
	private volatile boolean alive = true;
	
	long lastTime;
	double leftEncoderZero, rightEncoderZero;
	int delay;

	//a map with current values
	private Map<Database.Value, Double> tempMap;

	//a map of how each value is called
	private Map<Database.Value, DoubleSupplier> callMap;

	//initializes attributes and adds functions to callMap
	//resets encoders and sets feedback devices for encoders
	public SensorThread(int delay) {
		this.delay = delay;
		
		//add new sensors here
		this.gyro = Robot.gyro;
		this.leftEncoder = new CANTalon(RobotMap.leftBackMotor);
		this.rightEncoder = new CANTalon(RobotMap.rightBackMotor);

		leftEncoder.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		rightEncoder.setFeedbackDevice(FeedbackDevice.QuadEncoder);

		resetEncoders();

		tempMap = new HashMap<>();

		callMap = new HashMap<>();

		callMap.put(Value.LEFT_POWER, () -> Robot.driveTrain.getLPow());
		callMap.put(Value.RIGHT_POWER, () -> Robot.driveTrain.getRPow());
		callMap.put(Value.GYRO, () -> /*gyro.getAngle()*/0);
		callMap.put(Value.RIGHT_ENC, () -> (rightEncoder.getEncPosition()-rightEncoderZero) * Database.RIGHT_ENC_CONSTANT);
		callMap.put(Value.LEFT_ENC, () -> (-leftEncoder.getEncPosition()-leftEncoderZero) * Database.LEFT_ENC_CONSTANT);

		callMap = Collections.unmodifiableMap(callMap);
		super.setDaemon(true);
	}
	
	/**
	 * this method simulates the thread methods Thread.pause() and Thread.kill(). 
	 * It continuously polls sensors and then sleeps for delay length while alive and running.
	 * When it is not running it simply waits and stops running when it is not alive
	 */
	@Override
	public void run() {
		while (alive) {
			//updates sensors, joysticks, and buttons
			updateSensors();
			Robot.oi.updateJoysticks();
			Robot.oi.updateButtons();

			lastTime = System.currentTimeMillis();
			// Thread.yield();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		System.out.println("sensor dead");
	}

	//takes current values for each sensor and pushes them to Database
	private void updateSensors() {
		//snapshots a value for every sensor
		for (Database.Value v : callMap.keySet()) {
			tempMap.put(v, callMap.get(v).getAsDouble());
		}
		
		//push those values to the database
		for(Database.Value v : tempMap.keySet())
		{
			Database.getInstance().setValue(v, tempMap.get(v));
		}
	}
	

	/**
	 * kills this thread. It may run one last loop. Stops any future looping.
	 */
	public void kill() {
		alive = false;
		notify();
	}

	public boolean isDead() {
		return !alive;
	}

	public void resetEncoders() {
		//rightEncoder.setEncPosition(0);
		//leftEncoder.setEncPosition(0);
		
		leftEncoderZero = leftEncoder.getEncPosition();
		rightEncoderZero = rightEncoder.getEncPosition();
	}

	public void resetGyro() {
		gyro.reset();
	}
}
