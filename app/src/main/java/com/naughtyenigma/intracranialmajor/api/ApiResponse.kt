package com.naughtyenigma.intracranialmajor.api

import androidx.annotation.Keep

@Keep
data class ApiResponse<out T>(
    val code: Int,
    val msg: String?,
    val data: T?
)