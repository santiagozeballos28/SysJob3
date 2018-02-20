package com.company.tools;

/**
 *
 * @author santiago.mamani
 */
public class ConstantData {

    public static final int MAX_LENGTH = 255;
    public static final int MIN_LENGTH = 10;
    public static final int SEPARATION_VACATION_DAY = 10;
    public static final int SEPARATION_SYSTEM_DAY = 5;
    //File configuration
    public static String FILE_CONFIGURATION = "com/company/config/sysjob-config.xml";
    //Mapper
    public static final String GET_BY_ID_EMPLOYEE = "Employee.getById";
    public static final String GET_BY_ID_EMPLOYEE_HISTORY_VACATION = "HistoryVacation.getById";
    public static final String GET_BY_ID_HISTORY_VACATION = "HistoryVacation.getByIdHistoryVacation";
    public static final String GET_BY_ID_EMPLOYEE_AND_DATE = "HistoryVacation.getByIdEmployeeAndDate";
    public static final String INSERT_HISTORY_VACATION = "HistoryVacation.insertHistoryVacation";
    public static final String EMPLOYEES_NOT_IN_DAY_VACATION = "Employee.getAllNotInDayVacation";
    public static final String GET_ALL_VACATION_COMPANY = "VacationCompany.getAll";
    public static final String INSERT_DAY_VACATION_LIST = "DayVacation.insertDayVacationList";
    public static final String UPDATE_DAY_VACATION = "DayVacation.updateDayVacation";
    public static final String GET_DAY_VACATION_BY_ID_AND_YEAR = "DayVacation.getByIdAndYear";
    public static final String GET_ALL_HOLIDAY = "Holiday.getAll";
    //Data   
    public static final String IDENTIFIER = "identifier";
    public static final String EMPTY = "empty";
    public static final String EMPLOYEE = "employee";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";
    public static final String REASON = "reason";
    public static final String SHORT = "short";
    public static final String LONG = "long";
    //properties
    public static final String IDENTIFIER_NOT_VALID = "identifier_not_valid";
    public static final String NOT_FOUND = "not_found";
    public static final String START_DATE_LESS_END_DATE = "start_date_less_end_date";
    public static final String SAME_YEAR = "same_year";
    public static final String REMAINING_VACATION = "remaining_vacation";
    public static final String ASCII = "ascii";
    public static final String SIZE_MAX = "size_max";
    public static final String SEPARATION_VACATION = "separation_vacation";
    public static final String NOT_VALID_DATE = "not_valid_date";
    public static final String CAN_NOT_VACATION = "can_not_vacation";
    public static final String ALREADY_EXISTING_VACATION = "already_existing_vacation";
    public static final String ANTICIPATION_DAYS = "anticipation_days";
    public static final String FUTURE_DATE = "future_date";
    //Format data
    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
    public static final String US_ASCII = "US-ASCII";

}
