/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.validation;

import com.company.util.Either;
import com.company.util.ErrorContainer;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author santiago.mamani
 */
public class ValidationCreateTest {

    @Test
    public void testIsValidDate() {
        String startDate = "2017-02015";
        String endDate = "2017-02015";
        Either<ErrorContainer, Boolean> expected = Either.success(true);
        VacationCreate vacationCreate = mock(VacationCreate.class);
        when(vacationCreate.hasRemainingVacation(0)).thenReturn(expected);
        when(vacationCreate.separationDay(startDate)).thenReturn(expected);
        when(vacationCreate.validSeparationDay(endDate, startDate)).thenReturn(expected);
        when(vacationCreate.validSeparationSystem(startDate)).thenReturn(expected);
        Assert.assertEquals(vacationCreate.isValidDate(startDate, endDate),expected);
    }
}
