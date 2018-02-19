package com.company.logic;

import com.company.util.Bundle;
import com.company.model.Employee;
import com.company.model.HistoryVacation;
import com.company.session.Connection;
import com.company.tools.ConstantData;
import com.company.util.Either;
import com.company.util.Error;
import com.company.util.ObjectResponce;
import com.company.validation.EmployeeGet;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author santiago.mamani
 */
public class EmployeeLogic {

    public ObjectResponce getEmployeeHistoryVacation(Long idEmployee) {
        EmployeeGet employeeGet = new EmployeeGet();
        Either<Error, Boolean> complyCondition = employeeGet.complyCondition(idEmployee);
        if (complyCondition.error()) {
            return new ObjectResponce(Response.Status.BAD_REQUEST, complyCondition.getError());
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
            List<HistoryVacation> historyVacations = session.selectList(ConstantData.GET_BY_ID_HISTORY_VACATION, idEmployee);
            employee.setHistoryVacations(historyVacations);
            return new ObjectResponce(Response.Status.OK, employee);
        } catch (Exception e) {
            return new ObjectResponce(Response.Status.INTERNAL_SERVER_ERROR, new Error(e.getMessage()));
        } finally {
            session.close();
        }
    }
}
