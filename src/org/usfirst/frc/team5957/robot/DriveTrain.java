package org.usfirst.frc.team5957.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {
	private VictorSP frontLeft, rearLeft, frontRight, rearRight;
	private DifferentialDrive drive;
	private ADXRS450_Gyro gyro;
	private Solenoid gear;
	private Joystick driver;
	private int rotationAxis;

	private static final double kP = 0.05; // TODO adjust kP until robot turning correctly.
	private double targetAngle;
	private final boolean oki = true;

	public DriveTrain(int frontLeft, int rearLeft, int frontRight, int rearRight, int PCMChannel, int gearChannel,
			Joystick driver) {
		this.frontLeft = new VictorSP(frontLeft);
		this.rearLeft = new VictorSP(rearLeft);
		this.frontRight = new VictorSP(frontRight);
		this.rearRight = new VictorSP(rearRight);
		drive = new DifferentialDrive(new SpeedControllerGroup(this.frontLeft, this.rearLeft),
				new SpeedControllerGroup(this.frontRight, this.rearRight));
		gyro = new ADXRS450_Gyro();
		reset();
		gear = oki ? new Solenoid(PCMChannel, gearChannel) : null;
		this.driver = driver == null ? null : driver;
	}

	public void setDriver(Joystick driver, int rotationAxis) {
		this.driver = driver;
		this.rotationAxis = rotationAxis;
	}

	// Gets power and angle and adjusts targets. Intnded for auto.
	public void setCourse(double power, double angle) {
		targetAngle = angle;
		drive.arcadeDrive(power, getAutoCorrection());
	}

	// Drive at power and get speed and rotation variables. Intended for teleop.
	public void teleopDrive(double power, double speed, double rotation) {
		drive.arcadeDrive(power * speed, power * rotation + getTeleopCorrection(), true);
	}

	// Drive with assumed power 100%
	public void teleopDrive(double speed, double rotation) {
		teleopDrive(1, speed, rotation);
	}

	public void setGear(boolean highGear) {
		gear.set(highGear);
	}

	// Stop driving, basically
	public void brake() {
		setCourse(0, 0);
	}

	// Reset sensor values. Just gyro on oki, encoders added on 2018-Temp-Name
	public void reset() {
		gyro.reset();
	}

	// Return turn correction value for autonomous. Always correcting.
	private double getAutoCorrection() {
		return (targetAngle - gyro.getAngle() * kP);
	}

	// * WIP * Return teleop correction. Returns 0 if robot is already turning.
	private double getTeleopCorrection() {
		return turning() ? 0 : (targetAngle - gyro.getAngle()) * kP;
	}

	// * WIP *
	private boolean turning() {
		return (frontLeft.get() != 0 && frontLeft.get() != frontRight.get()) && (driver.getRawAxis(rotationAxis) > 0);
	}

}
