package com.permissionx.librarymanagementsystem.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class UserResponse(
    val meta: Meta,
    val user: User
) {
    data class Meta(
        val msg: String,
        val status: Int
    )

    @Entity
    data class User(
        @PrimaryKey val id: String = "",
        val name: String,
        val password: String
    )
}