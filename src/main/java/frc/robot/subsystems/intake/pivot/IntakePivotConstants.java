// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot.subsystems.intake.pivot;

public final class IntakePivotConstants {
  private IntakePivotConstants() {}

  public static final int kLeaderID = 11;
  public static final int kFollowerID = 10;

  public static final double kPivotSpeedDown = 0.12;
  public static final double kPivotSpeedUp = -0.18;

  public static final double kP = 0.07;
  public static final double kI = 0.0;
  public static final double kD = 0.0;
  public static final double kFF = 0.00375;

  public static final double kMinOutput = -0.4;
  public static final double kMaxOutput = 0.4;

  public static final double kNominalVoltage = 12.0;
}
