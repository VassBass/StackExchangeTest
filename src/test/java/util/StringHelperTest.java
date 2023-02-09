package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringHelperTest {

    @Test
    public void testIntArrayToString() {
        int[] ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        String expected = "1;2;3;4;5;6;7;8;9";

        assertEquals(expected, StringHelper.intArrayToString(ints));
    }
}