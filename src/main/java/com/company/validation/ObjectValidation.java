package com.company.validation;

import com.company.util.Bundle;
import com.company.tools.ConstantData;
import com.company.tools.ConstantKeyError;
import com.company.util.Either;
import com.company.util.Error;
import com.company.util.ErrorContainer;

/**
 *
 * @author santiago.mamani
 */
public class ObjectValidation {

    public void verifyIdentifier(Long identifier, ErrorContainer error) {
        if (!ObjectValidationUtil.isValidIdentifier(identifier)) {
            Object[] args = {Bundle.getData(ConstantData.IDENTIFIER), identifier};
            String message = Bundle.getMessage(ConstantData.MSG_NOT_VALID_IDENTIFIER, args);
            error.addError(new Error(ConstantKeyError.IDENTIFIER,message));
        }
    }

    public Either<ErrorContainer, Boolean> isUsAscii(String str) {
        if (!ObjectValidationUtil.isUsAscii(str)) {
            Object[] args = {str};
            String message = Bundle.getMessage(ConstantData.MSG_FORMAT_ASCII, args);
            return Either.errorContainer(new ErrorContainer(ConstantData.Status.BAD_REQUEST,new Error(ConstantKeyError.ASCII,message)));
        }
        return Either.success(true);
    }

    public Either<ErrorContainer, Boolean> isValidSize(String typeStr, String str, int min, int max) {
        if (str.length() < min) {
            Object[] args = {Bundle.getData(typeStr), str, Bundle.getData(ConstantData.SHORT), min, max};
            String message = Bundle.getMessage(ConstantData.MSG_SIZE_VALID, args);
            return Either.errorContainer(new ErrorContainer(ConstantData.Status.BAD_REQUEST,new Error(ConstantKeyError.SIZE,message)));
        }
        if (str.length() > max) {
            Object[] args = {Bundle.getData(typeStr), str, Bundle.getData(ConstantData.LONG), min, max};
            String message = Bundle.getMessage(ConstantData.MSG_SIZE_VALID, args);
            return Either.errorContainer(new ErrorContainer(ConstantData.Status.BAD_REQUEST,new Error(ConstantKeyError.SIZE,message)));
        }
        return Either.success(true);
    }
}
