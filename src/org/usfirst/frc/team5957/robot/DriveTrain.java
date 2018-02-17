package org.usfirst.frc.team5957.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {
	private static VictorSP fL, rL, fR, rR;
	private static DifferentialDrive drive;
	private static ADXRS450_Gyro gyro;
	private static Solenoid gear;

	final double kP = 0.03;
	double targetAngle;
	final boolean oki = true;

	public DriveTrain(int frontLeft, int rearLeft, int frontRight, int rearRight, int PCMChannel, int gearChannel) {
		fL = new VictorSP(frontLeft);
		rL = new VictorSP(rearLeft);
		fR = new VictorSP(frontRight);
		rR = new VictorSP(rearRight);
		drive = new DifferentialDrive(new SpeedControllerGroup(fL, rL), new SpeedControllerGroup(fR, rR));
		gyro = new ADXRS450_Gyro();
		reset();
		gear = oki ? new Solenoid(PCMChannel, gearChannel) : null;
	}

	public void setCourse(double power, double angle) {

		targetAngle = angle;
		drive.arcadeDrive(power, getAutoCorrection());

	}

	public void drive(double power, double speed, double rotation) {
		drive.arcadeDrive(power * speed, power * rotation + getTeleopCorrectionPower(), true);
	}

	public void drive(double speed, double rotation) {
		drive(1, speed, rotation);
	}

	public void setGear(boolean highGear) {
		gear.set(highGear);
	}

	public void brake() {

		setCourse(0, 0);

	}

	public void reset() {

		gyro.reset();

	}

	private double getAutoCorrection() {

		return (targetAngle - gyro.getAngle() * kP);

	}

	private double getTeleopCorrectionPower() {

		return isTurning() ? 0 : (targetAngle - gyro.getAngle()) * kP;
	}

	private boolean isTurning() {

		return fL.get() != 0 && fL.get() != fR.get();

	}

}
