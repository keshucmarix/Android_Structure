package com.app.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.app.R

class BaseRecyclerView<MODEL, Binding : ViewBinding>(
    val context: Activity,
    itemBinding: (LayoutInflater, ViewGroup, Boolean) -> Binding,
    bind: (Binding, MODEL, Int) -> Unit
) : GenericAdapter<MODEL, Binding>(itemBinding, bind) {

    fun noDataFound(message: String) {
        val inflater: LayoutInflater = context.layoutInflater
        val view: View = inflater.inflate(com.app.R.layout.no_data_found, null)
        if (itemCount != -1) {
            context.setContentView(view)
            val textView = view.findViewById<TextView>(R.id.textView)
            textView.text = message
        } else {
            val viewGroup = view.parent as ViewGroup
            viewGroup.removeView(view)
        }
    }
}