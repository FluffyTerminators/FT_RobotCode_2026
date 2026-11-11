package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class AutomaticShoot {

    public GoBildaPinpointDriver pinpoint;
    public double Angle;

    public AutomaticShoot(GoBildaPinpointDriver pinpoint) {
        this.pinpoint = pinpoint;
    }

    public void pointToPos(double x, double y) {

        double hypToPosition = Math.sqrt(Math.pow(x - pinpoint.getPosX(DistanceUnit.INCH), 2) + Math.pow(y - pinpoint.getPosY(DistanceUnit.INCH), 2)); // Calculates the X and Y difference in Position to the center of the goal, then uses Pythagoras Theorem to return the distance to the speed calculator
        double adjacentToPosition = (x - pinpoint.getPosX(DistanceUnit.INCH));

        Angle = Math.toDegrees(Math.asin(adjacentToPosition / hypToPosition));
    }

    public void shootRedLeft() // Shoots to Left red goal, as per the driver
    {


    }

    public void shootRedRight() // Shoots to Right red goal, as per the driver
    {

    }

    public void shootBlueLeft() // Shoots to Left blue goal, as per the driver
    {

    }

    public void shootBlueRight() // Shoots to Right red goal, as per the driver
    {

    }

}