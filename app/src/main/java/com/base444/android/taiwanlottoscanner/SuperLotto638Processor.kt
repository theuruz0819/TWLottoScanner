package com.base444.android.taiwanlottoscanner

import android.text.TextUtils
import android.util.Log
import androidx.core.text.isDigitsOnly
import com.google.mlkit.vision.text.Text

fun processSuperLotto638Numbers(visionText: Text, processInterface: LottoTextProcessor.OnTextProcessInterface){
    for (block in visionText.textBlocks) {
        for (line in block.lines) {
            var lineText = line.text
            for (element in line.elements) {
                var text = element.text
                text = text.replace("O", "0")
                if (lineText.contains('A') && isSupperLotteNumberA(text)){
                    Log.e(LottoTextProcessor.TAG, " A : " + text + element.boundingBox.toString())
                } else if (lineText.contains('B')){
                    val replace = text.toString().replace("B", "")
                    if(isSupperLotteNumberB(replace)){
                        Log.e(LottoTextProcessor.TAG, " B : " + replace)
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
                    Log.e(LottoTextProcessor.TAG, e.message)
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
    if (text.length == 2) {
        if(text.toIntOrNull() == null){
            return false
        } else if (text.toIntOrNull()!! in 1..8) {
            return true
        }
    }
    return false
}