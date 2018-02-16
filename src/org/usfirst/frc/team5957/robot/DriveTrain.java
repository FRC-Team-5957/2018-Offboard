package org.usfirst.frc.team5957.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {
	VictorSP fL, rL, fR, rR;
	DifferentialDrive drive;
	ADXRS450_Gyro gyro;

	final double kP = 0.03;
	double targetAngle;

	public DriveTrain(int frontLeft, int rearLeft, int frontRight, int rearRight, int PCMChannel, int gearChannel) {

		fL = new VictorSP(frontLeft);
		rL = new VictorSP(rearLeft);
		fR = new VictorSP(frontRight);
		rR = new VictorSP(rearRight);
		drive = new DifferentialDrive(new SpeedControllerGroup(fL, rL), new SpeedControllerGroup(fR, rR));
		gyro = new ADXRS450_Gyro();
		reset();

	}

	public void setCourse(double power, double angle) {

		targetAngle = angle;
		drive.arcadeDrive(power, getCorrectionPower());

	}

	private double getCorrectionPower() {
		return (targetAngle - gyro.getAngle()) * kP;
	}

	private boolean isTurning() {

	}

	public void brake() {

		setCourse(0, 0);

	}

	public void reset() {

		gyro.reset();

	}

}
