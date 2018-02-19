package com.company.validation;

import com.company.util.Bundle;
import com.company.tools.ConstantData;
import com.company.util.Either;
import com.company.util.Error;

/**
 *
 * @author santiago.mamani
 */
public class ObjectValidation {

    private Bundle bundle;

    public ObjectValidation() {
        bundle = new Bundle();
    }

    public void verifyIdentifier(Long identifier, Error error) {
        if (!ObjectValidationUtil.isValidIdentifier(identifier)) {
            Object[] args = {bundle.getData(ConstantData.IDENTIFIER), identifier};
            String errorMessage = bundle.getMessage(ConstantData.IDENTIFIER_NOT_VALID, args);
            error.addError(errorMessage);
        }
    }

    public void verifyEmptyIdentifier(Long identifier, Error error) {
        if (identifier == null) {
            Object[] args = {bundle.getData(ConstantData.IDENTIFIER)};
            String errorMessage = bundle.getMessage(ConstantData.EMPTY, args);
            error.addError(errorMessage);
        }
    }

    public Either<Error, Boolean> isUsAscii(String str) {
        if (!ObjectValidationUtil.isUsAscii(str)) {
            Object[] args = {str};
            String message = bundle.getMessage(ConstantData.ASCII, args);
            return Either.error(new Error(message));
        }
        return Either.success(true);
    }
}
