package com.base444.android.taiwanlottoscanner.viewHolder

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.base444.android.taiwanlottoscanner.adapter.BaseLottoResultViewHolder
import com.base444.android.taiwanlottoscanner.R
import com.base444.android.taiwanlottoscanner.model.BaseLotto
import com.base444.android.taiwanlottoscanner.model.Lotto649
import com.base444.android.taiwanlottoscanner.model.Lotto649OpenedNumber
import com.base444.android.taiwanlottoscanner.model.LottoTargetNumber

class Lotto649ViewHolder(itemView: View) : BaseLottoResultViewHolder(itemView) {
    private val firstArea: LinearLayout = itemView.findViewById<LinearLayout>(R.id.number_list_first_area)
    override fun bind(lotto: BaseLotto, targetNumber: LottoTargetNumber?) {
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
                value.textSize = 20f

                if (targetNumber != null && targetNumber is Lotto649OpenedNumber) {
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

    override fun setDeletedListener(listener: View.OnClickListener) {
        itemView.findViewById<ImageView>(R.id.number_list_remove_ic).setOnClickListener(listener)
    }

    override fun setOnClickListener(listener: View.OnClickListener) {
        firstArea.setOnClickListener(listener)
    }
}