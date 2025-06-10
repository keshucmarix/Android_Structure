package com.app.ui.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.data.model.User
import com.app.data.model.request.StoreRequest
import com.app.data.model.response.StoreResponse
import com.app.data.repository.AuthRepository
import com.app.data.request.SignUpRequest
import com.app.utils.Exception
import com.app.utils.Resource
import com.app.utils.collectData
import com.app.utils.launchWithExceptionHandling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {


    private val userData: MutableLiveData<Resource<User>> = MutableLiveData()
    var userModel: LiveData<Resource<User>> = userData

    fun signUp(params: SignUpRequest) = viewModelScope.launch(Dispatchers.IO) {

        userData.collectData(repository.signUp(params), this)
    }


    val storeTrendingMutableLiveData: MutableLiveData<Resource<MutableList<StoreResponse>>> =
        MutableLiveData()

    fun getStoreTrendingList(params: StoreRequest) {
        viewModelScope.launchWithExceptionHandling({
            storeTrendingMutableLiveData.collectData(repository.getStoreTrending(params), it)
        }, {
            storeTrendingMutableLiveData.Exception(it)
        })
    }
}