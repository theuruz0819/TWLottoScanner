package com.base444.android.taiwanlottoscanner.model

import kotlinx.android.parcel.Parcelize

@Parcelize
class SuperLotte638(
    var aBlocknumbers: List<Int>, var bBlocknumbers: List<Int>, override var date: String, override var term: String, override var isMAtch: Boolean
)  : BaseLotto(date, term, isMAtch){


    fun getNumberString(): String{
        var string = ""
        for (number in aBlocknumbers){
            string += number.toString().padStart(2, '0')
        }
        for (number in bBlocknumbers){
            string += number.toString().padStart(2, '0')
        }
        return  string
    }

    override fun equals(other: Any?): Boolean {
        if (other is SuperLotte638){
            if(!other.aBlocknumbers.size.equals(this.aBlocknumbers.size)){
                return false
            }
            return other.getNumberString().equals(this.getNumberString())
        } else {
            return false
        }
    }

    override fun hashCode(): Int {
        var result = aBlocknumbers.hashCode()
        result = 31 * result + bBlocknumbers.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + term.hashCode()
        result = 31 * result + isMAtch.hashCode()
        return result
    }

}