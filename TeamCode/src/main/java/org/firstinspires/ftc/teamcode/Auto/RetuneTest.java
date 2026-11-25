package org.firstinspires.ftc.teamcode.Auto;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import static com.pedropathing.api.Paths.*;
import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.*;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;
@Autonomous(name = "RetuneTest", group = "Autonomous")
public class RetuneTest extends LinearOpMode{

    /// //
    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose hiveStart = poseFactory.of(59, 8.75, 270);
    private final Pose leaveWall = poseFactory.of(59, 15, 270);
    private final Pose strafetoSideWall = poseFactory.of(9, 15, 270);
    private final Pose intakefromGarden = poseFactory.of(9, 8.75, 270);
    private final Pose gardentoPark = poseFactory.of(14.5, 95, 270);
    private final Pose gardentoParkControl1 = poseFactory.of(40, 38, 0);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, leaveWall())
               // follow(follower, strafetoSideWall()),
                //follow(follower, intakefromGarden()),
                //follow(follower, gardentoPark())
        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(hiveStart);
        follower.update();

        waitForStart();
        schedule(autoRoutine());

        while (opModeIsActive()) {
            follower.update();
            Scheduler.execute();

            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("heading", follower.pose().heading());

            if (follower.currentPath() != null) {
                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }

    public Path leaveWall() {
        return Paths.line(hiveStart, leaveWall).constant(leaveWall);
    }

    public Path strafetoSideWall() {
        return Paths.line(leaveWall, strafetoSideWall).constant(strafetoSideWall);
    }

    public Path intakefromGarden() {
        return Paths.line(strafetoSideWall, intakefromGarden).constant(intakefromGarden);
    }

    public Path gardentoPark() {
        return Paths.curve(intakefromGarden, gardentoParkControl1, gardentoPark).constant(gardentoPark);
    }
}
