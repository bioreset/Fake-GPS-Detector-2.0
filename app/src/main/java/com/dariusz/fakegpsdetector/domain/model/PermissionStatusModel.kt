package com.dariusz.fakegpsdetector.domain.model

data class PermissionStatusModel(
    val status: Boolean = false,
    val neededPermissions: List<String>? = listOf()
)
