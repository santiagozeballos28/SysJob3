package com.company.validation;

import com.company.util.Either;
import com.company.util.Error;

/**
 *
 * @author santiago.mamani
 */
public class EmployeeGet {

    private ObjectValidation objectValidation;

    public EmployeeGet() {
        objectValidation = new ObjectValidation();
    }

    public Either<Error, Boolean> complyCondition(Long identifier) {
        Either<Error, Boolean> either = new Either<Error, Boolean>();
        Error error = new Error();
        objectValidation.verifyEmptyIdentifier(identifier, error);
        if (!error.isEmpty()) {
            return Either.error(error);

        } else {
            objectValidation.verifyIdentifier(identifier, error);
            if (!error.isEmpty()) {
                return Either.error(error);
            }
        }
        return Either.success(true);
    }
}
