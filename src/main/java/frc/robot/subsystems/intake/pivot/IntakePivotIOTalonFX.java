// Copyright (c) 2021-2026 Littleton Robotics
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by a BSD
// license that can be found in the LICENSE file
// at the root directory of this project.

package frc.robot.subsystems.intake.pivot;

import static frc.robot.util.PhoenixUtil.*;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.ParentDevice;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Voltage;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.intake.IntakeConstants;

public class IntakePivotIOTalonFX implements IntakePivotIO {
  private final TalonFX leader =
      new TalonFX(IntakePivotConstants.kLeaderID, TunerConstants.kCANBus);
  private final TalonFX follower =
      new TalonFX(IntakePivotConstants.kFollowerID, TunerConstants.kCANBus);

  private final VoltageOut voltageRequest = new VoltageOut(0.0);
  private final PositionVoltage positionRequest = new PositionVoltage(0.0);
  private final Follower followerRequest =
      new Follower(IntakePivotConstants.kLeaderID, MotorAlignmentValue.Aligned);

  private final StatusSignal<Angle> position = leader.getPosition();
  private final StatusSignal<AngularVelocity> velocity = leader.getVelocity();
  private final StatusSignal<Voltage> appliedVolts = leader.getMotorVoltage();
  private final StatusSignal<Current> current = leader.getStatorCurrent();

  private final Debouncer connectedDebounce = new Debouncer(0.5, Debouncer.DebounceType.kFalling);

  public IntakePivotIOTalonFX() {
    var config = new TalonFXConfiguration();
    config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
    config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
    config.Feedback.SensorToMechanismRatio = IntakeConstants.PIVOT_GEAR_RATIO;
    config.Slot0.kP = IntakePivotConstants.kP;
    config.Slot0.kI = IntakePivotConstants.kI;
    config.Slot0.kD = IntakePivotConstants.kD;
    config.Voltage.PeakForwardVoltage =
        IntakePivotConstants.kMaxOutput * IntakePivotConstants.kNominalVoltage;
    config.Voltage.PeakReverseVoltage =
        IntakePivotConstants.kMinOutput * IntakePivotConstants.kNominalVoltage;
    config.CurrentLimits.StatorCurrentLimit = 80.0;
    config.CurrentLimits.StatorCurrentLimitEnable = true;

    tryUntilOk(5, () -> leader.getConfigurator().apply(config, 0.25));
    tryUntilOk(5, () -> follower.getConfigurator().apply(config, 0.25));
    tryUntilOk(5, () -> leader.setPosition(IntakeConstants.PIVOT_STOW_ANGLE.getRotations(), 0.25));
    follower.setControl(followerRequest);

    BaseStatusSignal.setUpdateFrequencyForAll(50.0, position, velocity, appliedVolts, current);
    ParentDevice.optimizeBusUtilizationForAll(leader, follower);
  }

  @Override
  public void updateInputs(IntakePivotIOInputs inputs) {
    var status = BaseStatusSignal.refreshAll(position, velocity, appliedVolts, current);

    inputs.connected = connectedDebounce.calculate(status.isOK());
    inputs.position = Rotation2d.fromRotations(position.getValueAsDouble());
    inputs.velocityRadPerSec = Units.rotationsToRadians(velocity.getValueAsDouble());
    inputs.appliedVolts = appliedVolts.getValueAsDouble();
    inputs.currentAmps = current.getValueAsDouble();
  }

  @Override
  public void setVoltage(double volts) {
    leader.setControl(
        voltageRequest.withOutput(
            MathUtil.clamp(
                volts,
                IntakePivotConstants.kMinOutput * IntakePivotConstants.kNominalVoltage,
                IntakePivotConstants.kMaxOutput * IntakePivotConstants.kNominalVoltage)));
  }

  @Override
  public void setPosition(Rotation2d position) {
    leader.setControl(
        positionRequest
            .withPosition(
                MathUtil.clamp(
                    position.getRotations(),
                    IntakeConstants.PIVOT_MIN_ANGLE.getRotations(),
                    IntakeConstants.PIVOT_MAX_ANGLE.getRotations()))
            .withFeedForward(IntakePivotConstants.kFF * IntakePivotConstants.kNominalVoltage));
  }
}
