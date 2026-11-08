package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Passthrough {
    public static DcMotorEx se_Pass;
    public Passthrough(DcMotorEx passServo)
    {
        se_Pass = passServo;
    }
    public static void runPass()
    {
         se_Pass.setPower(-0.7);
    }
    public static void ejectPass()
    {
        se_Pass.setPower(0.7);
    }
    public static void stopPass()
    {
        se_Pass.setPower(0);
    }
}
