package seng201.team019.services;

import java.time.Duration;

public class DateFormatterService {
    /**
     * Returns nice string of a time in milliseconds
     *
     * @param time is the time in milliseconds
     * @return formatted string with hrs:min:sec.milliseconds
     */
    public String formatTime(long time) {
        Duration duration = Duration.ofMillis(time);
        return String.format("%02d:%02d:%02d.%03d", duration.toHoursPart(), duration.toMinutesPart(),
                duration.toSecondsPart(), duration.toMillisPart());
    }
}
