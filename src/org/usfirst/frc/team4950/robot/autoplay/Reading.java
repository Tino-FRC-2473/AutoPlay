package org.usfirst.frc.team4950.robot.autoplay;

public class Reading {
	private double leftPow;
	private double rightPow;
	private double gyro;
	private int leftEnc;
	private int rightEnc;
	private boolean gearMech;
	private boolean shooter;
	
	public Reading(double lp, double rp, double g, int le, int re, boolean gm, boolean s) {
		leftPow = lp;
		rightPow = rp;
		gyro = g;
		leftEnc = le;
		rightEnc = re;
		gearMech = gm;
		shooter = s;
	}

	public Reading() {
		this(0, 0, 0, 0, 0, false, false);
	}
	
	public double getLeftPow()		{	return leftPow;		}
	public double getRightPow()		{	return rightPow;	}
	public double getGyro()			{	return gyro;		}
	public int getLeftEnc()			{	return leftEnc;		}
	public int getRightEnc()		{	return rightEnc;	}
	public boolean getGearMech()	{	return gearMech;	}
	public boolean getShooter()		{	return shooter;	}
	
	@Override
	public String toString() {
		return leftPow + " " + rightPow + " " + gyro + " " + leftEnc + " " + rightEnc + " " + gearMech + " " + shooter;
	}
}
