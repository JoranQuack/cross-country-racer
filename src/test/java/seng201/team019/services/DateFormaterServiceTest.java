package seng201.team019.services;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DateFormaterServiceTest {

    private TimeFormaterService dateFormaterService;

    @BeforeEach
    public void init() {
        dateFormaterService = new TimeFormaterService();
    }

    @Test
    void testFormatTime() {
        Assertions.assertEquals("00:00:00.000", dateFormaterService.formatTime(0));
    }
}
