
package org.usfirst.frc.team4950.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Supplier;

import org.usfirst.frc.team4950.robot.autoplay.FlusherThread;
import org.usfirst.frc.team4950.robot.autoplay.Translator;
import org.usfirst.frc.team4950.robot.autoplay.UpdaterThread;
import org.usfirst.frc.team4950.robot.commands.ExampleCommand;
import org.usfirst.frc.team4950.robot.subsystems.ExampleSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	public static UpdaterThread updater;
	public static FlusherThread flusher;
	public static AnalogGyro gyro;
	SensorThread sense;
	PrintStream out;
	public static Translator translate;

	public static boolean isRecordingForAutoPlay = false;
	
	public static ArrayBlockingQueue<String> tempData;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		System.out.println("\n**************************************************");
		System.out.print("You are beginning to run AutoPlay, ");
		if(isRecordingForAutoPlay) {
			System.out.println("specifically the RECORDING part.");
		} else {
			System.out.println("specifically the REPLAYING part");
		}
		System.out.println("**************************************************");
		System.out.println("If you want to change what portion of AutoPlay to run,");
		System.out.println("change the isRecordingForAutoPlay boolean in Robot.");
		
		Map<String, Supplier<Command>> systemsMap = new HashMap<>();
		systemsMap.put("EXAMPLE_SUBSYSTEM", () -> exampleSubsystem.getCurrentCommand());
		
		sense = new SensorThread(10);
		
		if(isRecordingForAutoPlay) {
			tempData = new ArrayBlockingQueue<String>(200);
			
			ServerSocket server;
			try {
				System.out.println("**************************************************");
				System.out.println("You are currently trying to record for AutoPlay.");
				System.out.println("Start running the RecieverClient; robot code will remain red otherwise.");
				System.out.println("**************************************************");
				server = new ServerSocket(8080);
				Socket socket = server.accept(); //It will get stuck on this line
				System.out.println("**************************************************");
				System.out.println("Connected; enable TeleOp and twist the joystick in port 1.\n");
				System.out.println("**************************************************\n");
				OutputStream rawOut = socket.getOutputStream();
				out = new PrintStream(rawOut);
				updater = new UpdaterThread(systemsMap);
				flusher = new FlusherThread(out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("**************************************************");
			System.out.println("You are currently trying to AutoPlay from Moments.java");
			System.out.println("Ensure that you have ran the TranscriptionClient to create Moments.java from moments.txt");
			System.out.println("**************************************************\n");
			translate = new Translator();
		}
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		if(!isRecordingForAutoPlay)
			translate.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		
		sense.start();
		if(isRecordingForAutoPlay) {
			updater.start();
			flusher.start();
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
