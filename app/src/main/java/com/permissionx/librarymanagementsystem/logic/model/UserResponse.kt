package com.permissionx.librarymanagementsystem.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class UserResponse(
    val meta: Meta,
    val user: User
)