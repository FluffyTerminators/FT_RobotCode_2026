package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
;import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("FLDrive");
        c.frontRightName.set("FRDrive");
        c.backLeftName.set("BLDrive");
        c.backRightName.set("BRDrive");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });

    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("Pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(0.6146368642491619);
        c.yPodOffset.set(5.824467215951034);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.40032081289154736);
                Controller secondaryTranslationalForward = Controller.proportional(0.14790784359430106);
                Controller primaryTranslationalLateral = Controller.proportional(0.6094394920001718);
                Controller secondaryTranslationalLateral = Controller.proportional(0.22517160777092088);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.012762659347662627));
                c.brake.set(Controller.proportionalFeedforward(0.010848260445513232));

                c.headingFeedback.set(Controller.proportional(7.009446214021449));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.09296965961939382, 0.0014814663802951902));

                c.linearBrakeCoefficients.set(Matrix.diag(0.1482819204377962, 0.09716609420338493));
                c.quadraticBrakeCoefficients.set(Matrix.diag(9.546370607673539E-4, 0.0014124535535994043));

                c.maxAchievableForwardVelocity.set(68.99828605853938);
                c.maxAchievableStrafeVelocity.set(50.562875163875404);
                c.naturalForwardDeceleration.set(33.77957391162059);
                c.naturalStrafeDeceleration.set(79.95037603004037);
            }
    );
}
// commit issues