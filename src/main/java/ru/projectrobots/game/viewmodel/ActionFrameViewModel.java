package ru.projectrobots.game.viewmodel;

/* created by zzemlyanaya on 25/03/2023 */

import ru.projectrobots.core.bus.GameEventBus;
import ru.projectrobots.core.events.GameEvent;
import ru.projectrobots.core.events.GameEventType;
import ru.projectrobots.di.container.AudioEntry;
import ru.projectrobots.di.container.LookEntry;
import ru.projectrobots.game.model.GameAction;
import ru.projectrobots.game.view.ActionsFrame;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.regex.Pattern;

import static ru.projectrobots.core.view.DialogFactory.showCloseDialog;

public class ActionFrameViewModel {

    private final ActionsFrame view;

    private final GameEventBus eventBus;

    public ActionFrameViewModel(GameEventBus eventBus) {
        this.eventBus = eventBus;
        view = getActionsView();
    }

    public ActionsFrame getView() {
        return view;
    }

    private ActionsFrame getActionsView() {
        ActionsFrame actionsView = new ActionsFrame(actionListener);
        actionsView.setPreferredSize(new Dimension(250, 500));

        actionsView.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent event) {
                super.internalFrameClosing(event);
                showCloseDialog(event, t -> {});
            }
        });

        return actionsView;
    }

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (Objects.equals(e.getActionCommand(), GameAction.FIREBALL.name())) {
                eventBus.sendData(GameEvent.getEventWithoutData(GameEventType.SEND_FIREBALL));
            }

            if (e.getActionCommand().startsWith("set.look")) {
                sendLookUpdate(e.getActionCommand());
            } else if (e.getActionCommand().startsWith("set.music")) {
                sendMusicUpdate(e.getActionCommand());
            }
        }
    };

    private void sendLookUpdate(String command) {
        String[] data = command.split(Pattern.quote("."));
        eventBus.sendData(new GameEvent<>(
            GameEventType.UPDATE_SETTING,
            new LookEntry(data[2], data[3])));
    }

    private void sendMusicUpdate(String command) {
        String[] data = command.split(Pattern.quote("."));
        eventBus.sendData(new GameEvent<>(
            GameEventType.UPDATE_SETTING,
            new AudioEntry(data[2])));
    }
}
