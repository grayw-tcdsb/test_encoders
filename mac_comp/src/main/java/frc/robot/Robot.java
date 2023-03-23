// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class Robot extends TimedRobot {
  private final Joystick driveController = new Joystick(0);

  PWMSparkMax r1 = new PWMSparkMax(0);
  PWMSparkMax r2 = new PWMSparkMax(0);
  PWMSparkMax r3 = new PWMSparkMax(0);
  MotorControllerGroup rightSide = new MotorControllerGroup(r1, r2, r3); 

  PWMSparkMax l1 = new PWMSparkMax(0);
  PWMSparkMax l2 = new PWMSparkMax(0);
  PWMSparkMax l3 = new PWMSparkMax(0);
  MotorControllerGroup leftSide = new MotorControllerGroup(l1, l2, l3);

  private final DifferentialDrive drive = new DifferentialDrive(leftSide, rightSide);

  private final Timer clock = new Timer();

  Encoder encoder = new Encoder(5, 7, false, CounterBase.EncodingType.k4X);
  SendableChooser<Command> m_Chooser = new SendableChooser<>();
  
  public void driving (double speed, double turnRotation) {
    drive.arcadeDrive(speed,turnRotation);
  }

  @Override
  public void robotInit() {
    leftSide.setInverted(false);
    rightSide.setInverted(true);

    encoder.setSamplesToAverage(5);
    encoder.setDistancePerPulse(1.0 / 360.0 * 2.0 * Math.PI * 1.5);
    encoder.setMinRate(1.0);
  }

  @Override
  public void robotPeriodic() {
    // SmartDashboard.putData(m_Chooser);

  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void disabledExit() {}

  @Override
  public void autonomousInit() {
    clock.restart();
  }

  @Override
  public void autonomousPeriodic() {
    if (clock.get() < 5) {
      drive.arcadeDrive(kDefaultPeriod, kDefaultPeriod);
    } 
  }

  @Override
  public void autonomousExit() {}

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    driving(driveController.getY(), driveController.getX());

    SmartDashboard.putNumber("Encoder Distance", encoder.getDistance());
    SmartDashboard.putNumber("Encoder Rate", encoder.getRate());
  }

  @Override
  public void teleopExit() {}

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void testExit() {}
}
