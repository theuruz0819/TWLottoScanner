package com.base444.android.taiwanlottoscanner

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.base444.android.taiwanlottoscanner.model.Lotto649


class ResultListAdapter(val context: Context,val numbersList: ArrayList<Lotto649>):
    RecyclerView.Adapter<ResultListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultListItemViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.result_list_item, parent, false)
        return ResultListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return numbersList.size
    }

    override fun onBindViewHolder(holder: ResultListItemViewHolder, position: Int) {
        holder?.bind(numbersList.get(position))
    }

}
class ResultListItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val firstArea = itemView?.findViewById<LinearLayout>(R.id.number_list_first_area)

    fun bind(lotto: Lotto649) {
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

            firstArea.addView(value)
        }
    }
}