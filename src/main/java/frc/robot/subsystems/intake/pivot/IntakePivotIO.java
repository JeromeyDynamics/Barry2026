// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot.subsystems.intake.pivot;

import edu.wpi.first.math.geometry.Rotation2d;
import org.littletonrobotics.junction.AutoLog;

public interface IntakePivotIO {
  @AutoLog
  public static class IntakePivotIOInputs {
    public boolean connected = false;
    public Rotation2d position = Rotation2d.kZero;
    public double velocityRadPerSec = 0.0;
    public double appliedVolts = 0.0;
    public double currentAmps = 0.0;
  }

  /** 
   * Updates the set of loggable inputs.
   * 
   * @param inputs The set of loggable inputs to update.
  */
  public default void updateInputs(IntakePivotIOInputs inputs) {}

  /** 
   * Applies a specific voltage to the intake pivot.
   * 
   * @param volts The voltage the intake pivot gets set to.
  */
  public default void setVoltage(double volts) {}

  /**
   * Sets the position of the intake pivot.
   * @param position
   */
  public default void setPosition(Rotation2d position) {}

  public default void stop() {
    setVoltage(0.0);
  }
}
