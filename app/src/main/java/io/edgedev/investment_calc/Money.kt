package io.edgedev.investment_calc

import android.os.Build
import android.text.Html
import android.view.View
import java.util.*

/**
 * Created by OPEYEMI OLORUNLEKE on 6/2/2017.
 */

class Money(val isInvSummary: Boolean) {

    var year: String? = null
    var money: String? = null
    private var summary = ""
    var month: List<Month> = ArrayList()

    init {

    }

    fun getSummary(): CharSequence {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(summary, Html.FROM_HTML_MODE_COMPACT)
        else
            Html.fromHtml(summary)
    }

    fun setSummary(summary: String) {
        this.summary = summary
    }

    fun showYearlyInv(): Int {
        return if (isInvSummary)
            View.INVISIBLE
        else
            View.VISIBLE
    }

    fun showSummaryInv(): Int {
        return if (isInvSummary)
            View.VISIBLE
        else
            View.INVISIBLE
    }

}
