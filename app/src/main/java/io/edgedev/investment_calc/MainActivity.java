package io.edgedev.investment_calc;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import io.edgedev.investment_calc.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.Callback {

    private static final String LUMP_SUM_KEY = "sum";
    private static final String MONTH_KEY = "month";
    private static final String INTEREST_PER_ANNUM_KEY = "interest";
    private static final String TENURE_KEY = "tenure";
    private static final String TAG = "MainActivity";
    public static DecimalFormat formatter = new DecimalFormat("#,###.##");
    private ActivityMainBinding binding;
    private EditText initialInvestmentEditTxt, interestEditTxt, investmentTenureEditTxt;
    private Money money;
    private EditText monthlyInvestmentEditTxt;
    private long theFinal;
    private AdView mAdView;
    private String intREST;
    private TinyDB lumpsum, monthlySum, interestPerYear, tenureInYears;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onPause() {
        super.onPause();
        mAdView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdView.destroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-9297690518647609~1456090179");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_main_activity));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });


        lumpsum = new TinyDB(this);
        monthlySum = new TinyDB(this);
        interestPerYear = new TinyDB(this);
        tenureInYears = new TinyDB(this);

        Toolbar toolbar = binding.toolbar2;
        setSupportActionBar(toolbar);

        mAdView = binding.adView;
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        RecyclerView recyclerView = binding.recyclerView;

        final RecyclerViewAdapter adapter = new RecyclerViewAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        initialInvestmentEditTxt = binding.initialInvestment;
        interestEditTxt = binding.percentEditText;
        investmentTenureEditTxt = binding.investmentTenure;
        monthlyInvestmentEditTxt = binding.monthlyInvestment;

        String one = lumpsum.getString(LUMP_SUM_KEY);
        String two = monthlySum.getString(MONTH_KEY);
        String three = interestPerYear.getString(INTEREST_PER_ANNUM_KEY);
        String four = tenureInYears.getString(TENURE_KEY);

        initialInvestmentEditTxt.setText(one);
        monthlyInvestmentEditTxt.setText(two);
        interestEditTxt.setText(three);
        investmentTenureEditTxt.setText(four);


        binding.calcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.mList.clear();
                adapter.notifyDataSetChanged();

                if (TextUtils.isEmpty(initialInvestmentEditTxt.getText().toString())
                        || TextUtils.isEmpty(interestEditTxt.getText().toString())
                        || TextUtils.isEmpty(investmentTenureEditTxt.getText().toString())) {
                    Snackbar.make(binding.getRoot(), "Pls, Input Required Field(s)", Snackbar.LENGTH_LONG).show();
                    return;
                }

                if (mInterstitialAd.isLoaded()) mInterstitialAd.show();

                String monthlyAdditions;
                if (TextUtils.isEmpty(monthlyInvestmentEditTxt.getText().toString())) {
                    monthlyAdditions = "0,000";
                } else {
                    monthlyAdditions = monthlyInvestmentEditTxt.getText().toString();
                }


                Singleton.getInstance().mMoneyList.clear();
                String initialInvestment = initialInvestmentEditTxt.getText().toString();
                String initialPurified = initialInvestment.replace(",", "");

                Log.d(TAG, "onClick: " + monthlyAdditions);
                String monthlyPurified = monthlyAdditions.replace(",", "");

                float initialCapital = Float.parseFloat(initialPurified);
                float monthlyCapital = Float.parseFloat(monthlyPurified);

                intREST = interestEditTxt.getText().toString();

                float yearlyInterestRate = (Float.parseFloat(intREST) / 100);
                float monthlyInterestRate = (yearlyInterestRate / 12);

                int years = Integer.parseInt(investmentTenureEditTxt.getText().toString());

                for (int i = 0; i < years; i++) {
                    money = new Money(false);

                    Log.i(TAG, "**********************************************");

                    money.setYear("Year " + (1 + i));

                    List<Month> months = new ArrayList<>();

                    for (int m = 1; m < 13; m++) {
                        Month month = new Month();
                        month.setMonthIndex("" + m);
                        double monthInterest;
                        if (i == 0 && m == 1) {
                            monthlyCapital = 0;
                            monthInterest = initialCapital * monthlyInterestRate;
                        } else {
                            monthlyCapital = Float.parseFloat(monthlyPurified);
                            monthInterest = (initialCapital + monthlyCapital) * monthlyInterestRate;
                        }
                        month.setBalanceAtStart("" + initialCapital);
                        Log.i(TAG, "BalanceAtStart " + initialCapital);

                        Log.i(TAG, "Interest " + monthInterest);
                        month.setInterest("" + monthInterest);

                        month.setInvestment("" + monthlyCapital);
                        Log.i(TAG, "Investment " + monthlyCapital);

                        theFinal = (long) (initialCapital += monthInterest + monthlyCapital);
                        month.setBalanceAtEnd("" + theFinal);
                        Log.i(TAG, "BalanceAtEnd " + theFinal);

                        Log.i(TAG, "******************************************");

                        months.add(month);
                    }
                    money.setMoney(formatter.format(theFinal));
                    Log.i(TAG, "Year END = " + theFinal);
                    Log.i(TAG, "**********************************************");


                    money.setMonth(months);
                    Singleton.getInstance().addMoney(money);
                    adapter.addMoney(money);

                    lumpsum.putString(LUMP_SUM_KEY, initialInvestment);
                    monthlySum.putString(MONTH_KEY, monthlyAdditions);
                    interestPerYear.putString(INTEREST_PER_ANNUM_KEY, intREST);
                    tenureInYears.putString(TENURE_KEY, "" + years);

                }
                float initPrincipal = Float.parseFloat(initialPurified);
                float diff_ = theFinal - initPrincipal;
                float percentageDifference = ((diff_ / initPrincipal) * 100);

                money = new Money(true);
                String summ = String.format(
                        getString(R.string.investment_summary),
                        initialInvestment, intREST, monthlyAdditions, years, formatter.format(theFinal), formatter.format(percentageDifference)
                );
                money.setSummary(summ);
                adapter.addMoney(money);

            }
        });
        initialInvestmentEditTxt.addTextChangedListener(new NumberTextWatcherForThousand(initialInvestmentEditTxt));
        monthlyInvestmentEditTxt.addTextChangedListener(new NumberTextWatcherForThousand(monthlyInvestmentEditTxt));
    }

    @Override
    public void clicked(String year) {
        Intent intent = MoneyPagerActivity.Companion.newIntent(this, year, intREST);
        startActivity(intent);
    }

    @Override
    public void share(String message) {
        ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setSubject(getString(R.string.app_name))
                .setText(message + "\n\n" + getString(R.string.little_extra) + "\n" + getString(R.string.app_link))
                .startChooser();
    }

    public class NumberTextWatcherForThousand implements TextWatcher {

        EditText editText;


        public NumberTextWatcherForThousand(EditText editText) {
            this.editText = editText;


        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                editText.removeTextChangedListener(this);
                String value = editText.getText().toString();


                if (value != null && !value.equals("")) {

                    if (value.startsWith(".")) {
                        editText.setText("0.");
                    }
                    if (value.startsWith("0") && !value.startsWith("0.")) {
                        editText.setText("");

                    }


                    String str = editText.getText().toString().replaceAll(",", "");
                    if (!value.equals(""))
                        editText.setText(getDecimalFormattedString(str));
                    editText.setSelection(editText.getText().toString().length());
                }
                editText.addTextChangedListener(this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        public String getDecimalFormattedString(String value) {
            StringTokenizer lst = new StringTokenizer(value, ".");
            String str1 = value;
            String str2 = "";
            if (lst.countTokens() > 1) {
                str1 = lst.nextToken();
                str2 = lst.nextToken();
            }
            String str3 = "";
            int i = 0;
            int j = -1 + str1.length();
            if (str1.charAt(-1 + str1.length()) == '.') {
                j--;
                str3 = ".";
            }
            for (int k = j; ; k--) {
                if (k < 0) {
                    if (str2.length() > 0)
                        str3 = str3 + "." + str2;
                    return str3;
                }
                if (i == 3) {
                    str3 = "," + str3;
                    i = 0;
                }
                str3 = str1.charAt(k) + str3;
                i++;
            }
        }
    }
}
