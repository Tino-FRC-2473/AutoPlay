package org.usfirst.frc.team4950.robot;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleSupplier;

import org.usfirst.frc.team4950.robot.Database.Value;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.AnalogGyro;

public class SensorThread extends Thread {

	// add new sensors here
	AnalogGyro gyro;
	CANTalon leftEncoder; // rightEncoder;
	private volatile boolean alive = true;
	long lastTime;
	int delay;

	private Map<Database.Value, Double> tempMap;

	// a map of how each value is called
	private Map<Database.Value, DoubleSupplier> callMap;

	public SensorThread(int delay) {

		leftEncoder = Robot.exampleSubsystem.leftMotor;

		this.delay = delay;

		// add new sensors here
		this.gyro = Robot.gyro;

		//leftEncoder.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		//rightEncoder.setFeedbackDevice(FeedbackDevice.QuadEncoder);

		resetEncoders();

		tempMap = new HashMap<>();

		callMap = new HashMap<>();

		// add the sensor name in the Values enum and the method of the sensor
		// that returns the sensor value.
		//callMap.put(Value.LEFT_ENCODER, () -> leftEncoder.getEncPosition());
		// callMap.put(Value.RIGHT_ENCODER, () ->
		// rightEncoder.getEncPosition());
		callMap.put(Value.LEFT_POWER, () -> Robot.exampleSubsystem.leftMotor.get());
		// callMap.put(Value.RIGHT_POWER, () -> Robot.exampleSubsystem.rightMotor.get().get());
		//callMap.put(Value.GYRO, () -> gyro.getAngle());//

		callMap = Collections.unmodifiableMap(callMap);
		super.setDaemon(true);
	}

	/**
	 * this method simulates the thread methods Thread.pause() and
	 * Thread.kill(). It continuously polls sensors and then sleeps for delay
	 * length while alive and running. When it is not running it simply waits
	 * and stops running when it is not alive
	 */

	@Override
	public void run() {
		while (alive) {

			// System.out.println(System.currentTimeMillis() - lastTime);
			
			updateSensors();
			Robot.oi.updateButtons();
			//System.out.println();

			lastTime = System.currentTimeMillis();
			// Thread.yield();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		System.out.println("dead");
	}

	private void updateSensors() {
		// snapshots a value for every sensor
		for (Database.Value v : callMap.keySet()) {
			tempMap.put(v, callMap.get(v).getAsDouble());//
		}

		// push those values to the database
		for (Database.Value v : tempMap.keySet()) {
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
		// rightEncoder.setEncPosition(0);
		leftEncoder.setEncPosition(0);
	}

	public void resetGyro() {
		gyro.reset();
	}
}
