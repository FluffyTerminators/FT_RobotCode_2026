package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Intake {
    private DcMotorEx m_Intake;
    public Intake(DcMotorEx intakeMotor)
    {
        m_Intake = intakeMotor;
    }
    public void runIntake()
    {
        m_Intake.setPower(1);
    }
    public void ejectIntake()
    {
        m_Intake.setPower(-1);
    }
    public void stopIntake()
    {
        m_Intake.setPower(0);
    }
}
