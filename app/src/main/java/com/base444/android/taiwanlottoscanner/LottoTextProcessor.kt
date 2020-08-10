package com.base444.android.taiwanlottoscanner

import com.base444.android.taiwanlottoscanner.model.BaseLotto
import com.base444.android.taiwanlottoscanner.model.Lotto649
import com.base444.android.taiwanlottoscanner.model.Lotto649OpenedNumber
import com.base444.android.taiwanlottoscanner.model.pool.LottoNumberGridPool
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

    fun lottoPriceText(lotto649: Lotto649, targetNumber: Lotto649OpenedNumber?, mode: String): String {
        if (mode.equals(MODE_649)) {
            return lotto649PriceText(lotto649, targetNumber)
        }
        return ""
    }

}