package com.example.accounts.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateRange {
    private DateRange() {}

    public static LocalDateTime[] resolveRange(LocalDate start, LocalDate end) {
        LocalDateTime startResolved = (start != null)
            ? start.atStartOfDay()
            : LocalDateTime.of(1970, 1, 1, 0, 0);

        LocalDateTime endResolved = (end != null)
            ? end.atTime(LocalTime.MAX)
            : LocalDateTime.now();

        return new LocalDateTime[]{startResolved, endResolved};
    }
}
