package com.company.model;

/**
 *
 * @author santiago.mamani
 */
public class DayVacation {

    private long idEmployee;
    private Integer year;
    private Integer vacationInitial;
    private Integer vacationRemaining;

    public DayVacation() {
    }

    public DayVacation(long idEmployee, Integer year, Integer vacationInitial, Integer vacationRemaining) {
        this.idEmployee = idEmployee;
        this.year = year;
        this.vacationInitial = vacationInitial;
        this.vacationRemaining = vacationRemaining;
    }

    public long getIdEmployee() {
        return idEmployee;
    }

    public int getYear() {
        return year;
    }

    public int getVacationInitial() {
        return vacationInitial;
    }

    public int getVacationRemaining() {
        return vacationRemaining;
    }

    public void setIdEmployee(long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setVacationInitial(Integer vacationInitial) {
        this.vacationInitial = vacationInitial;
    }

    public void setVacationRemaining(Integer vacationRemaining) {
        this.vacationRemaining = vacationRemaining;
    }
}
