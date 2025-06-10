package com.app.di.module

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.app.utils.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ConnectivityInterceptorImpl @Inject constructor(private val context: Context) :
    ConnectivityInterceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable())
            throw NoConnectivityException("Internet not available")
        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}
