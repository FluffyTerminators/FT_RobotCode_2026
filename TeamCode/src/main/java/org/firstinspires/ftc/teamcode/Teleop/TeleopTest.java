package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Passthrough;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Util.Constants;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "BioTele")
public class TeleopTest extends LinearOpMode {
    public DcMotor bLDrive;
    public DcMotor bRDrive;
    public DcMotor fLDrive;
    public DcMotor fRDrive;
    public DcMotorEx intake;
    public DcMotorEx polShooter;
    public DcMotorEx necShooter;
    public CRServo passthrough;
    public GoBildaPinpointDriver pinpoint;
    public Drive s_Drive;
    public Intake s_Intake;
    public Passthrough s_Passthrough;
    public Shooter s_Shooter;

    public void runOpMode() throws InterruptedException{
        bLDrive = hardwareMap.get(DcMotor.class, "BLDrive");
        bRDrive = hardwareMap.get(DcMotor.class, "BRDrive");
        fLDrive = hardwareMap.get(DcMotor.class, "FLDrive");
        fRDrive = hardwareMap.get(DcMotor.class, "FRDrive");
        intake = hardwareMap.get(DcMotorEx.class, "Intake");
        polShooter = hardwareMap.get(DcMotorEx.class, "PollenShooter");
        necShooter = hardwareMap.get(DcMotorEx.class, "NectarShooter");
        passthrough = hardwareMap.get(CRServo.class, "Passthrough");
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "Pinpoint");

        s_Drive = new Drive(fLDrive, fRDrive, bLDrive, bRDrive);
        s_Intake = new Intake(intake);
        s_Passthrough = new Passthrough(passthrough);
        s_Shooter = new Shooter(polShooter, necShooter);

        pinpoint.setOffsets(100, -25, DistanceUnit.MM); //these are tuned for 3110-0002-0001 Product Insight #1
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.setHeading(0, AngleUnit.DEGREES);
        pinpoint.update();

        boolean resetLast = false;
        double heading;
        boolean pollen = true;

        telemetry.addData("Status;", "Initialised");
        telemetry.update();
        waitForStart();

        while (opModeIsActive())
        {
            telemetry.addData("Status;", "Running");
            telemetry.update();

            if (gamepad1.left_bumper) {
                if (!resetLast) {
                    //imu.resetYaw();
                    pinpoint.setHeading(0, AngleUnit.DEGREES);
                    resetLast = true;
                }
            } else { resetLast = false; }
            pinpoint.update();

            double robotYaw = pinpoint.getHeading(AngleUnit.DEGREES);
            heading = Math.toRadians(pinpoint.getPosition().getHeading(AngleUnit.DEGREES) + Constants.Drive.headingOffset);

            telemetry.addData("PinPoint Status", pinpoint.getDeviceStatus());
            telemetry.addData("Heading Scalar", pinpoint.getYawScalar());
            telemetry.addData("Heading", Math.toDegrees(heading));
            if (gamepad1.b){
                Drive.isFieldCentricMode(getRuntime());
            }
            Drive.driveControl(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
            if (gamepad2.right_bumper){
                Shooter.pollenRev(500);
                pollen = true;
            }
            if (gamepad2.left_bumper){
                Shooter.necterRev(500);
                pollen = false;
            }
            if (gamepad2.right_bumper || gamepad2.left_bumper)
            {
             if (Shooter.speedCheck(500,pollen)){
                 Passthrough.runPass();
             }
            } else {
                Passthrough.stopPass();
            }

            if (gamepad2.right_trigger > 0)
            {
                Intake.runIntake();
            } else {
                Intake.stopIntake();
            }
        }

    }
}

