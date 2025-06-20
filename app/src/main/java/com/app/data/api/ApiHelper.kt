package com.app.data.api

import com.app.data.request.SignUpRequest
import javax.inject.Inject

open class ApiHelper @Inject constructor(private val apiService: ApiService) {
    suspend fun getUsers() = apiService.getUsers() //test abc

    suspend fun getUserData() = apiService.getUserData()
    suspend fun getSignUp(params: SignUpRequest) = apiService.signUp(params)
}