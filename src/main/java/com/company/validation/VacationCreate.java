package com.company.validation;

import com.company.logic.DateOperation;
import com.company.model.DayVacation;
import com.company.model.HistoryVacation;
import com.company.model.VacationCompany;
import com.company.util.Bundle;
import com.company.tools.ConstantData;
import com.company.util.Either;
import com.company.util.Error;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author santiago.mamani
 */
public class VacationCreate {

    private Bundle bundle;
    private DayVacation dayVacation;
    private HistoryVacation historyVacation;
    private List<VacationCompany> vacationsCompany;
    private DateValidation dateValidation;
    private ObjectValidation objectValidation;

    public VacationCreate() {
        bundle = new Bundle();
        dateValidation = new DateValidation();
        objectValidation = new ObjectValidation();
        historyVacation = new HistoryVacation();
        dayVacation = new DayVacation();
    }

    public VacationCreate(DayVacation dayVacation, HistoryVacation historyVacation, List<VacationCompany> vacationsCompany) {
        this.dayVacation = dayVacation;
        this.historyVacation = historyVacation;
        this.vacationsCompany = vacationsCompany;
        bundle = new Bundle();
    }

    public void setDayVacation(DayVacation dayVacation) {
        this.dayVacation = dayVacation;
    }

    public void setHistoryVacation(HistoryVacation historyVacation) {
        this.historyVacation = historyVacation;
    }

    public void setVacationsCompany(List<VacationCompany> vacationsCompany) {
        this.vacationsCompany = vacationsCompany;
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
        return objectValidation.isUsAscii(reason.trim());
    }

    public Either<Error, Boolean> complyConditionDate(String startDate, String endDate) {
        Error error = new Error();
        Either<Error, Boolean> resValidDate = dateValid(startDate, endDate);
        if (resValidDate.error()) {
            return resValidDate;
        }
        int numberDaysRequested = DateOperation.diferenceDays(startDate, endDate);
        resValidDate = hasRemainingVacation(numberDaysRequested);
        if (resValidDate.error()) {
            error.addAllErrors(resValidDate.getError());
        }

        if (!error.isEmpty()) {
            return Either.error(error);
        }
        return Either.success(true);
    }

    private Either<Error, Boolean> dateValid(String startDate, String endDate) {
        Either<Error, Boolean> resValidDate = dateValidation.isValidFormat(startDate, endDate);
        if (resValidDate.error()) {
            return resValidDate;
        }
        resValidDate = dateValidation.isLess(startDate, endDate);
        Error errorRes = new Error();
        if (resValidDate.error()) {
            errorRes.addAllErrors(resValidDate.getError());
        }
        resValidDate = dateValidation.areSameYear(startDate, endDate);
        if (resValidDate.error()) {
            errorRes.addAllErrors(resValidDate.getError());
        } else {
            resValidDate = separationDay(startDate);
            if (resValidDate.error()) {
                errorRes.addAllErrors(resValidDate.getError());
            }
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
        boolean historyVacationSameYear = DateOperation.areSameYear(historyVacation.getEndDate(), startDate);
        if (historyVacationSameYear) {
            return validSeparationDay(historyVacation.getEndDate(), startDate);
        }
        return Either.success(true);
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
        int diferenceDay = DateOperation.diferenceDays(endDateBeforeVacation, startDateCurrectVacation);
        if (diferenceDay < ConstantData.SEPARATION_VACATION_DAY) {
            Object[] args = {ConstantData.SEPARATION_VACATION_DAY};
            String message = bundle.getMessage(ConstantData.SEPARATION_VACATION, args);
            return Either.error(new Error(message));
        }
        return Either.success(true);
    }
}
