package com.company.logic;

import com.company.model.DayVacation;
import com.company.model.Employee;
import com.company.model.VacationCompany;
import com.company.session.Connection;
import com.company.tools.ConstantData;
import com.company.util.ObjectResponce;
import com.company.util.Error;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author santiago.mamani
 */
public class DaysVacationLogic {

    public ObjectResponce fillVacationDays() {
        int yearCurrent = DateOperation.getYearCurrent();
        SqlSession session = null;
        try {
            session = new Connection().getSqlSession();
            List<Employee> employeesNotInDayVacations = session.selectList(ConstantData.EMPLOYEES_NOT_IN_DAY_VACATION, yearCurrent);
            if (employeesNotInDayVacations.isEmpty()) {
                return new ObjectResponce(Response.Status.CREATED);
            }
            List<VacationCompany> vacationCompanys = session.selectList(ConstantData.GET_ALL_VACATION_COMPANY);
            List<DayVacation> dayVacations = generateDaysVacation(employeesNotInDayVacations, vacationCompanys);
            session.insert(ConstantData.INSERT_DAY_VACATION_LIST, dayVacations);
            session.commit();
            return new ObjectResponce(Response.Status.CREATED);
        } catch (Exception e) {
            session.rollback();
            return new ObjectResponce(Response.Status.INTERNAL_SERVER_ERROR, new Error(e.getMessage()));
        } finally {
            session.close();
        }
    }

    private List<DayVacation> generateDaysVacation(List<Employee> employeesNotInDayVacations, List<VacationCompany> vacationCompanys) {
        List<DayVacation> dayVacations = new ArrayList<DayVacation>();
        int yearCurrent = DateOperation.getYearCurrent();
        for (Employee employee : employeesNotInDayVacations) {
            int yearEmployeeSeniority = DateOperation.diferenceYear(employee.getDateOfHire());
            int dasyVacation = getDayVacation(yearEmployeeSeniority, vacationCompanys);
            dayVacations.add(new DayVacation(employee.getIdEmployee(), yearCurrent, dasyVacation, dasyVacation));
        }
        return dayVacations;
    }

    private int getDayVacation(int yearEmployeeSeniority, List<VacationCompany> vacationCompanys) {
        VacationCompany vacationCompanyAux = new VacationCompany();
        boolean findSeniority = false;
        int i = 0;
        while (i < vacationCompanys.size() && !findSeniority) {
            vacationCompanyAux = vacationCompanys.get(i);
            if (yearEmployeeSeniority < vacationCompanyAux.getUpToYear()) {
                findSeniority = true;
            }
            i++;
        }
        return vacationCompanyAux.getDaysVacation();
    }
}
