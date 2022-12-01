package com.permissionx.librarymanagementsystem.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object LibraryManagementSystemNetwork {


    val userService = ServiceCreator.create<UserService>()

    suspend fun login(name:String, pwd:String) = userService.login(name, pwd).await()

    suspend fun registered(name:String, pwd:String) = userService.registered(name, pwd).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body == null) it.resumeWithException(RuntimeException("body is null"))
                    else
                        it.resume(body)
                }
                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }
            })
        }
    }
}