package com.titanic.webmvc.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LocalDateTimeLearningTest {

    @Test
    void truncatedToBySecondGiven_ShouldIncludeMilliSecond() {
        System.out.println((LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
            .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        System.out.println(LocalDateTime.now());
    }
}
