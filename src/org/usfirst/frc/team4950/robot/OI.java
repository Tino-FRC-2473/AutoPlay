package org.usfirst.frc.team4950.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team4950.robot.commands.ExampleCommand;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import org.usfirst.frc.team4950.robot.Database.ButtonName;
import org.usfirst.frc.team4950.robot.Database.Value;
import org.usfirst.frc.team4950.robot.commands.ButtonCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	public static Joystick throttle = new Joystick(0);
	private Map<ButtonName, Boolean> tempButtonMap;// a map with a snapshot of
													// the values
	private Map<ButtonName, BooleanSupplier> buttonCallMap;// a map that takes
															// the button names
															// and associates
															// functions with
															// them
	private Map<Value, Double> joyMap;// a map with a snapshot of the values
	private Map<Value, DoubleSupplier> joyCallMap;// a map that maps joystick
													// names with the functions
													// to get their values

	public OI() {

		tempButtonMap = new HashMap<>();

		joyMap = new HashMap<>();

		buttonCallMap = new HashMap<>();

		// add the button calls here
		
		//EXAMPLE:
		//buttonCallMap.put(ButtonName.SAMPLE, () -> getThrottle().getRawButton(0));
		buttonCallMap.put(ButtonName.BUTTON, () -> getThrottle().getRawButton(0));

		buttonCallMap = Collections.unmodifiableMap(buttonCallMap);

		joyCallMap = new HashMap<>();

		// add joystick calls here
		joyCallMap.put(Value.LEFT_POWER, () -> getThrottle().getZ());

		joyCallMap = Collections.unmodifiableMap(joyCallMap);

		// Database.getInstance().getButton(ButtonName.TRIGGER).whenActive(new
		// ButtonTest());
		Database.getInstance().getButton(ButtonName.BUTTON).whenActive(new ButtonCommand());
	}

	public static Joystick getThrottle() {
		return throttle;
	}

	public void updateJoysticks() {

		// snapshots the current joystick
		for (Value v : joyCallMap.keySet()) {
			joyMap.put(v, joyCallMap.get(v).getAsDouble());
		}
		// pushes to the Database
		for (Value v : joyMap.keySet()) {
			Database.getInstance().setValue(v, joyMap.get(v));
		}
	}

	public void updateButtons() {
		// snapshots the current joystick
		for (ButtonName b : buttonCallMap.keySet()) {
			tempButtonMap.put(b, buttonCallMap.get(b).getAsBoolean());
		}
		// pushes to the Database
		for (ButtonName b : tempButtonMap.keySet()) {
			Database.getInstance().setButtonValue(b, tempButtonMap.get(b));
		}
	}
}
