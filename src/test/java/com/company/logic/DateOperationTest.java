package com.company.logic;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author santiago.mamani
 */
public class DateOperationTest {

    //@Test
    public void testDateDiferenceYear1() {
        try {
            int expected = 1;
            String date = "2017-02-14";
            Assert.assertEquals(DateOperation.diferenceYear(date), expected);
        } catch (ParseException ex) {
            Logger.getLogger(DateOperationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //@Test
    public void testDateDiferenceYear2() {

        int expected = 0;
        String date = "2017-02-15";
        try {
            Assert.assertEquals(DateOperation.diferenceYear(date), expected);
        } catch (ParseException ex) {
            Logger.getLogger(DateOperationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testDateAreSameYear() {
        try {
            boolean expected = true;
            String firstDate = "2017-02-15";
            String secondDate = "2017-02-15";
            Assert.assertEquals(DateOperation.areSameYear(firstDate, secondDate), expected);
        } catch (ParseException ex) {
            Logger.getLogger(DateOperationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testDateAreSameYear2() {
        try {
            boolean expected = false;
            String firstDate = "2017-02-15";
            String secondDate = "2018-02-15";
            Assert.assertEquals(DateOperation.areSameYear(firstDate, secondDate), expected);
        } catch (ParseException ex) {
            Logger.getLogger(DateOperationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
