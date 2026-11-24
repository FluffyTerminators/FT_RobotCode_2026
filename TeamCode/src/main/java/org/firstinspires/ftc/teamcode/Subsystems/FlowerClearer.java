package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class FlowerClearer {
    private Servo FWServo;
    double jitterPos = 0;
    public FlowerClearer(Servo FwServo){
        this.FWServo = FwServo;
    }
    public void FlowerUp(){
        FWServo.setPosition(0.44);
    }

    public void flowerJitter(double time)
    {
        jitterPos = (((int)(time / 0.2)) % 2) * 0.1;
        FWServo.setPosition(jitterPos);
    }
}
