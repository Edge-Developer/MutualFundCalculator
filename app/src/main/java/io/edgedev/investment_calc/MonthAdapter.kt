package io.edgedev.investment_calc

import androidx.databinding.DataBindingUtil
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout

import java.util.ArrayList

import io.edgedev.investment_calc.databinding.SingleMonthBinding

/**
 * Created by OPEYEMI OLORUNLEKE on 6/2/2017.
 */

internal class MonthAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<MonthAdapter.MonthHolder>() {

    var mMonths: List<Month>

    init {
        mMonths = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<SingleMonthBinding>(inflater, R.layout.single_month, parent, false)
        return MonthHolder(binding)
    }

    override fun onBindViewHolder(holder: MonthHolder, position: Int) {

        holder.bind(mMonths[position])
        if (position % 2 == 0) {
            //holder.vLinearLayout.setBackground(null);
            holder.vLinearLayout.setBackgroundColor(Color.parseColor("#b2ebf2"))
        } else {
            holder.vLinearLayout.setBackgroundColor(Color.parseColor("#e0f7fa"))
        }
    }

    override fun getItemCount(): Int {
        return mMonths.size
    }

    fun addMonths(months: List<Month>) {
        mMonths = months
        notifyDataSetChanged()
    }

    inner class MonthHolder(internal var vBinding: SingleMonthBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(vBinding.root) {
        var vLinearLayout: LinearLayout

        init {
            vLinearLayout = vBinding.lLayout
        }

        fun bind(month: Month) {
            vBinding.month = month
        }
    }
}
