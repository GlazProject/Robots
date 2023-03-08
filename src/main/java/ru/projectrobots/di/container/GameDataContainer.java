package ru.projectrobots.di.container;

/* created by zzemlyanaya on 07/03/2023 */

import ru.projectrobots.game.model.Robot;
import ru.projectrobots.game.model.Target;
import ru.projectrobots.game.model.drawer.RobotDrawer;
import ru.projectrobots.game.model.drawer.TargetDrawer;

public record GameDataContainer(
    Robot robot,
    RobotDrawer robotDrawer,
    Target target,
    TargetDrawer targetDrawer
) {}