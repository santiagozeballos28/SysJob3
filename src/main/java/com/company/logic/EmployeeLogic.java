package com.company.logic;

import com.company.util.Bundle;
import com.company.model.Employee;
import com.company.model.HistoryVacation;
import com.company.session.MyBatisSqlSessionFactory;
import com.company.tools.ConstantData;
import com.company.tools.ConstantData.Status;
import com.company.tools.ConstantKeyError;
import com.company.util.Either;
import com.company.util.Error;
import com.company.util.ErrorContainer;
import com.company.validation.EmployeeGet;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author santiago.mamani
 */
public class EmployeeLogic {

    public Either<ErrorContainer, Employee> getEmployeeHistoryVacation(Long idEmployee) {
        EmployeeGet employeeGet = new EmployeeGet();
        Either<ErrorContainer, Boolean> complyCondition = employeeGet.complyCondition(idEmployee);
        if (complyCondition.errorContainer()) {
            return Either.errorContainer(complyCondition.getErrorContainer());
        }
        SqlSession session = MyBatisSqlSessionFactory.getSqlSessionFactory().openSession(true);
        try {
            Employee employee = session.selectOne(ConstantData.GET_BY_ID_EMPLOYEE, idEmployee);
            if (employee == null) {
                Object[] args = {Bundle.getData(ConstantData.EMPLOYEE)};
                String message = Bundle.getMessage(ConstantData.MSG_OBJECT_NOT_FOUND, args);
                return Either.errorContainer(new ErrorContainer(Status.NOT_FOUND, new Error(ConstantKeyError.NOT_FOUND, message)));
            }
            List<HistoryVacation> historyVacations = session.selectList(ConstantData.GET_BY_ID_EMPLOYEE_HISTORY_VACATION, idEmployee);
            employee.setHistoryVacations(historyVacations);
            return Either.success(employee);
        } catch (Exception e) {
            return Either.errorContainer(new ErrorContainer(Status.INTERNAL_SERVER_ERROR, new Error(ConstantKeyError.SERVER, e.getMessage())));
        } finally {
            session.close();
        }
    }
}
