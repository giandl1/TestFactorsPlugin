package expackage;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleTest {

    @Test
    public void notFlaky() {
        Example flaky = new Example();
        int numero = flaky.notFlaky(1, 2,1);
        assertEquals(3, numero);

    }

    @Test
    public void flaky(){
        Example flaky = new Example();
        int numero = flaky.flaky(4);
        assertEquals(2, numero);
    }


}