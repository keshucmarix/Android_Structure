package com.app.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import com.app.BuildConfig
import com.app.R

object Dialog {
    fun alertDialogForUpdate(context: Activity) {
        context.apply {
            val alertDialog = AlertDialog.Builder(context, R.style.CustomAlertDialogWindowBG18)
            val view = layoutInflater.inflate(R.layout.version_update_alert_dialog, null)
            val textViewUpdate: AppCompatTextView = view.findViewById(R.id.textViewUpdate)
            alertDialog.setView(view)

            val alert = alertDialog.create()
            textViewUpdate.setOnClickListener {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")

                )
                startActivity(intent)
            }

            alert.setCanceledOnTouchOutside(false)
            alert.show()
            alert.window?.setLayout(760, WindowManager.LayoutParams.WRAP_CONTENT)
        }

    }
}