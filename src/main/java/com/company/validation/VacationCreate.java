package com.company.validation;

import com.company.logic.DateOperation;
import com.company.model.DayVacation;
import com.company.model.HistoryVacation;
import com.company.model.Holiday;
import com.company.util.Bundle;
import com.company.tools.ConstantData;
import com.company.tools.ConstantData.Status;
import com.company.tools.ConstantKeyError;
import com.company.util.Either;
import com.company.util.ErrorContainer;
import com.company.util.Error;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author santiago.mamani
 */
public class VacationCreate {

    private DayVacation dayVacation;
    private HistoryVacation historyVacation;
    private List<String> holidays;
    private DateValidation dateValidation;
    private ObjectValidation objectValidation;

    public VacationCreate() {
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

    public Either<ErrorContainer, Boolean> notEmpty(String startDate, String endDate) {
        ErrorContainer errorContainer = new ErrorContainer();
        if (StringUtils.isBlank(startDate)) {
            Object[] args = {Bundle.getData(ConstantData.START_DATE)};
            String message = Bundle.getMessage(ConstantData.MSG_ERROR_VALIDATION_REQUIRED, args);
            errorContainer.addError(new Error(ConstantKeyError.EMPTY, message));
        }
        if (StringUtils.isBlank(endDate)) {
            Object[] args = {Bundle.getData(ConstantData.END_DATE)};
            String message = Bundle.getMessage(ConstantData.MSG_ERROR_VALIDATION_REQUIRED, args);
            errorContainer.addError(new Error(ConstantKeyError.EMPTY, message));
        }
        if (errorContainer.hasError()) {
            errorContainer.setStatus(Status.BAD_REQUEST);
            return Either.errorContainer(errorContainer);
        }
        return Either.success(true);
    }

    public Either<ErrorContainer, Boolean> isValidReason(String reason) {
        ErrorContainer errorContainer = new ErrorContainer();
        Either<ErrorContainer, Boolean> isUsAscii = objectValidation.isUsAscii(reason.trim());
        if (isUsAscii.errorContainer()) {
            errorContainer.addAllErrors(isUsAscii.getErrorContainer().getErrors());
        }
        Either<ErrorContainer, Boolean> isValidSize = objectValidation.isValidSize(ConstantData.REASON, reason.trim(), ConstantData.MIN_LENGTH, ConstantData.MAX_LENGTH);
        if (isValidSize.errorContainer()) {
            errorContainer.addAllErrors(isValidSize.getErrorContainer().getErrors());
        }
        if (errorContainer.hasError()) {
            errorContainer.setStatus(Status.BAD_REQUEST);
            return Either.errorContainer(errorContainer);
        }
        return Either.success(true);
    }

    public Either<ErrorContainer, Boolean> isValidDate(String startDate, String endDate) {
        Either<ErrorContainer, Boolean> resValidDate = dateValidation.isValidFormat(startDate, endDate);
        if (resValidDate.errorContainer()) {
            return resValidDate;//If the date formats are not valid, an errorContainer is returned
        }
        ErrorContainer errorContainer = new ErrorContainer();
        boolean validStartDate = true;
        boolean validEndDate = true;
        resValidDate = dateValidation.isDateFuture(ConstantData.START_DATE, startDate);
        if (resValidDate.errorContainer()) {
            validStartDate = false;
            errorContainer.addAllErrors(resValidDate.getErrorContainer());
        } else {
            resValidDate = dateValidation.isThisYear(ConstantData.START_DATE, startDate);
            if (resValidDate.errorContainer()) {
                validStartDate = false;
                errorContainer.addAllErrors(resValidDate.getErrorContainer());
            }
        }
        resValidDate = dateValidation.isDateFuture(ConstantData.END_DATE, endDate);
        if (resValidDate.errorContainer()) {
            validEndDate = false;
            errorContainer.addAllErrors(resValidDate.getErrorContainer());
        } else {
            resValidDate = dateValidation.isThisYear(ConstantData.END_DATE, endDate);
            if (resValidDate.errorContainer()) {
                validEndDate = false;
                errorContainer.addAllErrors(resValidDate.getErrorContainer());
            }
        }
        if (validStartDate && validEndDate) {
            resValidDate = dateValidation.isLessOrEquals(startDate, endDate);
            if (resValidDate.errorContainer()) {
                errorContainer.addAllErrors(resValidDate.getErrorContainer());
            }
        }
        if (errorContainer.hasError()) {
            //If the start date is greater than the final date or they are not from the same year, the errorContainer is returned.
            return Either.errorContainer(errorContainer);
        }
        resValidDate = validSeparationSystem(startDate);
        if (resValidDate.errorContainer()) {
            errorContainer.addAllErrors(resValidDate.getErrorContainer());
        }
        resValidDate = separationDay(startDate);
        if (resValidDate.errorContainer()) {
            errorContainer.addAllErrors(resValidDate.getErrorContainer());
        }
        try {
            int numberDaysRequested = DateOperation.getBusinessDays(startDate, endDate, holidays);
            resValidDate = hasRemainingVacation(numberDaysRequested);
            if (resValidDate.errorContainer()) {
                errorContainer.addAllErrors(resValidDate.getErrorContainer());
            }
        } catch (ParseException ex) {
            errorContainer.addError(new Error(ConstantKeyError.FOMRAT_DATE, ex.getMessage()));
        }
        if (errorContainer.hasError()) {
            errorContainer.setStatus(Status.BAD_REQUEST);
            return Either.errorContainer(errorContainer);
        }
        return Either.success(true);
    }

    private Either<ErrorContainer, Boolean> separationDay(String startDate) {
        if (historyVacation.isEmpty()) {
            return Either.success(true);
        }
        try {
            boolean historyVacationSameYear = DateOperation.areSameYear(historyVacation.getEndDate(), startDate);
            ErrorContainer errorContainer = new ErrorContainer();
            if (historyVacationSameYear) {
                Either<ErrorContainer, Boolean> existingVacation = alreadyExistingVacation(historyVacation.getStartDate(), historyVacation.getEndDate(), startDate);
                if (existingVacation.errorContainer()) {
                    errorContainer.addAllErrors(existingVacation.getErrorContainer());
                }
                Either<ErrorContainer, Boolean> separationDays = validSeparationDay(historyVacation.getEndDate(), startDate);
                if (separationDays.errorContainer()) {
                    errorContainer.addAllErrors(separationDays.getErrorContainer());
                }
            }
            if (errorContainer.hasError()) {
                errorContainer.setStatus(Status.BAD_REQUEST);
                return Either.errorContainer(errorContainer);
            }
            return Either.success(true);
        } catch (ParseException ex) {
            return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, new Error(ConstantKeyError.FOMRAT_DATE, ex.getMessage())));
        }
    }

    private Either<ErrorContainer, Boolean> hasRemainingVacation(int numberDaysRequested) {
        if (dayVacation.getVacationRemaining() == 0) {
            Object[] args = {};
            String message = Bundle.getMessage(ConstantData.MSG_CAN_NOT_VACATION, args);
            return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, new Error(ConstantKeyError.REMAIMING, message)));
        }
        if (numberDaysRequested > dayVacation.getVacationRemaining()) {
            Object[] args = {numberDaysRequested, dayVacation.getVacationRemaining()};
            String message = Bundle.getMessage(ConstantData.MSG_REMAINING_VACATION, args);
            return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, new Error(ConstantKeyError.REMAIMING, message)));
        }
        return Either.success(true);
    }

    private Either<ErrorContainer, Boolean> validSeparationDay(String endDateBeforeVacation, String startDateCurrectVacation) {
        try {
            int diferenceDay = DateOperation.getBusinessDays(endDateBeforeVacation, startDateCurrectVacation, holidays) - 1;
            if (diferenceDay <= ConstantData.SEPARATION_VACATION_DAY) {
                Object[] args = {ConstantData.SEPARATION_VACATION_DAY};
                String message = Bundle.getMessage(ConstantData.MSG_SEPARATION_VACATION, args);
                return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, new Error(ConstantKeyError.SEPARATION_VACTION, message)));
            }
            return Either.success(true);
        } catch (ParseException ex) {
            return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, new Error(ConstantKeyError.FOMRAT_DATE, ex.getMessage())));
        }
    }

    private Either<ErrorContainer, Boolean> alreadyExistingVacation(String startDateBeforeVacation, String endDateBeforeVacation, String startDateCurrectVacation) {
        try {
            if (!DateOperation.isLess(endDateBeforeVacation, startDateCurrectVacation)) {
                Object[] args = {startDateBeforeVacation, endDateBeforeVacation};
                String message = Bundle.getMessage(ConstantData.MSG_ALREADY_EXISTING_VACATION, args);
                return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, new Error(ConstantKeyError.EXISTING_VACTION, message)));
            }
            return Either.success(true);
        } catch (ParseException ex) {
            return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, new Error(ConstantKeyError.FOMRAT_DATE, ex.getMessage())));
        }
    }

    private Either<ErrorContainer, Boolean> validSeparationSystem(String startDateVacation) {
        try {
            String currentDate = DateOperation.getDateCurrent();
            int daySeparationSystem = DateOperation.getBusinessDays(currentDate, startDateVacation, holidays) - 1;
            if (daySeparationSystem <= ConstantData.SEPARATION_SYSTEM_DAY) {
                Object[] args = {ConstantData.SEPARATION_SYSTEM_DAY};
                String message = Bundle.getMessage(ConstantData.MSG_ANTICIPATION_DAYS_SYSTEM, args);
                return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, new Error(ConstantKeyError.ANTICIPATION_DAY, message)));
            }
            return Either.success(true);
        } catch (ParseException ex) {
            return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, new Error(ConstantKeyError.FOMRAT_DATE, ex.getMessage())));
        }
    }

    public Either<ErrorContainer, HistoryVacation> getInstanceHistoryVacation(long idEmployee, String startDate, String endDate, String reason) {
        try {
            int dayVacation = DateOperation.getBusinessDays(startDate, endDate, holidays);
            return Either.success(new HistoryVacation(0, idEmployee, startDate, endDate, reason, dayVacation));
        } catch (ParseException ex) {
            return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, new Error(ConstantKeyError.FOMRAT_DATE, ex.getMessage())));
        }
    }
}
