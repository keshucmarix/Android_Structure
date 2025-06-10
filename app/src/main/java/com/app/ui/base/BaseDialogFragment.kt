package com.app.ui.base

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.app.R
import com.app.utils.AppSession
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

abstract class BaseDialogFragment<T : ViewBinding> : DialogFragment() {


    @Inject
    lateinit var appSession: AppSession

    private var _binding: T? = null

    protected val binding: T
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = createViewBinding(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val parent = view.parent as? View
        parent?.setBackgroundColor(Color.TRANSPARENT)
        bindData()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            /*setOnShowListener {
                val bottomSheet =
                    findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                bottomSheet.setBackgroundResource(android.R.color.transparent)
            }*/
        }
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

    fun getParentFragment(targetFragment: Class<T>): T? {
        if (parentFragment == null) return null
        try {
            return targetFragment.cast(parentFragment)
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }

        return null
    }

    open fun onBackActionPerform(): Boolean {
        return true
    }

    open fun onViewClick(view: View) {

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun onError(throwable: Throwable) {
        try {

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showMessage(message: String?) {
        message?.let {
            with(Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT)) {
                view.z = 200f
                // Used to show snackbar on top
                val params = view.layoutParams as CoordinatorLayout.LayoutParams
                params.gravity = Gravity.TOP
                view.layoutParams = params
                view.setBackgroundColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.colorPrimary,
                        null
                    )
                )

                val textView =
                    view.findViewById<AppCompatTextView>(com.google.android.material.R.id.snackbar_text)
                textView.maxLines = 4
                show()
            }
        }
    }

    /**
     * This method is used for binding view with your binding
     */
    protected abstract fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean,
    ): T

    /**
     * This method is used for binding view with your binding
     */
    protected abstract fun bindData()

    fun logout() {
        // Write logout code
    }
}