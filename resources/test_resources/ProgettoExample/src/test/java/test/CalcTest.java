package test;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalcTest {

    @Test
    public void add() {
        final long result = new Calc().add(2, 3);
        assertEquals(5L, result);
        assertEquals(5L, result);
        assertEquals(5L, result);

    }

    @Test
    public void subtract() {
        final long result = new Calc().subtract(10,3);
        assertEquals(7L, result);
    }

}