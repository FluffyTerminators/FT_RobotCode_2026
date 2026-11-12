package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Passthrough;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Util.Constants;

@TeleOp(name = "BioTest")
public class Test extends LinearOpMode {
    public Servo FwServo;
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
        FwServo = hardwareMap.get(Servo.class, "FWServo");

        s_Drive = new Drive(fLDrive, fRDrive, bLDrive, bRDrive);
        s_Intake = new Intake(intake);
        s_Passthrough = new Passthrough(passthrough);
        s_Shooter = new Shooter(polShooter, necShooter);

        pinpoint.setOffsets(100, -25, DistanceUnit.MM); //these are tuned for 3110-0002-0001 Product Insight #1
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.setHeading(0, AngleUnit.DEGREES);
        pinpoint.update();
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                polShooter.setPower(1);
            } else {
                polShooter.setPower(0);
            }

            if (gamepad1.b) {
                necShooter.setPower(1);
            } else {
                necShooter.setPower(0);
            }
             FwServo.setPosition(gamepad2.left_stick_x);
            telemetry.addData("Servopos", FwServo.getPosition());
            telemetry.update();
        }

    }
}


