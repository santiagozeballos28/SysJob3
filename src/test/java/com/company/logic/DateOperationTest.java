package com.company.logic;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author santiago.mamani
 */
public class DateOperationTest {

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

    @Test
    public void testDateAreSameYear3() {
        try {
            boolean expected = false;
            String firstDate = "2018-12-17";
            String secondDate = "2019-02-15";
            Assert.assertEquals(DateOperation.areSameYear(firstDate, secondDate), expected);
        } catch (ParseException ex) {
            Logger.getLogger(DateOperationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testBusinessDays() {
        try {
            int expected = 4;
            String startDate = "2018-02-28";
            String endDate = "2018-03-05";
            Assert.assertEquals(DateOperation.getBusinessDays(startDate, endDate, null), expected);
        } catch (ParseException ex) {
            Logger.getLogger(DateOperationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testBusinessDays2() {
        try {
            int expected = 1;
            String startDate = "2018-03-03";
            String endDate = "2018-03-05";
            Assert.assertEquals(DateOperation.getBusinessDays(startDate, endDate, null), expected);
        } catch (ParseException ex) {
            Logger.getLogger(DateOperationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testBusinessDays3() {
        try {
            int expected = 2;
            String startDate = "2018-02-28";
            String endDate = "2018-03-05";
            List<String> holidays = new ArrayList<String>();
            holidays.add(endDate);
            holidays.add(startDate);
            Assert.assertEquals(DateOperation.getBusinessDays(startDate, endDate, holidays), expected);
        } catch (ParseException ex) {
            Logger.getLogger(DateOperationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testIsLessOrEqual() {
        try {
            boolean expected = true;
            String startDate = "2018-02-28";
            String endDate = "2018-03-05";
            Assert.assertEquals(DateOperation.isLessOrEquals(startDate, endDate), expected);
        } catch (ParseException ex) {
            Logger.getLogger(DateOperationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testIsLessOrEqual2() {
        try {
            boolean expected = true;
            String startDate = "2018-02-28";
            String endDate = "2018-02-28";
            Assert.assertEquals(DateOperation.isLessOrEquals(startDate, endDate), expected);
        } catch (ParseException ex) {
            Logger.getLogger(DateOperationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testIsLessOrEqual3() {
        try {
            boolean expected = false;
            String startDate = "2018-02-28";
            String endDate = "2018-02-27";
            Assert.assertEquals(DateOperation.isLessOrEquals(startDate, endDate), expected);
        } catch (ParseException ex) {
            Logger.getLogger(DateOperationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testIsLess() {
        try {
            boolean expected = true;
            String startDate = "2018-02-28";
            String endDate = "2018-03-05";
            Assert.assertEquals(DateOperation.isLess(startDate, endDate), expected);
        } catch (ParseException ex) {
            Logger.getLogger(DateOperationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testIsLess2() {
        try {
            boolean expected = true;
            String startDate = "2018-12-25";
            String endDate = "2018-12-26";
            Assert.assertEquals(DateOperation.isLess(startDate, endDate), expected);
        } catch (ParseException ex) {
            Logger.getLogger(DateOperationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testIsLess3() {
        try {
            boolean expected = false;
            String startDate = "2018-12-25";
            String endDate = "2018-12-25";
            Assert.assertEquals(DateOperation.isLess(startDate, endDate), expected);
        } catch (ParseException ex) {
            Logger.getLogger(DateOperationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testIsLess4() {
        try {
            boolean expected = false;
            String startDate = "2018-12-25";
            String endDate = "2018-12-24";
            Assert.assertEquals(DateOperation.isLess(startDate, endDate), expected);
        } catch (ParseException ex) {
            Logger.getLogger(DateOperationTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
