/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5957.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends IterativeRobot {

	static DriveTrain drive;
	static Compressor compressor;
	static Joystick driver;
	static Encoder left;
	static Timer timer;
	final boolean oki = true;

	@Override
	public void robotInit() {

		if (oki) {
			drive = new DriveTrain(2, 3, 0, 1, 0, 0, driver);
		} else {
			drive = new DriveTrain(0, 1, 2, 3, 0, 0, driver);
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
		drive.setCourse(0.5, 0);
		Timer.delay(1.5);
		drive.brake();

		// Check to see that this works, must drive at least remotely straight
		// Follow Simbotics PID Tutorial to further tune adjustment
	}

	@Override
	public void autonomousPeriodic() {

	}

	@Override
	public void teleopPeriodic() {

		if (driver.getRawButton(5)) {
			drive.setGear(false);
		} else if (driver.getRawButton(6)) {
			drive.setGear(true);
		}

		drive.teleopDrive(-driver.getRawAxis(1), driver.getRawAxis(4));
	}

	@Override
	public void testPeriodic() {
	}

}
