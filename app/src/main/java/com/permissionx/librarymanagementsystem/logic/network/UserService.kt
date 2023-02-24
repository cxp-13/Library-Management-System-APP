package com.permissionx.librarymanagementsystem.logic.network

import com.permissionx.librarymanagementsystem.logic.model.Meta
import com.permissionx.librarymanagementsystem.logic.model.MetaResponse
import com.permissionx.librarymanagementsystem.logic.model.TokenResponse
import com.permissionx.librarymanagementsystem.logic.model.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @POST("users/login")
    fun login(@Body hashMap: Map<String, String>): Call<TokenResponse>

    @POST("users/registered")
    fun registered(@Body hashMap: Map<String, String>): Call<MetaResponse>


}