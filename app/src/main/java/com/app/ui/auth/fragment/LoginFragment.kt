package com.app.ui.auth.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.R
import com.app.data.model.request.StoreRequest
import com.app.data.model.response.StoreResponse
import com.app.databinding.AuthFragmentLoginBinding
import com.app.ui.auth.viewmodel.AuthViewModel
import com.app.ui.base.BaseActivity
import com.app.ui.base.BaseFragment
import com.app.utils.AppSession
import com.app.utils.ApplicationException
import com.app.utils.observer

import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : BaseFragment<AuthFragmentLoginBinding>(), View.OnClickListener {

    private lateinit var mainViewModel: AuthViewModel
    var pageCount = 1

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean,
    ): AuthFragmentLoginBinding {
        return AuthFragmentLoginBinding.inflate(inflater, container, attachToRoot)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(
            this,
            defaultViewModelProviderFactory
        )[AuthViewModel::class.java]
        mainViewModel.storeTrendingMutableLiveData.observer(this, ::onStoreData, ::onFailure)

    }


    override fun onViewCreated() {
        // appSession.bearerToken = AppSession.staticToken. test
        setClickListener()
        // apiCallSignUp()
        //  apiCallGetStoreTrending()



    }


    private fun apiCallGetStoreTrending() {
        //Test
        if (pageCount == 1) {
            showLoader()
        }
        mainViewModel.getStoreTrendingList(StoreRequest().apply {

        })
    }


    private fun setClickListener() = with(binding) {
        buttonLogin.setOnClickListener(this@LoginFragment)
        textViewSignUp.setOnClickListener(this@LoginFragment)
    }

    override fun onBackActionPerform(): Boolean {
        return false
    }

    @Inject
    lateinit var appSession: AppSession

    override fun onClick(v: View?) {
        when (v) {
            binding.buttonLogin -> {
                (activity as BaseActivity).trackPartnerServiceClick("NeighborhoodIntel")
                if (validation()) {

                }
            }

            binding.textViewSignUp -> {
                (activity as BaseActivity).trackPartnerServiceClick("DirectOffer")
               // val navController = findNavController()


                //navController.navigate(R.id.signUp, Bundle().apply {})
            }
        }
    }

    private fun validation(): Boolean {
        try {
            validator.submit(binding.textViewEmail)
                .checkEmpty().errorMessage(getString(R.string.validation_empty_email))
                .checkValidEmail().errorMessage(R.string.validation_valid_email)
                .check()
            validator.submit(binding.textViewPassword)
                .checkEmpty().errorMessage(getString(R.string.validation_empty_password))
                .check()
            return true
        } catch (e: ApplicationException) {
            showMessage(e.message)
            return false
        }
    }


    private fun onStoreData(data: MutableList<StoreResponse>) {
        hideLoader()
        Log.e("TAG", "onStoreData: " + data)

    }

    /*override fun onFailure(errorMessage: String) {
        Log.e("TAG", "onFailure: "+errorMessage )
    }*/

}