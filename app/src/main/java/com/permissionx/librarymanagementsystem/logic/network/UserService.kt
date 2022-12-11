package com.permissionx.librarymanagementsystem.logic.network

import com.permissionx.librarymanagementsystem.logic.model.TokenResponse
import com.permissionx.librarymanagementsystem.logic.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @GET("users/login")
    fun login(@Query("name") name: String, @Query("password") password: String): Call<UserResponse>

    @POST("users/registered")
    fun registered(@Body hashMap: Map<String, String>): Call<TokenResponse>


}