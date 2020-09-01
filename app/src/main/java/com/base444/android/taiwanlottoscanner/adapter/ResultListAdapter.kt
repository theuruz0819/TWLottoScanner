package com.base444.android.taiwanlottoscanner.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.base444.android.taiwanlottoscanner.LottoTextProcessor
import com.base444.android.taiwanlottoscanner.LottoTextProcessor.MODE_638
import com.base444.android.taiwanlottoscanner.LottoTextProcessor.MODE_649
import com.base444.android.taiwanlottoscanner.R
import com.base444.android.taiwanlottoscanner.model.*
import com.base444.android.taiwanlottoscanner.viewHolder.Lotto649ViewHolder
import com.base444.android.taiwanlottoscanner.viewHolder.SuperLotto638ViewHolder
import com.google.mlkit.vision.text.Text

class ResultListAdapter(
    var targetNumber: LottoTargetNumber?,
    val numbersList: ArrayList<BaseLotto>,
    var showDialogInterface: ShowDialogInterface):
    RecyclerView.Adapter<BaseLottoResultViewHolder>() {
    interface ShowDialogInterface{
        fun showDialog(text: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseLottoResultViewHolder {
        if (viewType == LOTTO649){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.result_list_item, parent, false)
            return Lotto649ViewHolder(view)
        } else if (viewType == SUPPERLOTTO){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.result_list_item, parent, false)
            return SuperLotto638ViewHolder(view)
        }else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.result_list_item, parent, false)
            return KnowViewHolder(
                view
            )
        }
    }
    override fun getItemCount(): Int {
        return numbersList.size
    }
    override fun onBindViewHolder(holder: BaseLottoResultViewHolder, position: Int) {
        holder.bind(numbersList.get(position), targetNumber)
        holder.setDeletedListener(View.OnClickListener {
            numbersList.removeAt(position)
            notifyDataSetChanged()
        })
        holder.setOnClickListener(View.OnClickListener {
            var returnText = ""
            when(getItemViewType(position)){
                LOTTO649 ->{
                    returnText = LottoTextProcessor.lottoPriceText(numbersList.get(position) as Lotto649, targetNumber, MODE_649)
                }
                SUPPERLOTTO ->{
                    returnText = LottoTextProcessor.lottoPriceText(numbersList.get(position) as SuperLotte638, targetNumber, MODE_638)
                }
            }
            showDialogInterface.showDialog(returnText)
        })
    }
    override fun getItemViewType(position: Int): Int {
        if (numbersList.get(position) is Lotto649){
            return LOTTO649
        } else if (numbersList.get(position) is SuperLotte638){
            return SUPPERLOTTO
        }
        else {
            return 0
        }
    }
    companion object {
        val UNKNOW = 0
        val LOTTO649 = 1
        val SUPPERLOTTO = 2
    }
}

abstract class BaseLottoResultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    abstract fun bind(lotto: BaseLotto, targetNumber: LottoTargetNumber?)
    abstract fun setDeletedListener(listener: View.OnClickListener)
    abstract fun setOnClickListener(listener: View.OnClickListener)
}

class KnowViewHolder(itemView: View): BaseLottoResultViewHolder(itemView) {
    override fun bind(lotto: BaseLotto, targetNumber: LottoTargetNumber?) {
    }
    override fun setDeletedListener(listener: View.OnClickListener) {
    }
    override fun setOnClickListener(listener: View.OnClickListener) {
    }
}