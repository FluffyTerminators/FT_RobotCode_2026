package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;

public class Passthrough {
    public static CRServo se_Pass;
    public static void passInit (CRServo passServo){
        se_Pass = passServo;
    }
    public static void runpass(){
         se_Pass.setPower(1);
    }
    public static void ejectpass(){
        se_Pass.setPower(-1);
    }
    public static void stoppass(){
        se_Pass.setPower(0);
    }
}
