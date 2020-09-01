package com.base444.android.taiwanlottoscanner

import com.base444.android.taiwanlottoscanner.model.*
import com.google.mlkit.vision.text.Text

object LottoTextProcessor {
    val TAG = "LottoTextProcessor"
    val MODE_649 = "lotto649"
    val MODE_638 = "SupperLotto638"

    interface OnTextProcessInterface{
        fun updateTermTextView(text: String)
        fun addLottoNumber(number: BaseLotto)
    }

    fun processLottoNumbers(visionText: Text, processInterface: OnTextProcessInterface, mode: String){
        if (mode.equals(MODE_649)) {
            processLotto649Numbers(visionText, processInterface)
        } else if(mode.equals(MODE_638)){
            processSuperLotto638Numbers(visionText, processInterface)
        }
    }

    fun lottoPriceText(lotto: BaseLotto, targetNumber: LottoTargetNumber?, mode: String): String {
        if (mode.equals(MODE_649) && targetNumber is Lotto649OpenedNumber && lotto is Lotto649) {
            return lotto649PriceText(lotto, targetNumber)
        } else if(mode.equals(MODE_638) && targetNumber is SuperLotto638OpenedNumber && lotto is SuperLotte638){
            return lotto638riceText(lotto, targetNumber)
        }
        return ""
    }
}