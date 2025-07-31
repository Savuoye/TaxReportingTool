package com.fisglobal.taxreporting.service.infrastructure;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;


/**
 * Time provider class used to get the current time
 */
@Service
public class CurrentTimeProvider {
    protected Clock clock;

    /**
     * Method returns current time based on the format
     *
     * @param format
     *                   Time format
     * 
     * @return Formatted time in string format
     */
    public String getCurrentTime(String format) {
        if (clock == null) {
            clock = Clock.systemDefaultZone();
        }
        Instant now = Instant.now(clock);
        DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern(format).withZone(ZoneId.systemDefault());
        return fmt1.format(now);
    }
}
