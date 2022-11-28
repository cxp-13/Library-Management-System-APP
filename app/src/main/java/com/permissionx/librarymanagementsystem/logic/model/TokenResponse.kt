package com.permissionx.librarymanagementsystem.logic.model

data class TokenResponse(
    val meta: Meta,
    val token: String
){
    data class Meta(
        val msg: String,
        val status: Int
    )
}