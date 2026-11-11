package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Passthrough {
    public DcMotorEx se_Pass;
    public Passthrough(DcMotorEx passServo)
    {
        se_Pass = passServo;
    }
    public void runPass()
    {
         se_Pass.setPower(1);
    }
    public void ejectPass()
    {
        se_Pass.setPower(1);
    }
    public void stopPass()
    {
        se_Pass.setPower(0);
    }
}
