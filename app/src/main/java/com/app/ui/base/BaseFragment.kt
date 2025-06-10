package com.app.ui.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.app.BuildConfig
import com.app.utils.Dialog
import com.app.utils.Validator
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import retrofit2.HttpException
import javax.inject.Inject

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    protected lateinit var toolbar: HasToolbar


    private var _binding: T? = null

    @Inject
    lateinit var validator: Validator

    protected val binding: T
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = createViewBinding(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated()
        versionUpdate()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (activity is HasToolbar)
            toolbar = activity as HasToolbar
    }


    fun hideKeyBoard() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).hideKeyboard()
        }
    }

    fun showKeyBoard() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showKeyboard()
        }
    }


    fun <T : BaseFragment<*>> getParentFragment(targetFragment: Class<T>): T? {
        if (parentFragment == null) return null
        try {
            return targetFragment.cast(parentFragment)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
        return null
    }


    open fun onShow() {

    }

    fun showMessage(message: String) {
        (activity as BaseActivity).showToast(message)

    }

    fun showMessage(@StringRes stringId: Int) {
        showSnackBar(getString(stringId))
    }


    private fun showSnackBar(message: String) {
        showMessage(message)

    }

    private fun showSnackBar(message: String, viewSet: View) {
        hideKeyBoard()

    }


    open fun onBackActionPerform(): Boolean {
        return true
    }

    open fun onViewClick(view: View) {

    }

    open fun onFailure(errorMessage: String) {
        hideLoader()
        showMessage(errorMessage)
    }

    open fun onException(errorMessage: Exception) {
        hideLoader()
        showMessage(errorMessage.message.toString())
        if (errorMessage is HttpException) {
            if (errorMessage.code() == 401) {
                //logout
                Log.e("Exception", "onException: ->Logout")
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun hideLoader() {
        (activity as BaseActivity).loader(false)
    }

    fun showLoader() {
        (activity as BaseActivity).loader(true)
    }

    fun logout() {
        // Write logout code
    }

    protected abstract fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ): T

    protected abstract fun onViewCreated()

    private fun versionUpdate() {
        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(5) // Example: fetch every hour
            .build()
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)

        firebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener((activity as BaseActivity)) { task ->
                if (task.isSuccessful) {
                    // After fetching, activate the fetched data
                    firebaseRemoteConfig.activate().addOnCompleteListener {
                        // Now you can access the Remote Config values
                        val versionCode =
                            firebaseRemoteConfig.getString("force_update_current_version_android")
                        // Use the fetched values in your app
                        Log.e("TAG", "versionUpdate: $versionCode")
                        if (BuildConfig.VERSION_CODE < versionCode.toInt()) {
                            Dialog.alertDialogForUpdate((activity as BaseActivity))
                        }
                    }
                } else {
                    Log.e("TAG", "versionUpdate: ERROR")
                    // Handle error
                }
            }

    }
}
