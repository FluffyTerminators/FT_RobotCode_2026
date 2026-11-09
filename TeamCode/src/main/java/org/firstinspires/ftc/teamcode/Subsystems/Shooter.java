package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.Util.Constants;

public class Shooter {
    public DcMotorEx m_shooterPol;
    public DcMotorEx m_shooterNec;
    private double shooterSpeed;
    private boolean shooterReady;
    public Shooter(DcMotorEx pollenMotor, DcMotorEx necterMotor)
    {
        m_shooterPol = pollenMotor;
        m_shooterNec = necterMotor;
    }
    public void pollenstop(){
        m_shooterPol.setPower(0);
    }
    public void nectarstop(){
        m_shooterNec.setPower(0);
    }
    public void pollenRev(double revspeed)
    {
        m_shooterPol.setVelocity(revspeed);
    }
    public void necterRev(double revspeed)
    {
        m_shooterNec.setVelocity(revspeed);
    }
    public void pollenEject()
    {
        m_shooterPol.setPower(1);
    }
    public void necterEject()
    {
        m_shooterNec.setPower(-1);
    }
    public boolean speedCheck(double shooterTarget, boolean pollen)
    {
        if (pollen)
        {
        shooterSpeed = m_shooterPol.getVelocity();
        } else
        {
            shooterSpeed = m_shooterNec.getVelocity();
        }

        if ((shooterSpeed > (shooterTarget - Constants.Shooter.shooter_speed_tolerance))
                && (shooterSpeed <(shooterTarget + Constants.Shooter.shooter_speed_tolerance)))
        {
            shooterReady = true;
        } else
        {
            shooterReady = false;
        }

        return shooterReady;
    }
}
