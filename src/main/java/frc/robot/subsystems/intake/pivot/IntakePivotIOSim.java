// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot.subsystems.intake.pivot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import frc.robot.subsystems.intake.IntakeConstants;

public class IntakePivotIOSim implements IntakePivotIO {
  private final SingleJointedArmSim sim =
      new SingleJointedArmSim(
          IntakeConstants.PIVOT_GEARBOX,
          IntakeConstants.PIVOT_GEAR_RATIO,
          SingleJointedArmSim.estimateMOI(
              IntakeConstants.PIVOT_LENGTH_METERS, IntakeConstants.PIVOT_MASS_KG),
          IntakeConstants.PIVOT_LENGTH_METERS,
          IntakeConstants.PIVOT_MIN_ANGLE.getRadians(),
          IntakeConstants.PIVOT_MAX_ANGLE.getRadians(),
          true,
          IntakeConstants.PIVOT_STOW_ANGLE.getRadians());

  private final PIDController controller =
      new PIDController(IntakePivotConstants.kP, IntakePivotConstants.kI, IntakePivotConstants.kD);

  private boolean closedLoop = true;
  private double appliedVolts = 0.0;

  public IntakePivotIOSim() {
    controller.setSetpoint(IntakeConstants.PIVOT_STOW_ANGLE.getRadians());
  }

  @Override
  public void updateInputs(IntakePivotIOInputs inputs) {
    if (closedLoop) {
      appliedVolts =
          MathUtil.clamp(
              controller.calculate(sim.getAngleRads())
                  + IntakePivotConstants.kFF * IntakePivotConstants.kNominalVoltage,
              IntakePivotConstants.kMinOutput * IntakePivotConstants.kNominalVoltage,
              IntakePivotConstants.kMaxOutput * IntakePivotConstants.kNominalVoltage);
    }

    sim.setInputVoltage(MathUtil.clamp(appliedVolts, -12.0, 12.0));
    sim.update(0.02);

    inputs.connected = true;
    inputs.position = Rotation2d.fromRadians(sim.getAngleRads());
    inputs.velocityRadPerSec = sim.getVelocityRadPerSec();
    inputs.appliedVolts = appliedVolts;
    inputs.currentAmps = Math.abs(sim.getCurrentDrawAmps());
  }

  @Override
  public void setVoltage(double volts) {
    closedLoop = false;
    controller.reset();
    appliedVolts =
        MathUtil.clamp(
            volts,
            IntakePivotConstants.kMinOutput * IntakePivotConstants.kNominalVoltage,
            IntakePivotConstants.kMaxOutput * IntakePivotConstants.kNominalVoltage);
  }

  @Override
  public void setPosition(Rotation2d position) {
    closedLoop = true;
    controller.setSetpoint(
        MathUtil.clamp(
            position.getRadians(),
            IntakeConstants.PIVOT_MIN_ANGLE.getRadians(),
            IntakeConstants.PIVOT_MAX_ANGLE.getRadians()));
  }
}
