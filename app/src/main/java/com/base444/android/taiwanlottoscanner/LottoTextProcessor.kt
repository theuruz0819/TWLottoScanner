package com.base444.android.taiwanlottoscanner

import android.text.TextUtils
import android.util.Log
import com.base444.android.taiwanlottoscanner.model.BaseLotto
import com.base444.android.taiwanlottoscanner.model.Lotto649
import com.base444.android.taiwanlottoscanner.model.Lotto649OpenedNumber
import com.google.mlkit.vision.text.Text

object LottoTextProcessor {
    val TAG = "LottoTextProcessor"

    interface OnTextProcessInterface{
        fun updateTermTextView(text: String)
        fun addLottoNumber(number: BaseLotto)
    }

    fun lotto649PriceText(lotto : Lotto649, openedNumber: Lotto649OpenedNumber?) : String{
        val isSpNumberMatch = lotto.numbers.contains(openedNumber?.sp_number)
        var matchNumberCount = 0
        for(number in lotto.numbers){
            if (openedNumber != null) {
                if (openedNumber.isNumberMatch(number)){
                    matchNumberCount ++
                }
            }
        }
        when(matchNumberCount){
            2 -> {
                if (isSpNumberMatch){
                    return "柒獎 NT$400"
                }
            }
            3 ->{
                if (isSpNumberMatch){
                    return "陸獎 NT$1,000"
                } else {
                    return "普獎 NT$400"
                }
            }
            4 ->{
                if (isSpNumberMatch){
                    return "肆獎"
                } else {
                    return "伍獎 NT$2,000"
                }
            }
            5 ->{
                if (isSpNumberMatch){
                    return "貳獎"
                } else {
                    return "參獎"
                }
            }
            6 ->{
                return "頭獎"
            }
        }
        return "沒中獎"
    }

    fun processLotto649Numbers(visionText: Text, processInterface: OnTextProcessInterface){
        var date= ""
        for (block in visionText.textBlocks) {
            for (line in block.lines) {
                Log.i(TAG, line.text)
                if (isDateText(line.text)){
                    date = line.text.take(9)
                }
                val numbers = ArrayList<Int>()
                for (element in line.elements) {
                    var text = element.text
                    if (text.contains(')')){
                        text =  text.substring(text.indexOf(')'))
                    }
                    if (isLotteNumber(element.text) && !numbers.contains(element.text.toInt())){
                        numbers.add(element.text.toInt())
                    }
                }
                if (numbers.size == 6){
                    var lotto = Lotto649(numbers, date, "", false)
                    processInterface.addLottoNumber(lotto)
                }
                if(line.text.contains('#')){
                    try {
                        var termNumber = line.text.substring(line.text.indexOf('#') + 1, line.text.indexOf('#') + 10)
                        if (TextUtils.isDigitsOnly(termNumber)){
                            processInterface.updateTermTextView(termNumber)
                        }
                    } catch (e: Exception){
                        Log.e(TAG, e.message)
                    }
                }
            }
        }
    }

    private fun isLotteNumber(text: String): Boolean{
        if (text.length == 2) {
            if(text.toIntOrNull() == null){
                return false
            } else if (text.toIntOrNull()!! in 1..49) {
                return true
            }
        }
        return false
    }

    private fun isDateText(text: String): Boolean{
        val targetText = text.takeLast(9)
        if (targetText.contains('/') && !targetText.contains(':')) {
            val dateArray = targetText.split('/')
            if (dateArray.size != 3){
                return false
            } else {
                if(dateArray[0].length != 3){
                    return false
                }
                if (dateArray[1].length != dateArray[2].length || dateArray[1].length != 2){
                    return false
                }
                return true
            }
        }
        return false
    }
}