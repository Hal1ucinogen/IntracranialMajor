package com.naughtyenigma.intracranialmajor.api

import androidx.annotation.Keep

@Keep
data class PageResponse<T>(
    val total: Int = 0,
    val pages: Int = 0,
    val list: List<T> = emptyList()
)
