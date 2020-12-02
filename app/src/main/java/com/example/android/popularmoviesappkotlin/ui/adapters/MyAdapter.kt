package com.example.android.popularmoviesappkotlin.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.popularmoviesappkotlin.R
import kotlinx.android.synthetic.main.list_item.view.*

class MyAdapter(
    private val dataArray: Array<String>,
    private val listener: OnRecyclerViewItemClick
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(dataArray[position], listener)
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: String, listener: OnRecyclerViewItemClick) {
            itemView.tvTitle.text = item
            itemView.setOnClickListener {
                val position = adapterPosition
                listener.onClick(position)
            }
        }
    }

    interface OnRecyclerViewItemClick {
        fun onClick(position: Int)
    }
}