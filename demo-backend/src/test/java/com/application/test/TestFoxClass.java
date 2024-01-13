package com.application.test;

import com.application.objects.Fox;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFoxClass {
    @Test
    public void testFoxSetName() {
        var testFox = new Fox("test1", 1);
        testFox.setName("test");
    }
    @Test
    public void testFoxSetTails() {
        var testFox = new Fox("test1", 1);
        testFox.setTails(2);
    }
    @Test
    public void testFoxGetName() {
        var testFox = new Fox("test1", 1);
        assertEquals("test1", testFox.getName());
    }
    @Test
    public void testFoxGetTails() {
        var testFox = new Fox("test1", 1);
        assertEquals(1, testFox.getTails());
    }
    @Test
    public void testFoxGetId() {
        var testFox = new Fox("test1", 1);
        assertEquals(null, testFox.getId());
    }
    @Test
    public void testFoxSetId() {
        var testFox = new Fox("test1", 1);
        testFox.setId(1L);
    }
}
