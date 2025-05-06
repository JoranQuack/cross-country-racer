package seng201.team019.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DateFormatterServiceTest {

    private DateFormatterService dateFormatterService;

    @BeforeEach
    public void init() {
        dateFormatterService = new DateFormatterService();
    }

    @Test
    void testFormatTime() {
        Assertions.assertEquals("00:00:00.000", dateFormatterService.formatTime(0));
    }
}
