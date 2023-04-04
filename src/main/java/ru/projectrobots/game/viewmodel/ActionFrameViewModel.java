package ru.projectrobots.game.viewmodel;

/* created by zzemlyanaya on 25/03/2023 */

import ru.projectrobots.core.bus.GameEventBus;
import ru.projectrobots.core.events.GameEvent;
import ru.projectrobots.core.events.GameEventType;
import ru.projectrobots.game.model.GameAction;
import ru.projectrobots.game.view.ActionsFrame;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

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
        }
    };
}
