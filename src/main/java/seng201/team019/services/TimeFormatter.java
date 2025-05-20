package seng201.team019.services;

import java.time.Duration;

/**
 * TimeFormatter is a utility class for formatting time in milliseconds into a
 * human-readable string format.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class TimeFormatter {
    /**
     * Returns a nice string of a time in milliseconds
     *
     * @param time is the time in milliseconds
     * @return formatted string with hrs:min:sec.milliseconds
     */
    public String formatTime(long time) {
        Duration duration = Duration.ofMillis(time);
        return String.format("%02d:%02d:%02d.%03d", duration.toHoursPart(), duration.toMinutesPart(),
                duration.toSecondsPart(), duration.toMillisPart());
    }

    /**
     * Returns a nice string of a time in milliseconds
     *
     * @param time is the time in milliseconds
     * @return formatted string with hrs:min
     */
    public String formatTimeShort(long time) {
        Duration duration = Duration.ofMillis(time);
        return String.format("%d:%02d", duration.toHoursPart(), duration.toMinutesPart());
    }
}
