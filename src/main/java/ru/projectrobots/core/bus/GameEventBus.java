package ru.projectrobots.core.bus;

/* created by zzemlyanaya on 24/03/2023 */

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import ru.projectrobots.core.events.GameEvent;

public class GameEventBus {

    private final PublishSubject<GameEvent> subject = PublishSubject.create();

    public GameEventBus() { }

    public void sendData(GameEvent event) {
        subject.onNext(event);
    }
    public Observable<GameEvent> getData() {
        return subject;
    }
}