package ru.projectrobots.core.view;/* created by zzemlyanaya on 07/03/2023 */

import ru.projectrobots.core.events.ViewUpdateEvent;

public interface UpdatableView {
    void onUpdate(ViewUpdateEvent event);
}