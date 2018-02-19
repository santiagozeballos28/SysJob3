package com.company.model;

/**
 *
 * @author santiago.mamani
 */
public class DayVacation {

    private long idEmployee;
    private int year;
    private int vacationInitial;
    private int vacationRemaining;

    public DayVacation() {
    }

    public DayVacation(long idEmployee, int year, int vacationInitial, int vacationRemaining) {
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

    public void setYear(int year) {
        this.year = year;
    }

    public void setVacationInitial(int vacationInitial) {
        this.vacationInitial = vacationInitial;
    }

    public void setVacationRemaining(int vacationRemaining) {
        this.vacationRemaining = vacationRemaining;
    }
}
