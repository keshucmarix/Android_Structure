package com.app.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.app.data.model.User
import com.app.data.repository.HomeRepository
import com.app.di.module.BearerTokenQualifier
import com.app.utils.Resource
import com.app.utils.collectData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    @BearerTokenQualifier val token: String
) : ViewModel() {

    private val userData: MutableLiveData<Resource<MutableList<User>>> = MutableLiveData()
    var userModel: LiveData<Resource<MutableList<User>>> = userData

    fun getUserData() = viewModelScope.launch(Dispatchers.IO) {
        Log.e(" okhttp.OkHttpClient", "test $token")
        userData.collectData(repository.getUserData(), this)
    }


    fun fetchUsers() {

        Log.e("TAG", "fetchUsers: ")
        userModel = liveData(context = Dispatchers.IO) {

            try {
                emit(Resource.success(data = repository.getUsers()))
            } catch (exception: Exception) {
                emit(Resource.error(exception.message ?: "Error Occurred!", data = null))
            }
        }
    }
}


//    fun fetchUsers() {
//
//        Log.e("TAG", "fetchUsers: ")
//        userModel = liveData(context = Dispatchers.IO) {
//            emit(Resource.loading(null))
//            try {
//                emit(Resource.success(data = repository.getUsers()))
//            } catch (exception: Exception) {
//                emit(Resource.error(exception.message ?: "Error Occurred!", data = null))
//            }
//        }
//    }

//    fun getUserData() = viewModelScope.launch(Dispatchers.IO) {
//        val response = repository.getUserData()
//        withContext(Dispatchers.Main) {
//            userData.value = Resource.loading(null)
//            try {
//                if (response.code() == 200) {
//                    userData.value = Resource.success(data = response.body())
//                } else {
//                    userData.value = Resource.error(response.errorBody().toString(), data = null)
//                }
//            } catch (e: Exception) {
//                userData.value = Resource.error(e.message ?: "Error Occurred!", data = null)
//            }
//        }
//
//        /*   repository.getUserData().collectLatest {
//               userData.value = it
//           }*/
//    }

//
//    fun fetchUsers1() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(null))
//        try {
//            emit(Resource.success(data = repository.getUsers()))
//        } catch (exception: Exception) {
//            emit(Resource.error(exception.message ?: "Error Occurred!", data = null))
//        }
//    }