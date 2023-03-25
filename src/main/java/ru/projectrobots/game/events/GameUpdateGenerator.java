package ru.projectrobots.game.events;

/* created by zzemlyanaya on 24/03/2023 */

import ru.projectrobots.core.bus.GameEventBus;
import ru.projectrobots.core.events.GameEvent;
import ru.projectrobots.core.events.GameEventType;

import java.util.Timer;
import java.util.TimerTask;

public class GameUpdateGenerator {

    private final GameEventBus eventBus;
    private final Timer timer = new Timer("Events generator", true);

    public GameUpdateGenerator(GameEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void startUpdates() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                eventBus.sendData(GameEvent.getEventWithoutData(GameEventType.REDRAW_VIEW));
            }
        }, 0, 50);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                eventBus.sendData(GameEvent.getEventWithoutData(GameEventType.UPDATE_ROBOT));
            }
        }, 0, 10);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                eventBus.sendData(GameEvent.getEventWithoutData(GameEventType.UPDATE_FIREBALL));
            }
        }, 0, 10);
    }
}
