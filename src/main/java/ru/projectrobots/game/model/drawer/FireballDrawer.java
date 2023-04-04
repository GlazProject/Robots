package ru.projectrobots.game.model.drawer;

/* created by zzemlyanaya on 07/03/2023 */

import ru.projectrobots.core.drawer.Drawer;
import ru.projectrobots.game.model.Fireball;
import ru.projectrobots.resources.ResourceManager;
import ru.projectrobots.resources.ResourceProvider;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class FireballDrawer extends Drawer {

    private static final String LEFT = "fireball.left";
    private static final String RIGHT = "fireball.right";

    private final HashMap<String, Integer> nextFrames = new HashMap<>();

    private int getNextFrame(Fireball fireball) {
        Integer frame = nextFrames.get(fireball.getId());
        if (frame == null) {
            nextFrames.put(fireball.getId(), 1);
            frame = 1;
        }

        return frame;
    }

    public void drawFireball(Graphics2D g2d, Fireball fireball) throws FileNotFoundException, NoSuchFieldException {
        if (fireball.isFinished()) return;

        String action = fireball.isLTR() ? RIGHT : LEFT;
        int nextFrame = getNextFrame(fireball) + 1;

        Image image = ResourceProvider.getImage(action + "." + nextFrame, true, false);
        g2d.drawImage(image,
            fireball.getX() - fireball.getModelWidth() / 2, fireball.getY() - fireball.getModelHeight() / 2,
            fireball.getModelWidth(), fireball.getModelHeight(),
            null);

        int totalFramesCount = ResourceManager.getFramesCount(action);
        nextFrame %= totalFramesCount;
        nextFrames.put(fireball.getId(), nextFrame);
    }

    public void drawAllFireballs(Graphics2D g2d, ArrayList<Fireball> fireballs) throws FileNotFoundException, NoSuchFieldException {
        for (Fireball fireball : fireballs) {
            drawFireball(g2d, fireball);
        }
    }
}
