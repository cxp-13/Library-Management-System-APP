package com.permissionx.librarymanagementsystem.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    //    const val BASE_URL = "http://routn.nat300.top/api/public/v1/"
    private const val BASE_URL = "http://192.168.0.112:8080/api/public/v1/"


    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create() = create(T::class.java)

}