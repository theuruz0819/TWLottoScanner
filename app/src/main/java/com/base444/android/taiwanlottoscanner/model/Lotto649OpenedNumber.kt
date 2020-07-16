package com.base444.android.taiwanlottoscanner.model

class Lotto649OpenedNumber(
    val numbers: ArrayList<Int>,
    val sp_number: Int,
    val date: String
) {
    fun getTextFromResult(): CharSequence? {

        var text = ""

        for (number in numbers){
            text = text + " " + number.toString().padStart(2, '0')
        }
        text = text + "   特別號 : " + sp_number.toString().padStart(2, '0')
        text = text + " \n 日期 : " + date

        return text
    }

    fun isNumberMatch(number: Int): Boolean{
        if (numbers.contains(number)){
            return true
        } else return sp_number == number
    }

    constructor() : this(ArrayList<Int>(), 0, "")
}