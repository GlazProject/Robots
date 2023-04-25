package ru.projectrobots.game.viewmodel;

/* created by zzemlyanaya on 05/03/2023 */

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.projectrobots.core.bus.GameEventBus;
import ru.projectrobots.core.events.GameEvent;
import ru.projectrobots.core.events.ViewUpdateEvent;
import ru.projectrobots.di.container.AudioEntry;
import ru.projectrobots.di.container.GameDataContainer;
import ru.projectrobots.di.container.GlobalSettings;
import ru.projectrobots.di.container.LookEntry;
import ru.projectrobots.game.events.GameUpdateGenerator;
import ru.projectrobots.game.model.Fireball;
import ru.projectrobots.game.model.Robot;
import ru.projectrobots.game.model.Target;
import ru.projectrobots.game.sound.Audio;
import ru.projectrobots.game.sound.AudioPlayer;
import ru.projectrobots.game.view.GameView;
import ru.projectrobots.log.Logger;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class GameViewModel {

    private final Robot robot;
    private final Target target;
    private final ArrayList<Fireball> fireballs;
    private final GameView view;

    private final GameEventBus eventBus;
    private final CompositeDisposable disposable = new CompositeDisposable();

    public GameViewModel(GameDataContainer data, GameEventBus eventBus) {
        this.eventBus = eventBus;
        GameUpdateGenerator updateGenerator = new GameUpdateGenerator(eventBus);

        robot = data.robot();
        target = data.target();
        fireballs = data.fireballs();
        view = new GameView(data);

        updateGenerator.startUpdates();
        initUserEventListeners();
        initGameEventListeners();

        AudioPlayer.loopBackgroundMusic(Audio.BACKGROUND_2);
    }

    public GameView getView() {
        return view;
    }

    private void initUserEventListeners() {
        view.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Logger.debug("Clicked at " + e.getPoint());
                target.setTargetPosition(e.getPoint());
                view.onUpdate(ViewUpdateEvent.REDRAW_MODEL_EVENT);
            }
        });
    }

    private void initGameEventListeners() {
        disposable.add(
            eventBus
                .getData()
                .observeOn(Schedulers.computation())
                .subscribe(this::onGameEventReceived)
        );
    }

    private void onGameEventReceived(GameEvent event) {
        Logger.debug("Received event: " + event.type().name());

        switch (event.type()) {
            case GAME_CLOSED -> onGameClosed();
            case REDRAW_VIEW -> view.onUpdate(ViewUpdateEvent.REDRAW_MODEL_EVENT);
            case UPDATE_ROBOT -> robot.update(target);
            case SEND_FIREBALL -> sendFireball();
            case UPDATE_FIREBALL -> updateFireballs();
            case UPDATE_SETTING -> updateGlobalSettings(event.data());
            case TARGET_COLLECTED -> AudioPlayer.playCollectedSound();
        }
    }

    private void updateGlobalSettings(Object entry) {
        if (entry instanceof AudioEntry audioEntry) {
            AudioPlayer.loopBackgroundMusic(Audio.findAudioBySuffix(audioEntry.backgroundTrack()));
        } else if (entry instanceof LookEntry lookEntry) {
            GlobalSettings.setSprite(lookEntry.entityId(), lookEntry.look());
        } else {
            Logger.error("Entry from event is not Global settings entry");
        }
    }

    private void sendFireball() {
        Fireball fireball = Fireball.generateFireball(robot.getBoardWidth(), robot.getBoardHeight());
        fireballs.add(fireball);
        AudioPlayer.playFireballSound();
    }

    private void updateFireballs() {
        Iterator<Fireball> iterator = fireballs.iterator();
        while (iterator.hasNext()) {
            Fireball fireball = iterator.next();

            fireball.update(target, robot);
            if (fireball.isFinished()) iterator.remove();
        }
    }

    private void onGameClosed() {
        AudioPlayer.stopAll();
        disposable.dispose();
    }
}
