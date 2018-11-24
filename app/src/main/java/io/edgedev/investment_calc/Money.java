package io.edgedev.investment_calc;

import android.os.Build;
import android.text.Html;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OPEYEMI OLORUNLEKE on 6/2/2017.
 */

public class Money {

    private String year, money, summary = "";
    private boolean isInvSummary;
    private List<Month> mMonth;

    public Money(boolean isInvSummary) {
        this.isInvSummary = isInvSummary;
        mMonth = new ArrayList<>();
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public List<Month> getMonth() {
        return mMonth;
    }

    public void setMonth(List<Month> month) {
        mMonth = month;
    }

    public CharSequence getSummary() {
        CharSequence str = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            str = (Html.fromHtml(summary, Html.FROM_HTML_MODE_COMPACT));
        else
            str = (Html.fromHtml(summary));
        return str;
//        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int showYearlyInv(){
        if (isInvSummary()) return View.INVISIBLE;
        else return View.VISIBLE;
    }
    public int showSummaryInv(){
        if (isInvSummary()) return View.VISIBLE;
        else return View.INVISIBLE;
    }

    public boolean isInvSummary() {
        return isInvSummary;
    }
}
