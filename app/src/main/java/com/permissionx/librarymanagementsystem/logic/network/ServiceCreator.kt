package com.permissionx.librarymanagementsystem.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    const val BASE_URL = "http://routn.nat300.top/api/public/v1"

    val retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

    fun <T> create(serviceClass: Class<T>) = retrofit.create(serviceClass)

    inline fun <reified T> create() = create(T::class.java)

}