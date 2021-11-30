package com.olutoba.sleepqualitytracker.sleeptracker

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.olutoba.sleepqualitytracker.R
import com.olutoba.sleepqualitytracker.database.SleepNight

class SleepNightAdapter : RecyclerView.Adapter<SleepNightAdapter.TextItemViewHolder>() {

    var data = listOf<SleepNight>()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.sleepQuality.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.text_item_view, parent, false)
                    as TextView
        return TextItemViewHolder(view)

    }

    /**
     * ViewHolder that holds a single [TextView].
     *
     * A ViewHolder holds a view for the [RecyclerView] as well as providing additional information
     * to the RecyclerView such as where on the screen it was last drawn during scrolling.
     */
    class TextItemViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

}
