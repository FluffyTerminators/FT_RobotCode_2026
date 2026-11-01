package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Intake {
    private static DcMotor m_Intake;
    public static void intakeInit(DcMotor intakemoter) {
        m_Intake = intakemoter;
    }
    public static void runIntake()
    {
        m_Intake.setPower(1);
    }
    public static void stopIntake(){
        m_Intake.setPower(0);
    }
    //hello
}
