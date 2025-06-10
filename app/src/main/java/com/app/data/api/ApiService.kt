package com.app.data.api

import com.app.data.model.User
import com.app.data.model.response.StoreResponse
import com.app.data.request.SignUpRequest
import com.app.utils.Resource
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {

    @GET("users")
    suspend fun getUsers(): MutableList<User>

    @GET("users")
    suspend fun getUserData(): Response<MutableList<User>>

    @POST("user/signup")
    suspend fun signUp(@Body params: SignUpRequest): Response<User>

    @GET("student/login")
    suspend fun storeTrending(): Resource<MutableList<StoreResponse>>

}