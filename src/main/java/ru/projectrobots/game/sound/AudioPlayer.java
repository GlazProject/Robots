package ru.projectrobots.game.sound;

/* created by zzemlyanaya on 21/04/2023 */

import ru.projectrobots.log.Logger;
import ru.projectrobots.resources.Repository;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;


public class AudioPlayer {

    static private Clip background = null;
    static private Clip fireball = null;
    static private Clip collected = null;

    static {
        try {
            fireball = AudioSystem.getClip();
            fireball.open(Repository.getSound(Audio.FIREBALL.getResName()));

            collected = AudioSystem.getClip();
            collected.open(Repository.getSound(Audio.COLLECTED.getResName()));

        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void loopBackgroundMusic(Audio track) {
        if (background != null) {
            background.stop();
        }

        if (track == Audio.NO_MUSIC)
            return;

        try {
            background = AudioSystem.getClip();
            background.open(Repository.getSound(track.getResName()));
        } catch (LineUnavailableException | IOException e) {
            Logger.error(e.getMessage());
        }

        background.start();
        background.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void playFireballSound() {
        if (fireball.isActive()) {
            fireball.stop();
        }
        fireball.setFramePosition(0);
        fireball.start();
    }

    public static void playCollectedSound() {
        if (collected.isActive()) {
            collected.stop();
        }
        collected.setFramePosition(0);
        collected.start();
    }

    public static void stopAll() {
        background.stop();
        background.close();

        fireball.stop();
        fireball.close();

        collected.stop();
        collected.close();
    }
}
