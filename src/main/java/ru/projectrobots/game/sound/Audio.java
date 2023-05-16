package ru.projectrobots.game.sound;/* created by zzemlyanaya on 22/04/2023 */

import java.util.Arrays;

public enum Audio {
    BACKGROUND_1("sounds.background1"),
    BACKGROUND_2("sounds.background2"),
    BACKGROUND_3("sounds.background3"),
    NO_MUSIC("sounds.no_music"),
    FIREBALL("sounds.fireball"),
    COLLECTED("sounds.collected");

    private final String name;

    Audio(String name) { this.name = name; }

    public String getResName() {
        return name;
    }

    public static Audio findAudioBySuffix(String name) {
        return Arrays.stream(values())
            .filter(a -> a.getResName().endsWith(name))
            .findFirst()
            .orElse(BACKGROUND_1);
    }
}
