package io.edgedev.investment_calc;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import io.edgedev.investment_calc.databinding.SingleMonthBinding;

/**
 * Created by OPEYEMI OLORUNLEKE on 6/2/2017.
 */

class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.MonthHolder>{

    List<Month> mMonths;

    public MonthAdapter() {
        mMonths  = new ArrayList<>();
    }

    @Override
    public MonthHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SingleMonthBinding binding = DataBindingUtil.inflate(inflater,R.layout.single_month, parent,false);
        return new MonthHolder(binding);
    }

    @Override
    public void onBindViewHolder(MonthHolder holder, int position) {

        holder.bind(mMonths.get(position));
        if (position % 2 == 0) {
            //holder.vLinearLayout.setBackground(null);
            holder.vLinearLayout.setBackgroundColor(Color.parseColor("#b2ebf2"));
        } else {
            holder.vLinearLayout.setBackgroundColor(Color.parseColor("#e0f7fa"));
        }
    }

    @Override
    public int getItemCount() {
        return mMonths.size();
    }

    public void addMonths(List<Month> months){
        mMonths = months;
        notifyDataSetChanged();
    }

    public class MonthHolder extends RecyclerView.ViewHolder {
        SingleMonthBinding vBinding;
        public LinearLayout vLinearLayout;

        public MonthHolder(SingleMonthBinding binding) {
            super(binding.getRoot());
            vBinding = binding;
            vLinearLayout = binding.lLayout;
        }

        public void bind(Month month){
            vBinding.setMonth(month);
        }
    }
}
