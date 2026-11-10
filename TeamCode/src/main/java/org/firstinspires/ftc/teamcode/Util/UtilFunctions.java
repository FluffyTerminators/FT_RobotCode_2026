package org.firstinspires.ftc.teamcode.Util;

public class UtilFunctions {

    public static double deadZone (double value){
        if (Math.abs(value) < 0.05) {return 0.0;} else {return value;}
    }
}
