package io.edgedev.investment_calc;

import java.text.DecimalFormat;

import static io.edgedev.investment_calc.MainActivity.formatter;

/**
 * Created by OPEYEMI OLORUNLEKE on 6/2/2017.
 */

public class Month {

    private String monthIndex;
    private String balanceAtStart;
    private String balanceAtEnd;
    private String investment;
    private String interest;


    public Month() {
    }

    public String getMonthIndex() {
        return monthIndex;
    }

    public String getBalanceAtStart() {
        long xyz = (long) Float.parseFloat(balanceAtStart);
        return formatter.format(xyz);
    }

    public String getBalanceAtEnd() {
        long xyz = (long) Float.parseFloat(balanceAtEnd);
        return formatter.format(xyz);
    }

    public String getInvestment() {
        long xyz = (long) Float.parseFloat(investment);
        return formatter.format(xyz);
    }

    public String getInterest() {
        long xyz = (long) Float.parseFloat(interest);
        return formatter.format(xyz);
    }


    public void setMonthIndex(String monthIndex) {
        this.monthIndex = monthIndex;
    }

    public void setBalanceAtStart(String balanceAtStart) {
        this.balanceAtStart = balanceAtStart;
    }

    public void setBalanceAtEnd(String balanceAtEnd) {
        this.balanceAtEnd = balanceAtEnd;
    }

    public void setInvestment(String investment) {
        this.investment = investment;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}
