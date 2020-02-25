package test;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    @Test
   public void add() {
        final long result = new Calculator().add(2, 3);
        assertEquals(5L, result);
    }

    @Test
   public void subtract() {
        final long result = new Calculator().subtract(10,3);
        assertEquals(7L, result);
    }


}