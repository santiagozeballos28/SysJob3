package com.company.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author santiago.mamani
 */
public class HistoryVacation {
    private long idHistoryVacation;
    private long idEmployee;
    private String startDate;
    private String endDate;
    private String reason;
    private int quantityDay;

    public HistoryVacation() {
    }

    public HistoryVacation(String startDate, String endDate, String reason, int quantityDay) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.quantityDay = quantityDay;
    }

    public HistoryVacation(long idHistoryVacation, long idEmployee, String startDate, String endDate, String reason, int quantityDay) {
        this.idHistoryVacation = idHistoryVacation;
        this.idEmployee = idEmployee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
        this.quantityDay = quantityDay;
    }

    public long getIdHistoryVacation() {
        return idHistoryVacation;
    }

    public long getIdEmployee() {
        return idEmployee;
    }
  
    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getReason() {
        return reason;
    }

    public int getQuantityDay() {
        return quantityDay;
    }

    public void setIdHistoryVacation(long idHistoryVacation) {
        this.idHistoryVacation = idHistoryVacation;
    }

    public void setIdEmployee(long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setQuantityDay(int quantityDay) {
        this.quantityDay = quantityDay;
    }
     @JsonIgnore
    public boolean isEmpty() {
        return startDate==null&&endDate==null;
    }
}
