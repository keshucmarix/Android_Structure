package com.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class GenericAdapter<MODEL, Binding : ViewBinding>(
    private val itemBinding: (LayoutInflater, ViewGroup, Boolean) -> Binding,
    private val bind: (Binding, MODEL, Int) -> Unit
) : RecyclerView.Adapter<GenericAdapter<MODEL, Binding>.ViewHolder>() {

    private var items: List<MODEL> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = itemBinding(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bind(holder.binding, items[position], position)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(newItems: List<MODEL>) {
        items = newItems
        notifyItemRangeChanged(0, items.size)
    }

    inner class ViewHolder(val binding: Binding) : RecyclerView.ViewHolder(binding.root)
}
