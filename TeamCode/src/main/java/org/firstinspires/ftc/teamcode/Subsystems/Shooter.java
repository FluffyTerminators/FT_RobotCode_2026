package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Util.Constants;

public class Shooter {
    public static DcMotorEx m_shooterPol;
    public static DcMotorEx m_shooterNec;
    private static double shooterSpeed;
    private static boolean shooterready;
    public Shooter(DcMotorEx pollenMotor, DcMotorEx necterMotor)
    {
        m_shooterPol = pollenMotor;
        m_shooterNec = necterMotor;
    }
    public static void pollenRev(double revspeed)
    {
        m_shooterPol.setVelocity(revspeed);
    }
    public static void necterRev(double revspeed)
    {
        m_shooterNec.setVelocity(revspeed);
    }
    public static boolean speedCheck(double shooterTarget, boolean pollen)
    {
        if (pollen)
        {
        shooterSpeed = m_shooterPol.getVelocity();
        } else
        {
            shooterSpeed = m_shooterNec.getVelocity();
        }

        if ((shooterSpeed > (shooterTarget - Constants.shooter.shooter_speed_tolerance))
                && (shooterSpeed <(shooterTarget + Constants.shooter.shooter_speed_tolerance)))
        {
            shooterready = true;
        } else
        {
            shooterready = false;
        }

        return shooterready;
    }
}
