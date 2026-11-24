package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Subsystems.AutomaticShoot;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Passthrough;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Util.Constants;

@Autonomous(name = "Wait-Park")
public class WaitPark extends LinearOpMode {
    public DcMotor bLDrive;
    public DcMotor bRDrive;
    public DcMotor fLDrive;
    public DcMotor fRDrive;
    public DcMotorEx intake;
    public DcMotorEx polShooter;
    public DcMotorEx necShooter;
    public DcMotorEx passthrough;
    public GoBildaPinpointDriver pinpoint;
    public Drive s_Drive;
    public Intake s_Intake;
    public Passthrough s_Passthrough;
    public Shooter s_Shooter;
    public AutomaticShoot s_AutoShoot;

    public void runOpMode() throws InterruptedException {
        bLDrive = hardwareMap.get(DcMotor.class, "BLDrive");
        bRDrive = hardwareMap.get(DcMotor.class, "BRDrive");
        fLDrive = hardwareMap.get(DcMotor.class, "FLDrive");
        fRDrive = hardwareMap.get(DcMotor.class, "FRDrive");
        intake = hardwareMap.get(DcMotorEx.class, "Intake");
        polShooter = hardwareMap.get(DcMotorEx.class, "PollenShooter");
        necShooter = hardwareMap.get(DcMotorEx.class, "NectarShooter");
        passthrough = hardwareMap.get(DcMotorEx.class, "Passthrough");
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "Pinpoint");

        s_Drive = new Drive(fLDrive, fRDrive, bLDrive, bRDrive, pinpoint);
        s_Intake = new Intake(intake);
        s_Passthrough = new Passthrough(passthrough);
        s_Shooter = new Shooter(polShooter, necShooter);
        s_AutoShoot = new AutomaticShoot(pinpoint);

        s_Drive.autoFieldCentric();

        pinpoint.setOffsets(100, -25, DistanceUnit.MM); //these are tuned for 3110-0002-0001 Product Insight #1
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.setHeading(0, AngleUnit.DEGREES);
        pinpoint.update();
        double heading = pinpoint.getHeading(AngleUnit.DEGREES);

        //  while(!(s_Drive.driveToRelativePosition(new Pose2D(DistanceUnit.INCH,10,10,AngleUnit.DEGREES,0)))){}
        waitForStart();
        double starttime = getRuntime();
        s_Drive.driveControl(0, 0, 0, 0);

        while (getRuntime() < starttime + 20) ;
        s_Drive.driveControl(0, 0, 0, 0);

        starttime = getRuntime();
        s_Drive.driveControl(-1, 0, 0, 0);

        while (getRuntime() < starttime + 0.1) ;
        s_Drive.driveControl(0, 0, 0, 0);

        // shoot
        starttime = getRuntime();
        s_Shooter.pollenRev(Constants.Shooter.pollen_shooter_speed);
        //for (int i = 0; i < 3; i++) {
        while (!s_Shooter.speedCheck(Constants.Shooter.pollen_shooter_speed, true) &&
                (getRuntime() < starttime + Constants.Shooter.max_auto_shoot)) {
            s_Shooter.pollenRev(Constants.Shooter.pollen_shooter_speed);
            s_Passthrough.stopPass();
        }
        while (getRuntime() < starttime + Constants.Shooter.max_auto_shoot) {
            s_Shooter.pollenRev(Constants.Shooter.pollen_shooter_speed);
            if (s_Shooter.speedCheck(Constants.Shooter.pollen_shooter_speed, true)) {
                s_Passthrough.runPass();
            } else {
                s_Passthrough.stopPass();
            }
        }
        starttime = getRuntime();
        s_Drive.driveControl(-1, 0, 0, 0);

        while (getRuntime() < starttime + 0.4) ;
        s_Drive.driveControl(0, 0, 0, 0);

        starttime = getRuntime();
        s_Drive.driveControl(0, -1, 0, 0);

        while (getRuntime() < starttime + 0.95) ;
        s_Drive.driveControl(0, 0, 0, 0);
    }
}
