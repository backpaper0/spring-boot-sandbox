package com.example;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimeSignalTest {

    @MockBean
    private TimeSignal timeSignal;

    @Test
    public void currentDateTime() throws Exception {

        final LocalDateTime value = LocalDateTime.of(2017, 1, 2, 3, 4, 5);

        given(timeSignal.currentDateTime()).willReturn(value);

        assertThat(timeSignal.currentDateTime()).isEqualTo(value);
    }
}
