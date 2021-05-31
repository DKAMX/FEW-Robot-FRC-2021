// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.drive.*;
//specialized for talon fx, can change to other motor controller
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

/** Add your docs here. */
public class ACCFRCTalon {
    WPI_TalonFX leftfront;
    WPI_TalonFX leftrear;
    WPI_TalonFX rightfront;
    WPI_TalonFX rightrear;
    DifferentialDrive chassis;

    double counts_per_round, wheel_diameter_inches, counts_per_cm, tolerance;

    public ACCFRCTalon(int lf_id, int lr_id, int rf_id, int rr_id) {
        leftfront = new WPI_TalonFX(lf_id);
        leftrear = new WPI_TalonFX(lr_id);
        rightfront = new WPI_TalonFX(rf_id);
        rightrear = new WPI_TalonFX(rr_id);
        chassis = new DifferentialDrive(leftfront, rightfront);
    }

    public void chassisInit() {
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
        encoderReset();
    }

    public void setupAutomation(double ticks, double diameter) {
        counts_per_round = ticks;
        wheel_diameter_inches = diameter;
        counts_per_cm = ticks / (diameter * 2.54 * 3.1415);
        tolerance = counts_per_round * 0.1;
    }

    public void driveTank(double left, double right, double power) {
        chassis.tankDrive(left * power, right * power * 0.8);
    }

    public void driveArcade(double move, double turn, double power) {
        chassis.arcadeDrive(move * power, turn * power * 0.8);
    }

    public void encoderReset() {
        leftfront.setSelectedSensorPosition(0);
    }

    public void encoderDrive(double power, double distance) {
        double target = leftfront.getSelectedSensorPosition() - distance * counts_per_cm;
        while (Math.abs(target - leftfront.getSelectedSensorPosition()) > tolerance) {
            if ((target - leftfront.getSelectedSensorPosition()) < 0) {
                double move = (target - leftfront.getSelectedSensorPosition()) * 0.0001 - 0.3;
                driveArcade(move, 0, power);
            } else {
                double move = (target - leftfront.getSelectedSensorPosition()) * 0.0001 + 0.3;
                driveArcade(move, 0, power);
            }
        }
        driveArcade(0, 0, power);
    }

    public void turnRight() {}

    public void turnLeft() {}

    public double telemetry() {
        return leftfront.getSelectedSensorPosition();
    }

    public void test() {
        while (leftfront.getSelectedSensorPosition() > -25514) {
            driveArcade(-0.3, 0, 1);
        }
    }
}
