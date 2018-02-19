package com.company.logic;

import com.company.tools.ConstantData;
import com.company.tools.RegularExpression;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import org.joda.time.LocalDate;
import org.joda.time.Period;

/**
 *
 * @author santiago.mamani
 */
public class DateOperation {

    public static int getYearCurrent() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    public static boolean isValidDateFormat(String date) {
        return Pattern.matches(RegularExpression.DATE, date);
    }

    public static int diferenceYear(String date) throws ParseException {
        int year = -1;
        SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantData.SIMPLE_DATE_FORMAT);
        Date dateO = dateFormat.parse(date);
        LocalDate pdate = LocalDate.fromDateFields(dateO);
        LocalDate now = LocalDate.now();
        Period diff = Period.fieldDifference(pdate, now);
        if (diff.getYears() > 0) {
            if (diff.getMonths() > 0) {
                return diff.getYears();
            } else {
                if (diff.getMonths() == 0) {
                    if (diff.getDays() > 0) {
                        return diff.getYears();
                    }
                }
                return diff.getYears() - 1;
            }
        }
        return year;
    }

    public static int diferenceDays(String dateFirst, String dateSecond) throws ParseException {
        int diferenceDays = -1;
        SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantData.SIMPLE_DATE_FORMAT);
        Date dateFirstFormat = dateFormat.parse(dateFirst);
        Date dateSecondFormat = dateFormat.parse(dateSecond);
        LocalDate localDateFirst = LocalDate.fromDateFields(dateFirstFormat);
        LocalDate localDateSecond = LocalDate.fromDateFields(dateSecondFormat);
        Period diff = Period.fieldDifference(localDateFirst, localDateSecond);
        diferenceDays = diff.getDays();

        return diferenceDays;
    }

    public static boolean isLess(String dateFirst, String dateSecond) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantData.SIMPLE_DATE_FORMAT);
        Date dateFirstFormat = dateFormat.parse(dateFirst);
        Date dateSecondFormat = dateFormat.parse(dateSecond);
        LocalDate localDateFirst = LocalDate.fromDateFields(dateFirstFormat);
        LocalDate localDateSecond = LocalDate.fromDateFields(dateSecondFormat);
        Period diff = Period.fieldDifference(localDateFirst, localDateSecond);
        if (diff.getYears() > 0) {
            return true;
        } else if (diff.getYears() == 0) {
            if (diff.getMonths() >= 0 && diff.getDays() >= 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean areSameYear(String dateFirst, String dateSecond) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantData.SIMPLE_DATE_FORMAT);
        Date dateFirstFormat = dateFormat.parse(dateFirst);
        Date dateSecondFormat = dateFormat.parse(dateSecond);
        LocalDate localDateFirst = LocalDate.fromDateFields(dateFirstFormat);
        LocalDate localDateSecond = LocalDate.fromDateFields(dateSecondFormat);
        Period diff = Period.fieldDifference(localDateFirst, localDateSecond);
        if (diff.getYears() == 0) {
            return true;
        }
        return false;
    }

    public static int getBusinessDays(String startDate, String endDate, List<String> holidays) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantData.SIMPLE_DATE_FORMAT);
        Calendar startDateCalendar = Calendar.getInstance();
        startDateCalendar.setTime(dateFormat.parse(startDate));
        Calendar endDateCalendar = Calendar.getInstance();
        endDateCalendar.setTime(dateFormat.parse(endDate));
        if (holidays == null) {
            return getBusinessDays(startDateCalendar, endDateCalendar, null);
        }
        List<Date> holidaysDate = new ArrayList<Date>();
        for (String holiday : holidays) {
            holidaysDate.add(dateFormat.parse(holiday));
        }
        return getBusinessDays(startDateCalendar, endDateCalendar, holidaysDate);
    }

    public static int getBusinessDays(Calendar startDate, Calendar endDate, List<Date> holidays) {
        int workDays = 0;
        boolean businessDay = false;
        while (startDate.before(endDate) || startDate.equals(endDate)) {
            if (holidays != null) {
                int i = 0;
                boolean find = false;
                while (i < holidays.size() && !find) {
                    Date holiday = holidays.get(i);
                    Date startDateTime = startDate.getTime();
                    if (startDate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && startDate.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && !startDateTime.equals(holiday)) {
                        businessDay = true;
                    } else {
                        businessDay = false;
                        find = true;
                    }
                    i++;
                }
            } else if (startDate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && startDate.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                workDays++;
            }
            if (businessDay == true) {
                workDays++;
            }
            startDate.add(Calendar.DATE, 1);
        }
        return workDays;
    }
}
