package io.edgedev.investment_calc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OPEYEMI OLORUNLEKE on 6/2/2017.
 */

public class Singleton {

    public List<Money> mMoneyList = new ArrayList<>();
    private static Singleton mSingleton;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (mSingleton == null){
            mSingleton = new Singleton();
        }
        return mSingleton;
    }

    public void addMoney(Money money) {
        mMoneyList.add(money);
    }

    public Money findMoney(String year) {
        for (Money money : mMoneyList) {
            if (money.getYear().equalsIgnoreCase(year)) {
                return money;
            }
        }
        return null;
    }

    public List<Money> getMoneyList() {
        return mMoneyList;
    }
}
