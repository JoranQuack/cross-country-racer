package seng201.team019.services;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class DateFormaterServiceTest {

    private DateFormaterService dateFormaterService;

    @BeforeEach
    public void init() {
        dateFormaterService = new DateFormaterService();
    }

    @Test
    void testFormatTime() {
        Assertions.assertEquals("00:00:00.000", dateFormaterService.formatTime(0));
    }
}
