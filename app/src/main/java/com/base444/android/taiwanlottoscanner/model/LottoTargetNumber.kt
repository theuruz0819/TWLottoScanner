package com.base444.android.taiwanlottoscanner.model

abstract class LottoTargetNumber {
    abstract fun getTextFromResult(): CharSequence?
    abstract fun getTextColorForNumber(number: Int): Int
}