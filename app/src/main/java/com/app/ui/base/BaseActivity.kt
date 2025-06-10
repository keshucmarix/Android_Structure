package com.app.ui.base

import android.R.id
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import com.app.R
import com.app.utils.CustomProgressDialog
import com.app.utils.Session

import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity(), HasToolbar {



    @Inject
    lateinit var session: Session

    lateinit var customProgressDialog: CustomProgressDialog

    var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(createViewBinding())
        //calling these seems to break firebase crashlytics.
        //FirebaseApp.initializeApp(this);
        //FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)
        setCustomProgress()


        test()


    }

    private fun test() {

    }

    private fun setCustomProgress() {
        customProgressDialog = CustomProgressDialog(this)
        customProgressDialog.setCanceledOnTouchOutside(false)

    }


    abstract fun findFragmentPlaceHolder(): Int

    abstract fun createViewBinding(): View


    fun showToast(message: String) {

        val layout = this.layoutInflater.inflate(
            R.layout.custom_toast,
            this.findViewById(R.id.cl_customToastContainer)
        )

        val textView: AppCompatTextView = layout.findViewById(R.id.textView)

        textView.text = message

        val toast = Toast(this)
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 0)
        toast.show()
    }


    private fun shouldGoBack(): Boolean {
        return true
    }

    override fun onBackPressed() {
        hideKeyboard()
        // val currentFragment = getCurrentFragment<BaseFragment<*>>()
        /*  if (currentFragment == null) onBackPressedDispatcher.onBackPressed()
          else if (currentFragment.onBackActionPerform() && shouldGoBack()) onBackPressedDispatcher.onBackPressed()*/
        super.onBackPressed()
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val inputManager =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
    }

    override fun showToolbar(b: Boolean) {
        val supportActionBar = supportActionBar
        if (supportActionBar != null) {

            if (b) supportActionBar.show()
            else supportActionBar.hide()
        }
    }

    override fun setToolbarTitle(title: CharSequence) {
        if (supportActionBar != null) {
            supportActionBar!!.title = title
        }
    }

    override fun setToolbarTitle(@StringRes title: Int) {

        if (supportActionBar != null) {
            supportActionBar!!.setTitle(title)
        }
    }

    override fun showBackButton(b: Boolean) {

        val supportActionBar = supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(b)
    }

    override fun setToolbarColor(@ColorRes color: Int) {}


    override fun setToolbarElevation(isVisible: Boolean) {

        if (supportActionBar != null) {
            supportActionBar!!.elevation = if (isVisible) 8f else 0f
        }
    }

    fun showKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val inputManager =
                this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }


    fun loader(show: Boolean) {

        if (show) {
            if (!customProgressDialog.isShowing)
                customProgressDialog.show()
        } else {
            if (customProgressDialog.isShowing)
                customProgressDialog.dismiss()
        }
    }

    fun trackPartnerServiceClick(serviceName: String?) {
//        val bundle = Bundle()
//        bundle.putString("username", "keshu")
//        bundle.putString("email", "keshu@gmail.com")
//        bundle.putString("platform", "Android")
//        bundle.putString("partner_service", serviceName)
//        bundle.putString("timestamp", SimpleDateFormat("MM-dd-yyyy HH:mm").format(Date()))
//
//        mFirebaseAnalytics?.logEvent(
//            "partner_service_click",
//            bundle
//        )

        val params = Bundle()
       // params.putString("image_name", "Android Demo")
        params.putString("full_text", "Tesing data")
        mFirebaseAnalytics!!.logEvent("share_image", params)
        mFirebaseAnalytics!!.setUserProperty("full_text", "Apple");

    }
    fun trackPartnerServiceClick1(serviceName: String?) {

    }

}
