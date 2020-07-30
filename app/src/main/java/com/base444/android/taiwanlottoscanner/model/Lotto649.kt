package com.base444.android.taiwanlottoscanner.model

import kotlinx.android.parcel.Parcelize

@Parcelize
class Lotto649(
    var numbers: List<Int>, override var date: String, override var term: String, override var isMAtch: Boolean
) : BaseLotto(date, term, isMAtch) {

    fun getNumberString(): String{
        var string = ""
        for (number in numbers){
            string += number.toString().padStart(2, '0')
        }
        return  string
    }

    override fun equals(other: Any?): Boolean {
        if (other is Lotto649){
            if(!other.numbers.size.equals(this.numbers.size)){
               return false
            }
            return other.getNumberString().equals(this.getNumberString())
        } else {
            return false
        }
    }

    override fun hashCode(): Int {
        var result = numbers.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + term.hashCode()
        result = 31 * result + isMAtch.hashCode()
        return result
    }
}