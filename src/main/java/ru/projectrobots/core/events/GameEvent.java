package ru.projectrobots.core.events;

/* created by zzemlyanaya on 24/03/2023 */

import org.jetbrains.annotations.Nullable;

public record GameEvent<T>(
    GameEventType type,
    @Nullable T data
) {
    public static GameEvent getEventWithoutData(GameEventType type) {
        return new GameEvent(type, null);
    }
}
