package com.company.validation;

import com.company.logic.DateOperation;
import com.company.tools.ConstantData;
import com.company.util.Bundle;
import com.company.util.Either;
import com.company.util.Error;

/**
 *
 * @author santiago.mamani
 */
public class DateValidation {

    private Bundle bundle;

    public DateValidation() {
        bundle = new Bundle();
    }

    public Either<Error, Boolean> isValidFormat(String startDate, String endDate) {
        Error error = new Error();
        if (!DateOperation.isValidDateFormat(startDate)) {
            Object[] args = {bundle.getData(ConstantData.START_DATE),startDate,ConstantData.SIMPLE_DATE_FORMAT};
            String message = bundle.getMessage(ConstantData.NOT_VALID_DATE, args);
            error.addError(message);
        }
        if (!DateOperation.isValidDateFormat(endDate)) {
            Object[] args = {bundle.getData(ConstantData.END_DATE),endDate,ConstantData.SIMPLE_DATE_FORMAT};
            String message = bundle.getMessage(ConstantData.NOT_VALID_DATE, args);
            error.addError(message);
        }
        if (error.isEmpty()) {
            return Either.success(true);
        }
        return Either.error(error);
    }

    public Either<Error, Boolean> isLess(String startDate, String endDate) {
        if (!DateOperation.isLess(startDate, endDate)) {
            Object[] args = {startDate, endDate};
            String message = bundle.getMessage(ConstantData.START_DATE_LESS_END_DATE, args);
            return Either.error(new Error(message));
        }
        return Either.success(true);
    }

    public Either<Error, Boolean> areSameYear(String startDate, String endDate) {
        if (!DateOperation.areSameYear(endDate, endDate)) {
            Object[] args = {DateOperation.getYearCurrent()};
            String message = bundle.getMessage(ConstantData.SAME_YEAR, args);
            return Either.error(new Error(message));
        }
        return Either.success(true);
    }
}
