package com.company.logic;

import com.company.model.DayVacation;
import com.company.model.Employee;
import com.company.model.HistoryVacation;
import com.company.session.Connection;
import com.company.tools.ConstantData;
import com.company.util.Bundle;
import com.company.util.Either;
import com.company.util.Error;
import com.company.util.ObjectResponce;
import com.company.validation.EmployeeGet;
import com.company.validation.VacationCreate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author santiago.mamani
 */
public class HistoryVacationLogic {

    public ObjectResponce sendVacation(long idEmployee, String startDate, String endDate, String reason) {
        EmployeeGet employeeGet = new EmployeeGet();
        Either<Error, Boolean> employeVerify = employeeGet.complyCondition(idEmployee);
        if (employeVerify.error()) {
            return new ObjectResponce(Response.Status.BAD_REQUEST, employeVerify.getError());
        }
        Error error = new Error();
        VacationCreate vacationCreate = new VacationCreate();
        Either<Error, Boolean> verifyEmpty = vacationCreate.notEmpty(startDate, endDate);
        if (verifyEmpty.error()) {
            error.addAllErrors(verifyEmpty.getError());
        }
        Either<Error, Boolean> verifyReason = new Either<Error, Boolean>();
        if (StringUtils.isNotBlank(reason)) {
            verifyReason = vacationCreate.reasonValid(reason);
        }
        if (verifyReason.error()) {
            error.addAllErrors(verifyReason.getError());
        }
        if (verifyEmpty.error()) {
            return new ObjectResponce(Response.Status.BAD_REQUEST, error);
        }
        SqlSession session = null;
        try {
            session = new Connection().getSqlSession();
            Employee employee = session.selectOne(ConstantData.GET_BY_ID_EMPLOYEE, idEmployee);
            if (employee == null) {
                Bundle bundle = new Bundle();
                Object[] args = {bundle.getData(ConstantData.EMPLOYEE)};
                String message = bundle.getMessage(ConstantData.NOT_FOUND, args);
                return new ObjectResponce(Response.Status.NOT_FOUND, new Error(message));
            }
            Map<String, Object> filterData = new HashMap<String, Object>();
            filterData.put("idEmployee", idEmployee);
            filterData.put("year", DateOperation.getYearCurrent());
            DayVacation dayVacation = session.selectOne(ConstantData.GET_DAY_VACATION_BY_ID_AND_YEAR, filterData);
            vacationCreate.setDayVacation(dayVacation);
            List<HistoryVacation> historyVacations = session.selectList(ConstantData.GET_BY_ID_HISTORY_VACATION, idEmployee);
            if (!historyVacations.isEmpty()) {
                HistoryVacation historyVacationLast = historyVacations.get(historyVacations.size() - 1);
                vacationCreate.setHistoryVacation(historyVacationLast);
            }
            Either<Error, Boolean> complyCondition = vacationCreate.complyConditionDate(startDate, endDate);
            if (complyCondition.error()) {
                error.addAllErrors(complyCondition.getError());
            }
            if (!error.isEmpty()) {
                return new ObjectResponce(Response.Status.BAD_REQUEST, error);
            }
            return new ObjectResponce(Response.Status.CREATED);
        } catch (Exception e) {
            session.rollback();
            return new ObjectResponce(Response.Status.INTERNAL_SERVER_ERROR, new Error(e.getMessage()));
        } finally {
            session.close();
        }
    }
}
