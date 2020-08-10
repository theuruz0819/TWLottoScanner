package com.base444.android.taiwanlottoscanner

import android.text.TextUtils
import android.util.Log
import com.base444.android.taiwanlottoscanner.LottoTextProcessor.TAG
import com.base444.android.taiwanlottoscanner.model.BaseLotto
import com.base444.android.taiwanlottoscanner.model.LottoNumberElement
import com.base444.android.taiwanlottoscanner.model.SuperLotte638
import com.google.mlkit.vision.text.Text

fun processSuperLotto638Numbers(visionText: Text, processInterface: LottoTextProcessor.OnTextProcessInterface){
    var lottoNumberElements : ArrayList<LottoNumberElement> = ArrayList()
    var number: SuperLotte638

    for (block in visionText.textBlocks) {
        for (line in block.lines) {
            var lineText = line.text
            for (element in line.elements) {
                var text = element.text
                text = text.replace("O", "0")
                if (lineText.contains('A') && isSupperLotteNumberA(text)){

                } else if (lineText.contains('B')){
                    val replace = text.toString().replace("B", "")
                    if(isSupperLotteNumberB(replace)){

                    }
                }
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

// dev
fun devProcessSuperLotto638Numbers(visionText: Text){
    var lottoNumberList : ArrayList<BaseLotto> = ArrayList()
    var setCount = 0
    var numbersA = ArrayList<Int>()
    var numbersB = ArrayList<Int>()

    for (block in visionText.textBlocks) {
        Log.i(TAG, block.text)
        for (line in block.lines) {
            var lineText = line.text
            if (lineText.contains('A')){
                numbersA.clear()
                numbersB.clear()
            }

            for (element in line.elements) {
                var text = element.text
                text = text.replace("O", "0")
                if (lineText.contains('A') && isSupperLotteNumberA(text)){
                    numbersA.add(text.toInt())
                } else if (lineText.contains('B')){
                    val replace = text.toString().replace("B", "")
                    if(isSupperLotteNumberB(replace)){
                        numbersB.add(replace.toInt())
                    }
                }
            }
            Log.i(TAG, numbersA.toString())
            Log.i(TAG, numbersB.toString())
            if (numbersB.size > 0 && numbersA.size == 6){
                Log.i(TAG, "ADD")
            }


            if(line.text.contains('#')){
                try {
                    var termNumber = line.text.substring(line.text.indexOf('#') + 1, line.text.indexOf('#') + 10)
                    if (TextUtils.isDigitsOnly(termNumber)){

                    }
                } catch (e: Exception){
                    //Log.e(LottoTextProcessor.TAG, e.message)
                }
            }
        }
    }
}

private fun isSupperLotteNumberA(text: String): Boolean{
    if (text.length == 2) {
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