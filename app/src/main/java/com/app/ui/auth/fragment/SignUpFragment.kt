package com.app.ui.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.R
import com.app.data.request.SignUpRequest
import com.app.databinding.AuthFragmentSignUpBinding
import com.app.ui.auth.viewmodel.AuthViewModel
import com.app.ui.base.BaseFragment
import com.app.utils.AppSession
import com.app.utils.ApplicationException
import com.app.utils.getTextValue
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : BaseFragment<AuthFragmentSignUpBinding>(), View.OnClickListener {

    private lateinit var mainViewModel: AuthViewModel

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean,
    ): AuthFragmentSignUpBinding {
        return AuthFragmentSignUpBinding.inflate(inflater, container, attachToRoot)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(
            this,
            defaultViewModelProviderFactory
        )[AuthViewModel::class.java]
    }

    override fun onViewCreated() {
        //  appSession.bearerToken = AppSession.staticToken
        setClickListener()
        /* Log.e("TAG", "onViewCreated: " + arguments?.getString("exampleArg"))
         Log.e("TAG", "onViewCreated: " + arguments?.getParcelable("modelClass"))*/
    }

    private fun apiCallSignUp() {
        mainViewModel.signUp(SignUpRequest().apply {
            email = binding.textViewFName.getTextValue()
            password = binding.textViewPassword.getTextValue()
            confirmPassword = binding.textViewConfirmPassword.getTextValue()
            firstName = binding.textViewFName.getTextValue()
            lastName = binding.textViewLName.getTextValue()
            mobileNumber = binding.textViewMobile.getTextValue()
            platform = "android"
        })
    }

    private fun setClickListener() = with(binding) {
        buttonLogin.setOnClickListener(this@SignUpFragment)
    }

    override fun onBackActionPerform(): Boolean {
        return false
    }

    @Inject
    lateinit var appSession: AppSession

    override fun onClick(v: View?) {
        when (v) {
            binding.buttonLogin -> {
                if (validation()) {
                    apiCallSignUp()
                }
            }
        }
    }

    private fun validation(): Boolean {
        try {
            validator.submit(binding.textViewFName)
                .checkEmpty().errorMessage(getString(R.string.validation_empty_f_name))
                .check()
            validator.submit(binding.textViewLName)
                .checkEmpty().errorMessage(getString(R.string.validation_empty_l_name))
                .check()
            validator.submit(binding.textViewEmail)
                .checkEmpty().errorMessage(getString(R.string.validation_empty_email))
                .checkValidEmail().errorMessage(R.string.validation_valid_email)
                .check()

            validator.submit(binding.textViewMobile)
                .checkEmpty().errorMessage(getString(R.string.validation_empty_mobile))
                .checkMinDigits(8).errorMessage(R.string.validation_valid_mobile)
                .checkMaxDigits(10).errorMessage(R.string.validation_valid_mobile)
                .check()

            validator.submit(binding.textViewPassword)
                .checkEmpty().errorMessage(getString(R.string.validation_empty_password))
                .check()

            validator.submit(binding.textViewConfirmPassword)
                .checkEmpty()
                .errorMessage(getString(R.string.validation_empty_confirm_password))
                .matchString(binding.textViewConfirmPassword.text.toString())
                .errorMessage(R.string.validation_empty_password)
                .check()
            return true
        } catch (e: ApplicationException) {
            showMessage(e.message)
            return false
        }
    }

}