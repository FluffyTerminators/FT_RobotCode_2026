package org.firstinspires.ftc.teamcode.Util;

public class UtilFunctions {
    public static double deadZone (double value){
        if (Math.abs(value) < Constants.Controls.deadband) {return 0.0;} else {return value;}
    }

    public static double clamp(double input, double min, double max){
        if (input < min)
        {return min;}
        else if (input > max)
        {return max;}
        else
        {return input;}
    }
}
