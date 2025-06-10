package com.app.data.repository

import com.app.data.api.ApiHelper
import com.app.data.model.User
import javax.inject.Inject

open class HomeRepository @Inject constructor(private val apiService: ApiHelper) {
    suspend fun getUsers(): MutableList<User> {
        return apiService.getUsers()
    }

    suspend fun getUserData() = apiService.getUserData()
}