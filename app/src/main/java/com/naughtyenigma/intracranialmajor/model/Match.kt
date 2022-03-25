package com.naughtyenigma.intracranialmajor.model

import com.google.gson.annotations.SerializedName

data class Match(
    val heroName: String,
    val heroId: Int,
    val heroIcon: String,
    @SerializedName("result")
    private val _result: String,
    val playDate: String,
    val kda: String,
    val integral: String,
    val lastHits: String,
    val denies: String,
    val goldPerMin: String,
    val xpPerMin: String
) {
    val win
        get() = _result == "1"
}
