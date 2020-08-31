package com.base444.android.taiwanlottoscanner.viewHolder

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.base444.android.taiwanlottoscanner.R
import com.base444.android.taiwanlottoscanner.adapter.BaseLottoResultViewHolder
import com.base444.android.taiwanlottoscanner.model.*

class SuperLotto638ViewHolder(itemView: View) : BaseLottoResultViewHolder(itemView) {

    private val firstArea: LinearLayout = itemView.findViewById<LinearLayout>(R.id.number_list_first_area)

    override fun bind(lotto: BaseLotto, targetNumber: LottoTargetNumber?) {

        if(lotto is SuperLotte638){
            firstArea.removeAllViews()
            for (number in lotto.aBlocknumbers){
                val value = TextView(itemView.context)
                value.text = number.toString().padStart(2, '0')
                value.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                value.setPadding(30,5,30,5)
                value.setTextColor(Color.BLACK)
                value.textSize = 20f

                if (targetNumber != null) {
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