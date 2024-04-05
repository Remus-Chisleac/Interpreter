package Controller;

import javafx.event.Event;
import javafx.event.EventType;

public class MyEvent extends Event{

    public MyEvent(EventType<? extends Event> arg0) {
        super(arg0);
    }

    public static final EventType<MyEvent> Populate =
                new EventType<>(Event.ANY, "Populate");


}
