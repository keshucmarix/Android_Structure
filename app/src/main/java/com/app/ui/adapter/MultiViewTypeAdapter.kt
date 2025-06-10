package com.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.data.model.MultiDataModel

open class MultiViewTypeAdapter<T>(
    private val itemLayoutRes: Map<Int, Int>,
    private val onBind: (View, MultiDataModel, Int) -> Unit
) : RecyclerView.Adapter<MultiViewTypeAdapter<T>.ViewHolder>() {

    private var data: List<MultiDataModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutResId = itemLayoutRes[viewType]
            ?: throw IllegalArgumentException("Invalid view type: $viewType")
        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        onBind(holder.itemView, data[position], position)
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        return data[position].type
    }

    fun setData(newData: List<MultiDataModel>) {
        data = newData
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}