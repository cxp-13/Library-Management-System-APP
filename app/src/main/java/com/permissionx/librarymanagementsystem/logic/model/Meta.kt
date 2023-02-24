package com.permissionx.librarymanagementsystem.logic.model

data class Meta(
    val msg: String,
    val status: Int
)

data class MetaResponse(
    val meta: Meta
)