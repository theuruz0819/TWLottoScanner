package com.base444.android.taiwanlottoscanner.model

import android.graphics.Color

class SuperLotto638OpenedNumber(
    val numbers: ArrayList<Int>,
    val date: String
) : LottoTargetNumber() {

    override fun getTextFromResult(): CharSequence? {
        var text = ""
        for (number in numbers){
            text = text + " " + number.toString().padStart(2, '0')
        }
        text = "$text \n 日期 : $date"
        return text
    }

    override fun getTextColorForNumber(number: Int):Int {
        if (isNumberMatch(number)){
            return Color.CYAN
        } else {
            return Color.BLACK
        }
    }
    fun isNumberMatch(number: Int): Boolean{
        return numbers.contains(number)
    }
    constructor() : this(ArrayList<Int>(),"")
}