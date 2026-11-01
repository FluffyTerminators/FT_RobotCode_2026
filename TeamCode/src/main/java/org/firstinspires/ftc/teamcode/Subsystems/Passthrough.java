package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;

public class Passthrough {
    public static CRServo se_Pass;
    public Passthrough(CRServo passServo)
    {
        se_Pass = passServo;
    }
    public static void runPass()
    {
         se_Pass.setPower(1);
    }
    public static void ejectPass()
    {
        se_Pass.setPower(-1);
    }
    public static void stopPass()
    {
        se_Pass.setPower(0);
    }
}
