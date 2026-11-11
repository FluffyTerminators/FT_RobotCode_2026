package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

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
        double testRev = 0;
        double p = 0;
        boolean pLast = false;
        double i = 0;
        boolean iLast = false;
        double d = 0;
        boolean dLast = false;
        double f = 0;
        boolean fLast = false;
        boolean shooting = false;
        boolean shotLast = false;

        telemetry.addData("Status;", "Initialised");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Status;", "Running");
            telemetry.update();

            if (gamepad1.left_bumper) {
                if (!resetLast) {
                    //imu.resetYaw();
                    pinpoint.setHeading(0, AngleUnit.DEGREES);
                    resetLast = true;
                }
            } else {
                resetLast = false;
            }
            pinpoint.update();

            double robotYaw = pinpoint.getHeading(AngleUnit.DEGREES);
            heading = Math.toRadians(pinpoint.getPosition().getHeading(AngleUnit.DEGREES) + Constants.Drive.headingOffset);

            telemetry.addData("PinPoint Status", pinpoint.getDeviceStatus());
            telemetry.addData("Heading Scalar", pinpoint.getYawScalar());
            telemetry.addData("Heading", Math.toDegrees(heading));
            if (gamepad1.b) {
                s_Drive.isFieldCentricMode(getRuntime());
            }

            if (gamepad1.y) {
                if (!pLast){
                    if (gamepad1.left_bumper){
                        p -= 0.1;
                    } else {
                        p += 0.1;
                    }
                    pLast = true;
                }
            } else {
                pLast = false;
            }

            if (gamepad1.b) {
                if (!iLast){
                    if (gamepad1.left_bumper){
                        i -= 0.1;
                    } else {
                        i += 0.1;
                    }
                    iLast = true;
                }
            } else {
                iLast = false;
            }

            if (gamepad1.a) {
                if (!dLast){
                    if (gamepad1.left_bumper){
                        d -= 0.1;
                    } else {
                        d += 0.1;
                    }
                    dLast = true;
                }
            } else {
                dLast = false;
            }

            if (gamepad1.x) {
                if (!fLast){
                    if (gamepad1.left_bumper){
                        f -= 0.1;
                    } else {
                        f += 0.1;
                    }
                    fLast = true;
                }
            } else {
                fLast = false;
            }

            telemetry.addData("P;", p);
            telemetry.addData("I;", i);
            telemetry.addData("D;", d);
            telemetry.addData("F;", f);

            PIDFCoefficients test = new PIDFCoefficients(p, i, d, f);

            necShooter.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, test);

            s_Drive.driveControl(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x,pinpoint.getHeading(AngleUnit.RADIANS));
            if (gamepad2.right_bumper) {
                shooting = true;
                s_Shooter.pollenRev(-860);
                pollen = true;
                if (s_Shooter.speedCheck(-860,pollen))/* && !shotLast)*/{
                    s_Passthrough.runPass();
                    shotLast = true;
                }
            } else {
                shooting = false;
                shotLast = true;
                s_Shooter.pollenstop();
            }
            telemetry.addData("polRev;", polShooter.getVelocity());
            if (gamepad2.left_bumper) {
                s_Shooter.necterRev(1500);
                pollen = false;
            } else {
                s_Shooter.nectarstop();
            }

            if (gamepad2.dpad_up) {
                s_Passthrough.runPass();
            } else if (gamepad2.dpad_down){
                s_Passthrough.ejectPass();
            } else if (!shooting){
                s_Passthrough.stopPass();
            }

            if (gamepad2.right_trigger > 0.1) {
                s_Intake.runIntake();
            } else if (gamepad2.a) {
                s_Intake.ejectIntake();
            } else {
                s_Intake.stopIntake();
            }
        }

    }
}


