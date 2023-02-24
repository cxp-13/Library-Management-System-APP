package com.permissionx.librarymanagementsystem.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey var id: String = "",
    val username: String,
    val password: String
)