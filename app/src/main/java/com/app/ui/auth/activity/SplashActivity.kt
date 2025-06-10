package com.app.ui.auth.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.app.R
import com.app.databinding.ActivitySplashBinding
import com.app.ui.base.BaseActivity
import com.app.utils.Common

class SplashActivity : BaseActivity() {
    //Data store on after user login
    lateinit var splashActivityBinding: ActivitySplashBinding
    override fun findFragmentPlaceHolder(): Int {
        return 0
    }


    override fun createViewBinding(): View {
        splashActivityBinding = ActivitySplashBinding.inflate(layoutInflater)
        return splashActivityBinding.root
    }

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            session.appLang = "en"
            session.bearerToken = Common.token

            navigateToMainActivity()

        }, 2000)
    }

    private fun navigateToMainActivity() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.navigate(R.id.auth)
        finish()
    }
}