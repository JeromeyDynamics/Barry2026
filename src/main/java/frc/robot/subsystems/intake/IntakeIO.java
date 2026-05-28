// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot.subsystems.intake;

import org.littletonrobotics.junction.AutoLog;

public interface IntakeIO {
  @AutoLog
  public static class IntakeIOInputs {
    public boolean connected = false;
    public double positionRad = 0.0;
    public double velocityRadPerSec = 0.0;
    public double appliedVolts = 0.0;
    public double currentAmps = 0.0;
  }

  /** 
   * Updates the set of loggable inputs.
   * 
   * @param inputs The set of loggable inputs to update.
  */
  public default void updateInputs(IntakeIOInputs inputs) {}

  /** 
   * Applies a specific voltage to the intake roller.
   * 
   * @param volts The voltage the intake roller gets set to.
  */
  public default void setVoltage(double volts) {}

  /** Stops the intake roller. */
  public default void stop() {
    setVoltage(0.0);
  }
}
