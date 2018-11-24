package io.edgedev.investment_calc

import java.text.DecimalFormat

import io.edgedev.investment_calc.MainActivity.formatter


/**
 * Created by OPEYEMI OLORUNLEKE on 6/2/2017.
 */

class Month {

    var monthIndex: String? = null
    var balanceAtStart: String? = null
        get() {
            val xyz = java.lang.Float.parseFloat(field!!).toLong()
            return formatter.format(xyz)
        }
    var balanceAtEnd: String? = null
        get() {
            val xyz = java.lang.Float.parseFloat(field!!).toLong()
            return formatter.format(xyz)
        }
    var investment: String? = null
        get() {
            val xyz = java.lang.Float.parseFloat(field!!).toLong()
            return formatter.format(xyz)
        }
    var interest: String? = null
        get() {
            val xyz = java.lang.Float.parseFloat(field!!).toLong()
            return formatter.format(xyz)
        }
}
