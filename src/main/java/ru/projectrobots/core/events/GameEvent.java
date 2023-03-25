package ru.projectrobots.core.events;

/* created by zzemlyanaya on 24/03/2023 */

public record GameEvent<T>(
    GameEventType type,
    T data
) {
    public static GameEvent getEventWithoutData(GameEventType type) {
        return new GameEvent(type, null);
    }
}
