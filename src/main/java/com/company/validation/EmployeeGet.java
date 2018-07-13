package com.company.validation;

import com.company.tools.ConstantData.Status;
import com.company.util.Either;
import com.company.util.ErrorContainer;

/**
 *
 * @author santiago.mamani
 */
public class EmployeeGet {

    private ObjectValidation objectValidation;

    public EmployeeGet() {
        objectValidation = new ObjectValidation();
    }

    public Either<ErrorContainer, Boolean> complyCondition(Long identifier) {
        Either<ErrorContainer, Boolean> either = new Either<ErrorContainer, Boolean>();
        ErrorContainer errorContainer = new ErrorContainer();
        objectValidation.verifyIdentifier(identifier, errorContainer);
        if (errorContainer.hasError()) {
            errorContainer.setStatus(Status.BAD_REQUEST);
            return Either.errorContainer(errorContainer);
        }
        return Either.success(true);
    }
}
