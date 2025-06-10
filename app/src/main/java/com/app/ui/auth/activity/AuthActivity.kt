package com.app.ui.auth.activity

import android.os.Bundle
import android.view.View
import com.app.R
import com.app.databinding.ActivityAuthBinding
import com.app.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity() {


    lateinit var authAcitivtyBinding: ActivityAuthBinding


    override fun findFragmentPlaceHolder(): Int {
        return R.id.placeHolder
    }


    override fun createViewBinding(): View {
        authAcitivtyBinding = ActivityAuthBinding.inflate(layoutInflater)
        return authAcitivtyBinding.root
    }


}