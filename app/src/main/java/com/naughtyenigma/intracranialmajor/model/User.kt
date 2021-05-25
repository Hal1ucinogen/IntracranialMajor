package com.naughtyenigma.intracranialmajor.model

import androidx.compose.runtime.Immutable

@Immutable
data class User(
    val accountId: Long,
    val avatarUrl: String,
    val nickName: String,
    val integral: Int,
    val rank: Int,
    val winRate: String,
    val token: String,
    val doubleCount: Int,
    val isDouble: Boolean
)

val userSample = User(
    122431670,
    "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/02/02fd43d5926460d730ec828f3a00451610801021_full.jpg",
    "莫太冲",
    3760,
    1,
    "61.76",
    "8134a071f038f9ba3bec6faa2dd33f34",
    2,
    false
)
