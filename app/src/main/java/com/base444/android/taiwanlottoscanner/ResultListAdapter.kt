package com.base444.android.taiwanlottoscanner

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.base444.android.taiwanlottoscanner.model.BaseLotto
import com.base444.android.taiwanlottoscanner.model.Lotto649
import com.base444.android.taiwanlottoscanner.model.Lotto649OpenedNumber


class ResultListAdapter(
    var targetNumber: Lotto649OpenedNumber?,
    val numbersList: ArrayList<BaseLotto>):
    RecyclerView.Adapter<ResultListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultListItemViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.result_list_item, parent, false)
        return ResultListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return numbersList.size
    }

    override fun onBindViewHolder(holder: ResultListItemViewHolder, position: Int) {
        holder?.bind(numbersList.get(position), targetNumber)
    }

}
class ResultListItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val firstArea: LinearLayout = itemView?.findViewById<LinearLayout>(R.id.number_list_first_area)

    fun bind(lotto: BaseLotto, targetNumber: Lotto649OpenedNumber?) {
        if(lotto is Lotto649){
            firstArea.removeAllViews()
            for (number in lotto.numbers){
                val value = TextView(itemView.context)
                value.text = number.toString().padStart(2, '0')
                value.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                value.setPadding(30,5,30,5)
                value.setTextColor(Color.BLACK)
                value.textSize = 25f

                if (targetNumber != null) {
                    when {
                        targetNumber.isNumberMatch(number) -> {
                            value.setTextColor(Color.CYAN)
                        }
                        targetNumber.isSpNumberMatch(number) -> {
                            value.setTextColor(Color.RED)
                        }
                        else -> {
                            value.setTextColor(Color.BLACK)
                        }
                    }
                } else {
                    value.setTextColor(Color.BLACK)
                }

                firstArea.addView(value)
            }
        }
    }
}