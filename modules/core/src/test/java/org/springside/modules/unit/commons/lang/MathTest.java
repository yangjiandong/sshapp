package org.springside.modules.unit.commons.lang;

import org.apache.commons.lang.math.Fraction;
import org.junit.Test;

public class MathTest {

    @Test
    public void testFraction(){
        String us = "23 31/37";
        Fraction fr = Fraction.getFraction(us);
        System.out.println(us + " :" + fr.doubleValue());
    }
}
