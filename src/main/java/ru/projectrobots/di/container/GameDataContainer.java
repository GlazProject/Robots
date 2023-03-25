package ru.projectrobots.di.container;

/* created by zzemlyanaya on 07/03/2023 */

import ru.projectrobots.game.model.Fireball;
import ru.projectrobots.game.model.Robot;
import ru.projectrobots.game.model.Target;

import java.util.ArrayList;

public record GameDataContainer(
    Robot robot,
    Target target,
    ArrayList<Fireball> fireballs
) {}