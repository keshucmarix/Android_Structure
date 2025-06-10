package com.app.data.repository

import com.app.data.api.ApiService
import com.app.data.model.User
import com.app.data.model.request.StoreRequest
import com.app.data.request.SignUpRequest
import javax.inject.Inject

open class AuthRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getUsers(): MutableList<User> {
        return apiService.getUsers()
    }

    suspend fun signUp(params: SignUpRequest) = apiService.signUp(params)

    suspend fun getStoreTrending(params: StoreRequest) = apiService.storeTrending()

}