package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Util.Constants;

public class Passthrough {
    public DcMotorEx se_Pass;
    public Passthrough(DcMotorEx passServo)
    {
        se_Pass = passServo;
    }
    public void runPass()
    {
         se_Pass.setPower(-Constants.Passthrough.passthrough_Power);
    }
    public void ejectPass()
    {
        se_Pass.setPower(Constants.Passthrough.passthrough_Power);
    }
    public void stopPass()
    {
        se_Pass.setPower(0);
    }
}
