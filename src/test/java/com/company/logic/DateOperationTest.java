package com.company.logic;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author santiago.mamani
 */
public class DateOperationTest {

    //@Test
    public void testDateDiferenceYear1() {

        int expected = 1;
        String date = "2017-02-14";
        Assert.assertEquals(DateOperation.diferenceYear(date), expected);
    }

    //@Test
    public void testDateDiferenceYear2() {

        int expected = 0;
        String date = "2017-02-15";
        Assert.assertEquals(DateOperation.diferenceYear(date), expected);
    }
}
