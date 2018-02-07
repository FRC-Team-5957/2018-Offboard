/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5957.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Robot extends IterativeRobot {

	VictorSP frontLeft, rearLeft, frontRight, rearRight;
	DifferentialDrive drive;
	DoubleSolenoid gear;
	Compressor compressor;
	Joystick driver;

	@Override
	public void robotInit() {

		frontLeft = new VictorSP(0);
		rearLeft = new VictorSP(1);
		frontRight = new VictorSP(2);
		rearRight = new VictorSP(3);
		drive = new DifferentialDrive(new SpeedControllerGroup(frontLeft, rearLeft),
				new SpeedControllerGroup(frontRight, rearRight));
		gear = new DoubleSolenoid(0, 0, 1);
		driver = new Joystick(0);
		compressor = new Compressor(0);
		compressor.setClosedLoopControl(true);

	}

	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void teleopPeriodic() {

		if (driver.getRawButton(5)) {
			gear.set(DoubleSolenoid.Value.kForward);
		} else if (driver.getRawButton(6)) {
			gear.set(DoubleSolenoid.Value.kReverse);
		}

		drive.arcadeDrive(driver.getRawAxis(1), driver.getRawAxis(4), true);
	}

	@Override
	public void testPeriodic() {
	}
}
