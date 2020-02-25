package expackage;

import java.util.Random;

public class Example {
    public int notFlaky(int x, int y, int op){
        if(op==1){
            int somma = x+y;
            return somma;
        }
        else{
            int diff = x-y;
            return diff;
        }

    }

    public int flaky(int x){
        Random r = new Random();
        int random = r.nextInt(x);
        return random;
    }



}
