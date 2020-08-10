package com.base444.android.taiwanlottoscanner.model

class LottoNumberElement(var number: Int, var line: Int, var element: Int, var type: String) {

    override fun toString(): String {
        return  number.toString() + " Line: " + line.toString() + " Element: " + element.toString()
     }

    fun getIndex(): String{
        return "L" + line.toString() +"E" + element.toString()
    }

}