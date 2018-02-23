package com.company.logic;

import com.company.model.DayVacation;
import com.company.model.Employee;
import com.company.model.HistoryVacation;
import com.company.model.Holiday;
import com.company.model.Mail;
import com.company.session.MyBatisSqlSessionFactory;
import com.company.tools.ConstantData;
import com.company.tools.ConstantData.Status;
import com.company.tools.ConstantKeyError;
import com.company.util.Bundle;
import com.company.util.Either;
import com.company.util.ErrorContainer;
import com.company.util.Error;
import com.company.validation.EmployeeGet;
import com.company.validation.VacationCreate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author santiago.mamani
 */
public class HistoryVacationLogic {

    public Either<ErrorContainer, HistoryVacation> sendVacation(long idEmployee, String startDate, String endDate, String reason) {
        EmployeeGet employeeGet = new EmployeeGet();
        Either<ErrorContainer, Boolean> employeVerify = employeeGet.complyCondition(idEmployee);
        if (employeVerify.errorContainer()) {
            return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, employeVerify.getErrorContainer().getErrors()));
        }
        ErrorContainer error = new ErrorContainer();
        VacationCreate vacationCreate = new VacationCreate();
        Either<ErrorContainer, Boolean> verifyEmpty = vacationCreate.notEmpty(startDate, endDate);
        if (verifyEmpty.errorContainer()) {
            error.addAllErrors(verifyEmpty.getErrorContainer());
        }
        Either<ErrorContainer, Boolean> verifyReason = new Either<ErrorContainer, Boolean>();
        if (StringUtils.isNotBlank(reason)) {
            verifyReason = vacationCreate.isValidReason(reason);
        }
        if (verifyReason.errorContainer()) {
            error.addAllErrors(verifyReason.getErrorContainer());
        }
        if (verifyEmpty.errorContainer()) {
            return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, error.getErrors()));
        }
        SqlSession session = MyBatisSqlSessionFactory.getSqlSessionFactory().openSession(false);
        try {
            Employee employee = session.selectOne(ConstantData.GET_BY_ID_EMPLOYEE, idEmployee);
            if (employee == null) {
                Object[] args = {Bundle.getData(ConstantData.EMPLOYEE)};
                String message = Bundle.getMessage(ConstantData.MSG_OBJECT_NOT_FOUND, args);
                return Either.errorContainer(new ErrorContainer(Status.NOT_FOUND, new Error(ConstantKeyError.NOT_FOUND, message)));
            }
            Map<String, Object> filterData = new HashMap<String, Object>();
            filterData.put("idEmployee", idEmployee);
            filterData.put("year", DateOperation.getYearCurrent());
            DayVacation dayVacation = session.selectOne(ConstantData.GET_DAY_VACATION_BY_ID_AND_YEAR, filterData);
            vacationCreate.setDayVacation(dayVacation);
            List<HistoryVacation> historyVacations = session.selectList(ConstantData.GET_BY_ID_EMPLOYEE_HISTORY_VACATION, idEmployee);
            if (!historyVacations.isEmpty()) {
                HistoryVacation historyVacationLast = historyVacations.get(historyVacations.size() - 1);
                vacationCreate.setHistoryVacation(historyVacationLast);
            }
            List<Holiday> holidays = session.selectList(ConstantData.GET_ALL_HOLIDAY);
            vacationCreate.setHoliday(holidays);
            Either<ErrorContainer, Boolean> complyCondition = vacationCreate.isValidDate(startDate, endDate);
            if (complyCondition.errorContainer()) {
                error.addAllErrors(complyCondition.getErrorContainer());
            }
            if (error.hasError()) {
                return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, error.getErrors()));
            }
            Either<ErrorContainer, HistoryVacation> getHistoryVacation = vacationCreate.getInstanceHistoryVacation(idEmployee, startDate, endDate, reason);
            if (getHistoryVacation.errorContainer()) {
                return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, getHistoryVacation.getErrorContainer().getErrors()));
            }
            HistoryVacation newHistoryVacation = getHistoryVacation.getSuccess();
            session.insert(ConstantData.INSERT_HISTORY_VACATION, newHistoryVacation);
            DayVacation updateDayVacation = generateDayVacationToUpdate(idEmployee, dayVacation.getVacationRemaining(), newHistoryVacation.getQuantityDay());
            session.update(ConstantData.UPDATE_DAY_VACATION, updateDayVacation);
            HistoryVacation historyVacationInserted = session.selectOne(ConstantData.GET_BY_ID_EMPLOYEE_AND_DATE, newHistoryVacation);
            Mail mail = generateMail(employee.getEmail().trim(), startDate, endDate, updateDayVacation.getVacationRemaining());
            SendMail sendMail = new SendMail();
            Either<ErrorContainer, Boolean> sendMailRes = sendMail.sendMail(mail);
            if (sendMailRes.errorContainer()) {
                throw new Exception(sendMailRes.getErrorContainer().getErrors().get(0).getMessage());
            }
            session.commit();
            return Either.success(historyVacationInserted);
        } catch (Exception e) {
            session.rollback();
            return Either.errorContainer(new ErrorContainer(Status.INTERNAL_SERVER_ERROR, new Error(ConstantKeyError.SERVER_SYSJOB, e.getMessage())));
        } finally {
            session.close();
        }
    }

    private DayVacation generateDayVacationToUpdate(long idEmployee, int vacationRemaining, int quantityDay) {
        int vacationRemainingNew = vacationRemaining - quantityDay;
        return new DayVacation(idEmployee, null, null, vacationRemainingNew);
    }

    private Mail generateMail(String emailEmployee, String startDate, String endDate, int vacationRemaining) {
        Object[] argsPeriod = {startDate, endDate};
        String messagePeriod = Bundle.getMessage(ConstantData.MSG_VACATION_PERIOD, argsPeriod);
        Object[] argsRemaining = {vacationRemaining};
        String messageRemaining = Bundle.getMessage(ConstantData.MSG_VACATION_REMAINING, argsRemaining);
        CreateMail createMail = new CreateMail();
        return createMail.getInstanceMail(emailEmployee, Bundle.getData(ConstantData.NEW_VACATION).toUpperCase(), messagePeriod + "\n" + messageRemaining);
    }
}
