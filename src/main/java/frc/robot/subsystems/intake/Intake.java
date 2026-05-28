// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot.subsystems.intake;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.intake.pivot.IntakePivotIO;
import frc.robot.subsystems.intake.pivot.IntakePivotIOInputsAutoLogged;
import org.littletonrobotics.junction.Logger;

public class Intake extends SubsystemBase {
  private final IntakeIO rollerIO;
  private final IntakePivotIO pivotIO;

  private final IntakeIOInputsAutoLogged rollerInputs = new IntakeIOInputsAutoLogged();
  private final IntakePivotIOInputsAutoLogged pivotInputs = new IntakePivotIOInputsAutoLogged();

  private final Alert rollerDisconnectedAlert =
      new Alert("Disconnected intake roller motor.", AlertType.kError);
  private final Alert pivotDisconnectedAlert =
      new Alert("Disconnected intake pivot motor.", AlertType.kError);

  public Intake(IntakeIO rollerIO, IntakePivotIO pivotIO) {
    this.rollerIO = rollerIO;
    this.pivotIO = pivotIO;
  }

  @Override
  public void periodic() {
    rollerIO.updateInputs(rollerInputs);
    pivotIO.updateInputs(pivotInputs);

    Logger.processInputs("Intake/Roller", rollerInputs);
    Logger.processInputs("Intake/Pivot", pivotInputs);

    rollerDisconnectedAlert.set(!rollerInputs.connected);
    pivotDisconnectedAlert.set(!pivotInputs.connected);
  }

  public void runRollers(double volts) {
    rollerIO.setVoltage(volts);
  }

  public void stopRollers() {
    rollerIO.stop();
  }

  public void runPivotVolts(double volts) {
    pivotIO.setVoltage(volts);
  }

  public void setPivotPosition(Rotation2d position) {
    pivotIO.setPosition(position);
  }

  public void stopPivot() {
    pivotIO.stop();
  }

  public void stop() {
    stopRollers();
    stopPivot();
  }

  public Rotation2d getPivotPosition() {
    return pivotInputs.position;
  }

  public Command runRollersCommand(double volts) {
    return Commands.startEnd(() -> runRollers(volts), this::stopRollers, this);
  }

  public Command setPivotPositionCommand(Rotation2d position) {
    return Commands.runOnce(() -> setPivotPosition(position), this);
  }

  public Command runPivotVoltsCommand(double volts) {
    return Commands.startEnd(() -> runPivotVolts(volts), this::stopPivot, this);
  }
}
