package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;
import org.junit.jupiter.api.Test;

public class AppTest {
    @Test
    void add_works() {
        assertEquals(5, App.add(2, 3));
    }

    @Test
    void mockito_example() {
        List<String> list = mock(List.class);
        when(list.get(0)).thenReturn("hello");
        assertEquals("hello", list.get(0));
        verify(list).get(0);
    }
}
