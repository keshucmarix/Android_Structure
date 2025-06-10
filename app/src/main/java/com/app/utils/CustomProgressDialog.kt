package com.app.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.app.R

class CustomProgressDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.decorView.setBackgroundResource(android.R.color.transparent)
        setContentView(R.layout.custom_progressbar)
        setCancelable(false)
    }
}