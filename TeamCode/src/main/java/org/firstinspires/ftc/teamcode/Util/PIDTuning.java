package org.firstinspires.ftc.teamcode.Util;

import static com.qualcomm.robotcore.hardware.MotorControlAlgorithm.PIDF;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

/**
 * Centralizes the shooter PIDF coefficients so they can be tweaked live from Panels.
 * Call {@link #applyTo(DcMotorEx)} anywhere the shooter is used to push the latest values.
 */
public final class PIDTuning {

    /** Default values aligned with existing shooter PID settings; adjust live from Panels. */
    //public static double velocityKp = 20.0; // REV 2018-2019 PIDF Kp = 1.17
    public static double velocityKp = 45; // REV 2018-2019 PIDF Kp = 1.17
    //public static double velocityKi = 0.0;  // REV 2018-2019 PIDF Ki = 0.117
    public static double velocityKi = 0;  // REV 2018-2019 PIDF Ki = 0.117
    //public static double velocityKd = 0.0; // REV 2018-2019 PIDF Kd = 0.0
    public static double velocityKd = 10; // REV 2018-2019 PIDF Kd = 0.0
    //public static double velocityKf = 18.0;  // REV 2018-2019 PIDF Kf = 11.7
    public static double velocityKf = 17.8;  // REV 2018-2019 PIDF Kf = 11.7

    public static double PolVelocityKp = 52;
    public static double PolVelocityKi = 0;
    public static double PolVelocityKd = 13;
    public static double PolVelocityKf = 24;

    public static double NecVelocityKp = 55;
    public static double NecVelocityKi = 0;
    public static double NecVelocityKd = 1;
    public static double NecVelocityKf = 15.4;

    private static final double EPSILON = 1e-6;

    private PIDTuning() {
        // Utility class
    }

    /** Applies the currently configured PIDF values to the provided shooter motor. */
    public static boolean applyTo(DcMotorEx motor) {
        if (motor == null) {
            return false;
        }

        PIDFCoefficients desired = new PIDFCoefficients(velocityKp, velocityKi, velocityKd, velocityKf, PIDF);
        // Pull the live coefficients from the hub so we can recover if the hub resets mid‑match.
        PIDFCoefficients current = motor.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
        if (!coefficientsEqual(current, desired)) {
            // Use both velocity-specific and general setters to catch SDK/hub quirks.
            motor.setVelocityPIDFCoefficients(velocityKp, velocityKi, velocityKd, velocityKf);
            motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, desired);
        }
        current = motor.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
        return coefficientsEqual(current,desired);
    }

    /**
     * applyTo(DcMotorEx motor, double set) applies the PIDF values to the given motor
     * @param motor - The motor the values are applied to
     * @param set - Chooses the set of values to apply
     *            - 1; General Values
     *            - 2; ShooterFront Specific values
     *            - 3; ShooterBack Specific values
     **/
    public static boolean applyTo(DcMotorEx motor, double set) {
        if (motor == null) {
            return false;
        }
        boolean result = false;
        if (set == 1) {
            PIDFCoefficients desired = new PIDFCoefficients(velocityKp, velocityKi, velocityKd, velocityKf, PIDF);
            // Pull the live coefficients from the hub so we can recover if the hub resets mid‑match.
            PIDFCoefficients current = motor.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
            if (!coefficientsEqual(current, desired)) {
                // Use both velocity-specific and general setters to catch SDK/hub quirks.
                motor.setVelocityPIDFCoefficients(velocityKp, velocityKi, velocityKd, velocityKf);
                motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, desired);
            }
            current = motor.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
            result = coefficientsEqual(current,desired);
        } else if (set == 2) {
            PIDFCoefficients desired = new PIDFCoefficients(PolVelocityKp, PolVelocityKi, PolVelocityKd, PolVelocityKf, PIDF);
            // Pull the live coefficients from the hub so we can recover if the hub resets mid‑match.
            PIDFCoefficients current = motor.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
            if (!coefficientsEqual(current, desired)) {
                // Use both velocity-specific and general setters to catch SDK/hub quirks.
                motor.setVelocityPIDFCoefficients(PolVelocityKp, PolVelocityKi, PolVelocityKd, PolVelocityKf);
                motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, desired);
            }
            current = motor.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
            result = coefficientsEqual(current,desired);
        } else if (set == 3)
        {
            PIDFCoefficients desired = new PIDFCoefficients(NecVelocityKp, NecVelocityKi, NecVelocityKd, NecVelocityKf, PIDF);
            // Pull the live coefficients from the hub so we can recover if the hub resets mid‑match.
            PIDFCoefficients current = motor.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
            if (!coefficientsEqual(current, desired)) {
                // Use both velocity-specific and general setters to catch SDK/hub quirks.
                motor.setVelocityPIDFCoefficients(NecVelocityKp, NecVelocityKi, NecVelocityKd, NecVelocityKf);
                motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, desired);
            }
            current = motor.getPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER);
            result = coefficientsEqual(current,desired);
        }
        return result;
    }

    private static boolean coefficientsEqual(PIDFCoefficients a, PIDFCoefficients b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }

        return Math.abs(a.p - b.p) < EPSILON
                && Math.abs(a.i - b.i) < EPSILON
                && Math.abs(a.d - b.d) < EPSILON
                && Math.abs(a.f - b.f) < EPSILON
                && a.algorithm == b.algorithm;
    }
}
