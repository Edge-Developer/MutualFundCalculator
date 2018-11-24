package io.edgedev.investment_calc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class InvestmentDetail extends Fragment {
    private static final String ARG_YEAR_KEY = "the_year";
    private MonthAdapter mMonthAdapter;
    private String year;


    public static InvestmentDetail newInstance(String year) {
        Bundle args = new Bundle();
        args.putString(ARG_YEAR_KEY, year);
        InvestmentDetail fragment = new InvestmentDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        year = getArguments().getString(ARG_YEAR_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_detail, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.monthRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMonthAdapter = new MonthAdapter();
        recyclerView.setAdapter(mMonthAdapter);

        List<Month> months = Singleton.getInstance().findMoney(year).getMonth();
        mMonthAdapter.addMonths(months);
        return view;
    }
}