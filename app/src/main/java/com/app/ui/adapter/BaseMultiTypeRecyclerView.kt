package com.app.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.app.R
import com.app.data.model.MultiDataModel

class BaseMultiTypeRecyclerView<MODEL>(
    val context: Activity,
    itemLayoutRes: Map<Int, Int>,
    onBind: (View, MultiDataModel, Int) -> Unit,
) : MultiViewTypeAdapter<MODEL>(itemLayoutRes, onBind) {

    fun noDataFound(message: String) {
        val inflater: LayoutInflater = context.layoutInflater
        val view: View = inflater.inflate(R.layout.no_data_found, null)
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