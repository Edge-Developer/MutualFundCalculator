package io.edgedev.investment_calc

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class InvestmentDetail : Fragment() {
    private lateinit var mMonthAdapter: MonthAdapter
    private var year: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        year = arguments?.getString(ARG_YEAR_KEY)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.activity_detail, container, false)

        val recyclerView = view.findViewById<View>(R.id.monthRecycler) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        mMonthAdapter = MonthAdapter()
        recyclerView.adapter = mMonthAdapter

        val months : List<Month> = Singleton.getInstance().findMoney(year)!!.month
        mMonthAdapter.addMonths(months)
        return view
    }

    companion object {
        private val ARG_YEAR_KEY = "the_year"
        fun newInstance(year: String): InvestmentDetail {
            val args = Bundle()
            args.putString(ARG_YEAR_KEY, year)
            val fragment = InvestmentDetail()
            fragment.arguments = args
            return fragment
        }
    }
}