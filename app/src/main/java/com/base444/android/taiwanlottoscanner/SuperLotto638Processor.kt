package com.base444.android.taiwanlottoscanner

import android.text.TextUtils
import android.util.Log
import com.base444.android.taiwanlottoscanner.LottoTextProcessor.TAG
import com.base444.android.taiwanlottoscanner.model.BaseLotto
import com.base444.android.taiwanlottoscanner.model.LottoNumberElement
import com.base444.android.taiwanlottoscanner.model.SuperLotte638
import com.google.mlkit.vision.text.Text

fun processSuperLotto638Numbers(visionText: Text, processInterface: LottoTextProcessor.OnTextProcessInterface){

    val numbersB = ArrayList<Int>()
    var date = ""

    for (block in visionText.textBlocks) {
        for (line in block.lines) {
            val numbersA = ArrayList<Int>()
            var lineText = line.text

            if (isDateText(line.text)){
                date = line.text.take(9)
            }

            if (lineText.contains('A')){
                numbersA.clear()
            }
            for (element in line.elements) {
                var text = element.text
                text = text.replace("O", "0")
                if (isSupperLotteNumberA(text)){
                    numbersA.add(text.toInt())
                } else if (text.trim().length % 2 == 0){
                    for (index in 0 until text.length - 2 step 2){
                        val subString = text.substring(IntRange(index, index + 2))
                        if (isSupperLotteNumberA(subString) && !numbersA.contains(subString.toInt())){
                            numbersA.add(subString.toInt())
                        }
                    }
                }
            }
            if (numbersA.size == 6){
                Log.i(TAG, numbersA.toString())
                Log.i(TAG, "ADD")
                var lotto = SuperLotte638(numbersA, numbersB, date, "", false)
                processInterface.addLottoNumber(lotto)
            }
            if(line.text.contains('#')){
                try {
                    var termNumber = line.text.substring(line.text.indexOf('#') + 1, line.text.indexOf('#') + 10)
                    if (TextUtils.isDigitsOnly(termNumber)){
                        processInterface.updateTermTextView(termNumber)
                    }
                } catch (e: Exception){
                    //Log.e(LottoTextProcessor.TAG, e.message)
                }
            }
        }
    }
}

fun isSupperLotteNumberA(text: String): Boolean{
    if (text.length <= 2) {
        if(text.toIntOrNull() == null){
            return false
        } else if (text.toIntOrNull()!! in 1..38) {
            return true
        }
    }
    return false
}

private fun isSupperLotteNumberB(text: String): Boolean{
    if (text.length <= 2) {
        if(text.toIntOrNull() == null){
            return false
        } else if (text.toIntOrNull()!! in 1..8) {
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
