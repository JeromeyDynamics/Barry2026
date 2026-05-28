// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot.subsystems.intake;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.system.plant.DCMotor;

public final class IntakeConstants {
  private IntakeConstants() {}

  public static final int kLeaderMotorID = 12;
  public static final int kFollowerMotorID = 19;

  public static final double kIntakeOutSpeed = -0.31;
  public static final double kIntakeInSpeed = 0.31;

  public static final double kMinOutput = -1.0;
  public static final double kMaxOutput = 1.0;
  public static final double kNominalVoltage = 12.0;

  public static final DCMotor ROLLER_GEARBOX = DCMotor.getKrakenX44Foc(2);
  public static final DCMotor PIVOT_GEARBOX = DCMotor.getKrakenX60Foc(1);

  public static final double ROLLER_GEAR_RATIO = 1.0;
  public static final double PIVOT_GEAR_RATIO = 60.0;

  public static final double ROLLER_MOI_KG_METERS_SQUARED = 0.004;
  public static final double PIVOT_LENGTH_METERS = 0.45;
  public static final double PIVOT_MASS_KG = 4.5;

  public static final Rotation2d PIVOT_MIN_ANGLE = Rotation2d.fromDegrees(-95.0);
  public static final Rotation2d PIVOT_MAX_ANGLE = Rotation2d.fromDegrees(0.0);
  public static final Rotation2d PIVOT_STOW_ANGLE = Rotation2d.fromDegrees(0.0);
  public static final Rotation2d PIVOT_DEPLOY_ANGLE = Rotation2d.fromDegrees(-55.0);
}
