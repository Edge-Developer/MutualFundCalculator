package io.edgedev.investment_calc

import android.content.Context
import androidx.databinding.DataBindingUtil
import android.os.Build
import androidx.core.app.ShareCompat
import androidx.recyclerview.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList

import io.edgedev.investment_calc.databinding.SingleYearBinding

/**
 * Created by OPEYEMI OLORUNLEKE on 6/2/2017.
 */

class RecyclerViewAdapter(private val mCallback: Callback, var mList: MutableList<Money> = ArrayList()) : androidx.recyclerview.widget.RecyclerView.Adapter<RecyclerViewAdapter.MoneyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoneyHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<SingleYearBinding>(inflater, R.layout.single_year, parent, false)
        return MoneyHolder(binding)
    }

    override fun onBindViewHolder(holder: MoneyHolder, position: Int) {
        holder.bind(mList[position])

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun addMoney(money: Money) {
        mList.add(money)
        notifyItemInserted(mList.size - 1)
    }

    interface Callback {
        fun clicked(year: String?)
        fun share(message: String)
    }

    inner class MoneyHolder(private val vBinding: SingleYearBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(vBinding.root) {
        private var vMoney: Money? = null

        init {
            vBinding.detailsBtn.setOnClickListener { mCallback.clicked(vMoney!!.year) }
            vBinding.share.setOnClickListener { mCallback.share(vBinding.summaryOfInv.text.toString()) }
        }

        fun bind(money: Money) {
            vBinding.money = money
            vMoney = money
        }
    }
}
