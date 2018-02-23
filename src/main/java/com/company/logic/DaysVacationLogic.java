package com.company.logic;

import com.company.model.DayVacation;
import com.company.model.Employee;
import com.company.model.VacationCompany;
import com.company.session.MyBatisSqlSessionFactory;
import com.company.tools.ConstantData;
import com.company.tools.ConstantData.Status;
import com.company.tools.ConstantKeyError;
import com.company.util.Either;
import com.company.util.Error;
import com.company.util.ErrorContainer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author santiago.mamani
 */
public class DaysVacationLogic {

    public Either<ErrorContainer, Boolean> fillVacationDays() {
        int yearCurrent = DateOperation.getYearCurrent();
        SqlSession session = MyBatisSqlSessionFactory.getSqlSessionFactory().openSession(false);
        try {
            List<Employee> employeesNotInDayVacations = session.selectList(ConstantData.EMPLOYEES_NOT_IN_DAY_VACATION, yearCurrent);
            if (employeesNotInDayVacations.isEmpty()) {
                return Either.success(true);
            }
            List<VacationCompany> vacationCompanys = session.selectList(ConstantData.GET_ALL_VACATION_COMPANY);
            Either<ErrorContainer, List<DayVacation>> dayVacations = generateDaysVacation(employeesNotInDayVacations, vacationCompanys);
            session.insert(ConstantData.INSERT_DAY_VACATION_LIST, dayVacations.getSuccess());
            session.commit();
            return Either.success(true);
        } catch (Exception e) {
            session.rollback();
            return Either.errorContainer(new ErrorContainer(Status.INTERNAL_SERVER_ERROR, new Error(ConstantKeyError.SERVER, e.getMessage())));
        } finally {
            session.close();
        }
    }

    private Either<ErrorContainer, List<DayVacation>> generateDaysVacation(List<Employee> employeesNotInDayVacations, List<VacationCompany> vacationCompanys) {
        //Try cache is added because the DateOperation.diferenceYear() method can throw an errorContainer.
        try {
            List<DayVacation> dayVacations = new ArrayList<DayVacation>();
            int yearCurrent = DateOperation.getYearCurrent();
            for (Employee employee : employeesNotInDayVacations) {
                int yearEmployeeSeniority = DateOperation.diferenceYear(employee.getDateOfHire());
                int dasyVacation = getDayVacation(yearEmployeeSeniority, vacationCompanys);
                dayVacations.add(new DayVacation(employee.getIdEmployee(), yearCurrent, dasyVacation, dasyVacation));
            }
            return Either.success(dayVacations);
        } catch (ParseException ex) {
            return Either.errorContainer(new ErrorContainer(Status.BAD_REQUEST, new Error(ConstantKeyError.FOMRAT_DATE, ex.getMessage())));
        }
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
