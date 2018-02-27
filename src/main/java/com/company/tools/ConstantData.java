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
    public static final String DELETE_BY_ID_EMPLOYEE = "HistoryVacation.deleteByIdEmployee";
    public static final String GET_BY_ID_HISTORY_VACATION = "HistoryVacation.getByIdHistoryVacation";
    public static final String GET_BY_ID_EMPLOYEE_AND_DATE = "HistoryVacation.getByIdEmployeeAndDate";
    public static final String INSERT_HISTORY_VACATION = "HistoryVacation.insertHistoryVacation";
    public static final String EMPLOYEES_NOT_IN_DAY_VACATION = "Employee.getAllNotInDayVacation";
    public static final String GET_ALL_VACATION_COMPANY = "VacationCompany.getAll";
    public static final String INSERT_DAY_VACATION_LIST = "DayVacation.insertDayVacationList";
    public static final String UPDATE_DAY_VACATION = "DayVacation.updateDayVacation";
    public static final String GET_DAY_VACATION_BY_ID_AND_YEAR = "DayVacation.getByIdAndYear";
    public static final String DELETE_BY_YEAR_VACATION = "DayVacation.deleteByYearVacation";
    public static final String GET_ALL_HOLIDAY = "Holiday.getAll";
    //Data properties  
    public static final String IDENTIFIER = "data.identifier";
    public static final String EMPTY = "data.empty";
    public static final String EMPLOYEE = "data.employee";
    public static final String START_DATE = "data.startDate";
    public static final String END_DATE = "data.endDate";
    public static final String REASON = "data.reason";
    public static final String SHORT = "data.short";
    public static final String LONG = "data.long";
    public static final String NEW_VACATION = "data.new.vacation";
    //Autentication data
    public static final String AUTENTICATION_USER_MAIL = "autentication.user.mail";
    public static final String AUTENTICATION_USER_PASSWORD_KEY = "autentication.user.keyPassword";
    //Mesages properties
    public static final String MSG_ERROR_VALIDATION_REQUIRED = "error.validation.required";
    public static final String MSG_NOT_VALID_IDENTIFIER = "error.notValid.identifier";
    public static final String MSG_NOT_IS_FUTURE_DATE = "error.notIsDateFuture";
    public static final String MSG_OBJECT_NOT_FOUND = "error.object.notFound";
    public static final String MSG_NOT_VALID_DATE_FORMAT = "error.notValidFormat.date";
    public static final String MSG_START_DATE_LESS_END_DATE = "error.startDate.less.endDate";
    public static final String MSG_DATE_NOT_YEAR_CURRENT = "error.date.notYearCurrent";
    public static final String MSG_REMAINING_VACATION = "error.remaining.vacation";
    public static final String MSG_CAN_NOT_VACATION = "error.canNotVacation";
    public static final String MSG_ANTICIPATION_DAYS_SYSTEM = "error.anticipationDays.system";
    public static final String MSG_SIZE_VALID = "error.size.valid";
    public static final String MSG_FORMAT_ASCII = "format.ascii";
    public static final String MSG_SEPARATION_VACATION = "info.separation.vacation";
    public static final String MSG_ALREADY_EXISTING_VACATION = "info.already.existing.vacation";
    public static final String MSG_VACATION_PERIOD = "info.vacation.period";
    public static final String MSG_VACATION_REMAINING = "info.vacation.remaining";
    public static final String MSG_DAY_VACATION_ZERO = "info.vacation.dayZero";
    //Format data
    public static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";
    public static final String US_ASCII = "US-ASCII";
    //enums

    public enum Status {
        OK, CREATED, BAD_REQUEST, NOT_FOUND, INTERNAL_SERVER_ERROR
    }
}
