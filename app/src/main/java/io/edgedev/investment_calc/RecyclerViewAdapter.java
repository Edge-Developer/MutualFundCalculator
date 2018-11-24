package io.edgedev.investment_calc;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.edgedev.investment_calc.databinding.SingleYearBinding;

/**
 * Created by OPEYEMI OLORUNLEKE on 6/2/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MoneyHolder> {

    List<Money> mList;
    private Callback mCallback;

    public RecyclerViewAdapter(Callback callback) {
        mCallback = callback;
        mList = new ArrayList<>();
    }

    @Override
    public MoneyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SingleYearBinding binding = DataBindingUtil.inflate(inflater, R.layout.single_year, parent, false);
        return new MoneyHolder(binding);
    }

    @Override
    public void onBindViewHolder(MoneyHolder holder, int position) {
        holder.bind(mList.get(position));

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addMoney(Money money) {
        mList.add(money);
        notifyItemInserted(mList.size() - 1);
    }

    public interface Callback {
        public void clicked(String year);
        void share(String message);
    }

    public class MoneyHolder extends RecyclerView.ViewHolder {
        private SingleYearBinding vBinding;
        private Money vMoney;

        public MoneyHolder(final SingleYearBinding binding) {
            super(binding.getRoot());
            vBinding = binding;
            vBinding.detailsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.clicked(vMoney.getYear());
                }
            });
            vBinding.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.share(vBinding.summaryOfInv.getText().toString());

                }
            });
        }

        public void bind(Money money) {
            vBinding.setMoney(money);
            vMoney = money;
        }
    }
}
