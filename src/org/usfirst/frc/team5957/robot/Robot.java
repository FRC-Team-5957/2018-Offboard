/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5957.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Robot extends IterativeRobot {

	DriveTrain drive;
	Compressor compressor;
	Joystick driver;
	Encoder left;
	Timer timer;
	final boolean oki = true;

	@Override
	public void robotInit() {

		if (oki) {
			drive = new DriveTrain(2, 3, 0, 1, 0, 0);
		} else {
			drive = new DriveTrain(0, 1, 2, 3, 0, 0);
		}
		driver = new Joystick(0);
		compressor = new Compressor(0);
		compressor.setClosedLoopControl(true);
		timer = new Timer();
		timer.reset();

		left = new Encoder(0, 1);

	}

	@Override
	public void autonomousInit() {
		timer.reset();
		drive.brake();
		Timer.delay(0.5);
		setDrive(90, 0.4);

		// Check to see that this works, must drive at least remotely straight
		// Follow Simbotics PID Tutorial to further tune adjustment

		// Can't drive and adjust from init method; driving will have to be done with
		// power/angle variable adjustment through periodic method
	}

	@Override
	public void autonomousPeriodic() {

		// drive does something

		// if (timer.get() == 0) {
		// timer.reset();
		// timer.start();
		// } else {
		// driveByTime(0.5, 0.01, 3);
		// turnByTime(-90, 3.01, 5);
		// driveByTime(0.5, 5.01, 7);
		// turnByTime(-90, 7.01, 9);
		// driveByTime(0.5, 9.01, 11);
		// turnByTime(-90, 11.01, 13);
		//
		// }
	}

	@Override
	public void teleopPeriodic() {

		if (driver.getRawButton(5)) {
			gear.set(true);
		} else if (driver.getRawButton(6)) {
			gear.set(false);
		}

		drive.arcadeDrive(-driver.getRawAxis(1), driver.getRawAxis(4), true);
	}

	@Override
	public void testPeriodic() {
	}

	// private void driveForward(double time) {
	// gyro.reset();
	// double angle = gyro.getAngle();
	// timer.reset();
	// timer.start();
	// while (timer.get() < time) {
	// drive.arcadeDrive(0.5, (angle - gyro.getAngle()) * kP);
	// }
	//
	// }
	//
	// private void turnToAngle(double angle) {
	// gyro.reset();
	// timer.reset();
	// while (gyro.getAngle() != angle) {
	// drive.arcadeDrive(0, 0.2 * (angle - gyro.getAngle()) * kP);
	// }
	// }
	//
	// private void driveByTime(double pow, double sT, double eT) {
	// gyro.reset();
	// double angle = gyro.getAngle();
	// while (timer.get() > sT && timer.get() < eT) {
	// drive.arcadeDrive(pow, (angle - gyro.getAngle()) * kP);
	// }
	// }
	//
	// private void turnByTime(double angle, double sT, double eT) {
	// gyro.reset();
	// while (timer.get() > sT && timer.get() < eT) {
	// if (angle < 0) {
	// drive.arcadeDrive(0, (angle - gyro.getAngle()) * kP, true);
	// } else {
	// drive.arcadeDrive(0, (angle - 180 + gyro.getAngle()) * kP, true);
	// }
	// }
	// }
	//
	// // Test on robot
	// boolean didIt = true;
	// boolean ready = true;
	// double autoSpeed = 0.5;
	// double turnSpeed = 0.75;
	//
	// private void reset() throws InterruptedException {
	// didIt = false;
	// if (didIt == false && left.getStopped()) {
	// didIt = true;
	// ready = true;
	// wait(100);
	// }
	// }
	//
	// private void forward(double t) {
	// if (ready == true) {
	// double angle = gyro.getAngle();
	// timer.reset();
	// timer.start();
	// }
	// while (timer.get() < t) {
	// // TODO finish
	// }
	// }

}
