package com.company.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author santiago.mamani
 */
public class HistoryVacation {

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
