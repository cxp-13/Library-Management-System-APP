package com.permissionx.librarymanagementsystem.logic.model

data class TokenResponse(
    val data: Data,
    val meta: Meta
) {
    data class Data(val token: String, val id: String)
}