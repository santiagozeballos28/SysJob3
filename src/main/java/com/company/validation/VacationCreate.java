package com.company.validation;

import com.company.logic.DateOperation;
import com.company.model.DayVacation;
import com.company.model.HistoryVacation;
import com.company.model.Holiday;
import com.company.util.Bundle;
import com.company.tools.ConstantData;
import com.company.util.Either;
import com.company.util.Error;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author santiago.mamani
 */
public class VacationCreate {

    private Bundle bundle;
    private DayVacation dayVacation;
    private HistoryVacation historyVacation;
    private List<String> holidays;
    private DateValidation dateValidation;
    private ObjectValidation objectValidation;

    public VacationCreate() {
        bundle = new Bundle();
        dayVacation = new DayVacation();
        historyVacation = new HistoryVacation();
        holidays = new ArrayList<String>();
        dateValidation = new DateValidation();
        objectValidation = new ObjectValidation();
    }

    public void setDayVacation(DayVacation dayVacation) {
        this.dayVacation = dayVacation;
    }

    public void setHistoryVacation(HistoryVacation historyVacation) {
        this.historyVacation = historyVacation;
    }

    public void setHoliday(List<Holiday> holidayCompany) {
        for (Holiday holiday : holidayCompany) {
            holidays.add(holiday.getDateHoliday());
        }
    }

    public Either<Error, Boolean> notEmpty(String startDate, String endDate) {
        Error error = new Error();
        if (StringUtils.isBlank(startDate)) {
            Object[] args = {bundle.getData(ConstantData.START_DATE)};
            String message = bundle.getMessage(ConstantData.EMPTY, args);
            error.addError(message);
        }
        if (StringUtils.isBlank(endDate)) {
            Object[] args = {bundle.getData(ConstantData.END_DATE)};
            String message = bundle.getMessage(ConstantData.EMPTY, args);
            error.addError(message);
        }
        if (!error.isEmpty()) {
            return Either.error(error);
        }
        return Either.success(true);
    }

    public Either<Error, Boolean> reasonValid(String reason) {
        Error error = new Error();
        Either<Error, Boolean> isUsAscii = objectValidation.isUsAscii(reason.trim());
        if (isUsAscii.error()) {
            error.addAllErrors(isUsAscii.getError());
        }
        Either<Error, Boolean> isValidSize = objectValidation.isValidSize(ConstantData.REASON, reason.trim(), ConstantData.MIN_LENGTH, ConstantData.MAX_LENGTH);
        if (isValidSize.error()) {
            error.addAllErrors(isValidSize.getError());
        }
        if (!error.isEmpty()) {
            return Either.error(error);
        }
        return Either.success(true);
    }

    public Either<Error, Boolean> complyConditionDate(String startDate, String endDate) {
        Error error = new Error();
        Either<Error, Boolean> resValidDate = dateValid(startDate, endDate);
        if (resValidDate.error()) {
            return resValidDate;
        }
        try {
            int numberDaysRequested = DateOperation.getBusinessDays(startDate, endDate, holidays);
            resValidDate = hasRemainingVacation(numberDaysRequested);
            if (resValidDate.error()) {
                error.addAllErrors(resValidDate.getError());
            }
            if (!error.isEmpty()) {
                return Either.error(error);
            }
            return Either.success(true);
        } catch (ParseException ex) {
            return Either.error(new Error(ex.getMessage()));
        }
    }

    private Either<Error, Boolean> dateValid(String startDate, String endDate) {
        Either<Error, Boolean> resValidDate = dateValidation.isValidFormat(startDate, endDate);
        if (resValidDate.error()) {
            return resValidDate;//If the date formats are not valid, an error is returned
        }
        resValidDate = dateValidation.isLessOrEquals(startDate, endDate);
        Error errorRes = new Error();
        if (resValidDate.error()) {
            errorRes.addAllErrors(resValidDate.getError());
        }
        resValidDate = dateValidation.areSameYear(startDate, endDate);
        if (resValidDate.error()) {
            errorRes.addAllErrors(resValidDate.getError());
        }
        resValidDate = dateValidation.isDateFuture(ConstantData.START_DATE, startDate);
        if (resValidDate.error()) {
            errorRes.addAllErrors(resValidDate.getError());
        }
        resValidDate = dateValidation.isDateFuture(ConstantData.END_DATE, endDate);
        if (resValidDate.error()) {
            errorRes.addAllErrors(resValidDate.getError());
        }

        if (!errorRes.isEmpty()) {
            //If the start date is greater than the final date or they are not from the same year, the error is returned.
            return Either.error(errorRes);
        }
        resValidDate = separationDay(startDate);
        if (resValidDate.error()) {
            errorRes.addAllErrors(resValidDate.getError());
        }
        if (!errorRes.isEmpty()) {
            return Either.error(errorRes);
        }
        return Either.success(true);
    }

    private Either<Error, Boolean> separationDay(String startDate) {
        if (historyVacation.isEmpty()) {
            return Either.success(true);
        }
        try {
            boolean historyVacationSameYear = DateOperation.areSameYear(historyVacation.getEndDate(), startDate);
            Error error = new Error();
            if (historyVacationSameYear) {
                Either<Error, Boolean> existingVacation = alreadyExistingVacation(historyVacation.getStartDate(), historyVacation.getEndDate(), startDate);
                if (existingVacation.error()) {
                    error.addAllErrors(existingVacation.getError());
                }
                Either<Error, Boolean> separationDays = validSeparationDay(historyVacation.getEndDate(), startDate);
                if (separationDays.error()) {
                    error.addAllErrors(separationDays.getError());
                }
            }
            Either<Error, Boolean> separationSystem = validSeparationSystem(startDate);
            if (separationSystem.error()) {
                error.addAllErrors(separationSystem.getError());
            }
            if (!error.isEmpty()) {
                return Either.error(error);
            }
            return Either.success(true);
        } catch (ParseException ex) {
            return Either.error(new Error(ex.getMessage()));
        }
    }

    private Either<Error, Boolean> hasRemainingVacation(int numberDaysRequested) {
        if (dayVacation.getVacationRemaining() == 0) {
            Object[] args = {};
            String message = bundle.getMessage(ConstantData.CAN_NOT_VACATION, args);
            return Either.error(new Error(message));
        }
        if (numberDaysRequested > dayVacation.getVacationRemaining()) {
            Object[] args = {numberDaysRequested, dayVacation.getVacationRemaining()};
            String message = bundle.getMessage(ConstantData.REMAINING_VACATION, args);
            return Either.error(new Error(message));
        }
        return Either.success(true);
    }

    private Either<Error, Boolean> validSeparationDay(String endDateBeforeVacation, String startDateCurrectVacation) {
        try {
            int diferenceDay = DateOperation.getBusinessDays(endDateBeforeVacation, startDateCurrectVacation, holidays);
            if (diferenceDay < ConstantData.SEPARATION_VACATION_DAY) {
                Object[] args = {ConstantData.SEPARATION_VACATION_DAY};
                String message = bundle.getMessage(ConstantData.SEPARATION_VACATION, args);
                return Either.error(new Error(message));
            }
            return Either.success(true);
        } catch (ParseException ex) {
            return Either.error(new Error(ex.getMessage()));
        }
    }

    private Either<Error, Boolean> alreadyExistingVacation(String startDateBeforeVacation, String endDateBeforeVacation, String startDateCurrectVacation) {
        try {
            if (!DateOperation.isLess(endDateBeforeVacation, startDateCurrectVacation)) {
                Object[] args = {startDateBeforeVacation, endDateBeforeVacation};
                String message = bundle.getMessage(ConstantData.ALREADY_EXISTING_VACATION, args);
                return Either.error(new Error(message));
            }
            return Either.success(true);
        } catch (ParseException ex) {
            return Either.error(new Error(ex.getMessage()));
        }
    }

    private Either<Error, Boolean> validSeparationSystem(String startDateVacation) {
        try {
            String currentDate = DateOperation.getDateCurrent();
            if (DateOperation.getBusinessDays(currentDate, startDateVacation, holidays) < ConstantData.SEPARATION_SYSTEM_DAY) {
                Object[] args = {ConstantData.SEPARATION_SYSTEM_DAY};
                String message = bundle.getMessage(ConstantData.ANTICIPATION_DAYS, args);
                return Either.error(new Error(message));
            }
            return Either.success(true);
        } catch (ParseException ex) {
            return Either.error(new Error(ex.getMessage()));
        }
    }

    public Either<Error, HistoryVacation> getInstanceHistoryVacation(long idEmployee,String startDate, String endDate, String reason) {
        try {
            int dayVacation = DateOperation.getBusinessDays(startDate, endDate, holidays);
            //return Either.success(new HistoryVacation(startDate, endDate, reason, dayVacation));
            return Either.success(new HistoryVacation(0,idEmployee, startDate, endDate, reason, dayVacation));
        } catch (ParseException ex) {
            return Either.error(new Error(ex.getMessage()));
        }
    }
}
