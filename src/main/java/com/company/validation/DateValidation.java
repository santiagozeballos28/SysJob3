package com.company.validation;

import com.company.logic.DateOperation;
import com.company.tools.ConstantData;
import com.company.tools.ConstantData.Status;
import com.company.tools.ConstantKeyError;
import com.company.util.Bundle;
import com.company.util.Either;
import com.company.util.Error;
import com.company.util.ErrorContainer;
import java.text.ParseException;

/**
 *
 * @author santiago.mamani
 */
public class DateValidation {

    public Either<ErrorContainer, Boolean> isValidFormat(String startDate, String endDate) {
        ErrorContainer errorContainer = new ErrorContainer();
        if (!DateOperation.isValidDateFormat(startDate)) {
            Object[] args = {Bundle.getData(ConstantData.START_DATE), startDate, ConstantData.SIMPLE_DATE_FORMAT};
            String message = Bundle.getMessage(ConstantData.MSG_NOT_VALID_DATE_FORMAT, args);
            errorContainer.addError(new Error(ConstantKeyError.FOMRAT_DATE, message));
        }
        if (!DateOperation.isValidDateFormat(endDate)) {
            Object[] args = {Bundle.getData(ConstantData.END_DATE), endDate, ConstantData.SIMPLE_DATE_FORMAT};
            String message = Bundle.getMessage(ConstantData.MSG_NOT_VALID_DATE_FORMAT, args);
            errorContainer.addError(new Error(ConstantKeyError.ANTICIPATION_DAY, message));
        }
        if (errorContainer.hasError()) {
            errorContainer.setStatus(Status.BAD_REQUEST);
            return Either.errorContainer(errorContainer);
        }
        return Either.success(true);
    }

    public Either<ErrorContainer, Boolean> isLessOrEquals(String startDate, String endDate) {
        try {
            if (!DateOperation.isLessOrEquals(startDate, endDate)) {
                Object[] args = {startDate, endDate};
                String message = Bundle.getMessage(ConstantData.MSG_START_DATE_LESS_END_DATE, args);
                return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, new Error(ConstantKeyError.START_DATE_LESS_END_DATE, message)));
            }
            return Either.success(true);
        } catch (ParseException ex) {
            return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, new Error(ConstantKeyError.FOMRAT_DATE, ex.getMessage())));
        }
    }

    public Either<ErrorContainer, Boolean> isDateFuture(String typeDate, String date) {
        try {
            String dateCurrent = DateOperation.getDateCurrent();
            if (!DateOperation.isLess(dateCurrent, date)) {
                Object[] args = {Bundle.getData(typeDate), date, dateCurrent};
                String message = Bundle.getMessage(ConstantData.MSG_NOT_IS_FUTURE_DATE, args);
                return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, new Error(ConstantKeyError.FUTURE_DATE, message)));
            }
            return Either.success(true);
        } catch (ParseException ex) {
            return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, new Error(ConstantKeyError.FOMRAT_DATE, ex.getMessage())));
        }
    }

    public Either<ErrorContainer, Boolean> areSameYear(String startDate, String endDate) {
        try {
            if (!DateOperation.areSameYear(startDate, endDate)) {
                Object[] args = {startDate, endDate, DateOperation.getYearCurrent()};
                String message = Bundle.getMessage(ConstantData.MSG_NOT_SAME_YEAR, args);
                return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, new Error(ConstantKeyError.SAME_YEAR, message)));
            }
            return Either.success(true);
        } catch (ParseException ex) {
            return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, new Error(ConstantKeyError.FOMRAT_DATE, ex.getMessage())));
        }
    }
}
