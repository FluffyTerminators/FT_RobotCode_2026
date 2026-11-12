package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.Servo;

public class FlowerClearer {
    private Servo FWServo;
    public FlowerClearer(Servo FwServo){
        this.FWServo = FwServo;
    }
    public void FlowerUp(){
        FWServo.setPosition(0.44);
    }
    public void FlowerDown(){
        FWServo.setPosition(0);
    }

}
