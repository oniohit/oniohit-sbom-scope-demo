package com.example;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class LegacyTest {
    @Test
    public void legacyAdd() {
        assertEquals(7, App.add(3, 4));
    }
}
