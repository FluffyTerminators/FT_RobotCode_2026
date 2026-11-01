package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Intake {
    private static DcMotor m_Intake;
    public static void intakeInit(DcMotor intakeMotor)
    {
        m_Intake = intakeMotor;
    }
    public static void runIntake()
    {
        m_Intake.setPower(1);
    }
    public static void stopIntake()
    {
        m_Intake.setPower(0);
    }
}
