package com.base444.android.taiwanlottoscanner.model.pool

import android.util.Log
import com.base444.android.taiwanlottoscanner.LottoTextProcessor.TAG
import com.base444.android.taiwanlottoscanner.model.LottoNumberElement
import com.base444.android.taiwanlottoscanner.model.SuperLotte638

class LottoNumberGridPool {
    var pool : MutableMap<String, ArrayList<Int>> = mutableMapOf()

    fun initial(){
        pool.clear()
    }

    fun putNumber(lottoNumberElement: LottoNumberElement){
        if (pool.get(lottoNumberElement.getIndex()) == null){
            val newList: ArrayList<Int> = arrayListOf()
            newList.add(lottoNumberElement.number)
            pool.put(lottoNumberElement.getIndex(), newList)
        } else {
            pool.get(lottoNumberElement.getIndex())!!.add(lottoNumberElement.number)
        }
    }

    fun getNumberTexts(){
        for(key in pool.keys){
            val countByElement = pool.get(key)?.groupingBy { it }?.eachCount()
            val maximumElement = countByElement?.maxBy { it.value }?.key
            if (key.contains("L1")){
                Log.i(TAG,key + " : " + maximumElement)
            }
        }
    }
    fun getIndexFromKey(key:String): Int{
      if(key.contains("L") && key.contains("E")){
          var start = key.indexOf("L")
          var end = key.indexOf("E")
          return key.substring(start + 1, end).toInt()
      } else {
          return -1
      }
    };
    fun getLottoNumberList() : ArrayList<SuperLotte638>{
        var numberList = arrayListOf<SuperLotte638>()

        for(key in pool.keys){
            val countByElement = pool.get(key)?.groupingBy { it }?.eachCount()
            val maximumElement = countByElement?.maxBy { it.value }?.key
            var index = getIndexFromKey(key)

        }

        return numberList
    }
}