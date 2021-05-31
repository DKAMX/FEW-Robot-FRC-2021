// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /*
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  */
  /*
  WPI_TalonFX leftfront = new WPI_TalonFX(10);
  WPI_TalonFX leftrear = new WPI_TalonFX(11);
  WPI_TalonFX rightfront = new WPI_TalonFX(12);
  WPI_TalonFX rightrear = new WPI_TalonFX(13);
  DifferentialDrive chassis = new DifferentialDrive(leftfront, rightfront);
  */
  Joystick gamepad = new Joystick(0);

  ACCFRCTalon chassis = new ACCFRCTalon(10, 11, 12, 13);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    /*
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    */
    /*
    leftfront.configFactoryDefault();
    leftrear.configFactoryDefault();
    rightfront.configFactoryDefault();
    rightrear.configFactoryDefault();
    leftrear.follow(leftfront);
    rightrear.follow(rightfront);
    leftfront.setInverted(TalonFXInvertType.Clockwise);
    rightfront.setInverted(TalonFXInvertType.CounterClockwise);
    leftrear.setInverted(InvertType.FollowMaster);
    rightrear.setInverted(InvertType.FollowMaster);
    chassis.setRightSideInverted(false);
    */
    //12.4583 * 2048 = 25,514.5984
    chassis.chassisInit();
    chassis.setupAutomation(25514, 6);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    /*
    double position = leftfront.getSelectedSensorPosition(0);
    SmartDashboard.putNumber("Position", position);
    */
    SmartDashboard.putNumber("position", chassis.telemetry());
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    /*
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    */
    chassis.encoderReset();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    /*
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
    */
    //chassis.encoderDrive(0.5, 100);
    chassis.encoderDrive(0.5, -100);
    //chassis.test();
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    //leftfront.setSelectedSensorPosition(0);
    chassis.encoderReset();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    /*
    double power = 1;
    chassis.arcadeDrive(gamepad.getRawAxis(1) * power, -gamepad.getRawAxis(0) * power * 0.6);
    */
    chassis.driveArcade(gamepad.getRawAxis(1), -gamepad.getRawAxis(0), 0.3);
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
