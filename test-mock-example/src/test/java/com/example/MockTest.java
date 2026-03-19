package com.example;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
public class MockTest {

    @Autowired
    private MyService service;

    @MockitoBean
    private Foobar mock;

    @Test
    void noMock() {
        assertEquals("nullnull", service.foobar());
    }

    @Test
    void mockBar() {
        when(mock.bar()).thenReturn("Baz");
        assertEquals("nullBaz", service.foobar());

        verify(mock).foo();
        verify(mock).bar();
        verifyNoMoreInteractions(mock);
    }
}
