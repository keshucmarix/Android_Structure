package com.app.ui.auth.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.databinding.FragmentSplashBinding
import com.app.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {



    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean,
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, attachToRoot)
    }

    override fun onViewCreated() {

    }

}