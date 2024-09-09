package org.test.model;

import java.time.Duration;
import java.util.List;

public class DirectionFlights {

    private String direction;
    private List<Duration> durations;

    public String getDirection() {
        return direction;
    }
    public void addDuration(Duration duration) {
        durations.add(duration);
    }
}
