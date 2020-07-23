package com.base444.android.taiwanlottoscanner.model

import android.graphics.Color

class Lotto649OpenedNumber(
    val numbers: ArrayList<Int>,
    val sp_number: Int,
    val date: String
) : LottoTargetNumber() {
    override fun getTextFromResult(): CharSequence? {
        var text = ""
        for (number in numbers){
            text = text + " " + number.toString().padStart(2, '0')
        }
        text = text + "   特別號 : " + sp_number.toString().padStart(2, '0')
        text = "$text \n 日期 : $date"
        return text
    }

    override fun getTextColorForNumber(number: Int):Int {
        if (isNumberMatch(number)){
            return Color.CYAN
        } else if (isSpNumberMatch(number)){
            return Color.RED
        } else {
            return Color.BLACK
        }
    }
    fun isNumberMatch(number: Int): Boolean{
        return numbers.contains(number)
    }
    fun isSpNumberMatch(number: Int): Boolean{
        return number == sp_number
    }
    constructor() : this(ArrayList<Int>(), 0, "")
}