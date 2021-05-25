package com.naughtyenigma.intracranialmajor.model

import com.google.gson.annotations.SerializedName

data class Match(
    val heroName: String,
    val heroId: Int,
    val heroIcon: String,
    @SerializedName("result")
    private val _result: String,
    val playDate: String,
    @SerializedName("isDouble")
    private val _isDouble: String,
    val kda: String,
    val integral: String,
    val lastHits: String,
    val denies: String,
    val goldPerMin: String,
    val xpPerMin: String
) {
    val win
        get() = _result == "1"
    val rankDoubleDown
        get() = _isDouble == "1"
}

val matches = listOf(
    Match(
        "风行者",
        21,
        "https://cdn.dota2.com/apps/dota2/images/heroes/windrunner_lg.png",
        "1",
        "2021-05-22 22:34:52",
        "0",
        "2/12/15",
        "3670+90",
        "170",
        "3",
        "338",
        "436"
    ),
    Match(
        "树精卫士",
        21,
        "https://cdn.dota2.com/apps/dota2/images/heroes/treant_lg.png",
        "0",
        "2021-05-21 23:00:00",
        "0",
        "3/2/26",
        "3560+110",
        "35",
        "1",
        "275",
        "466"
    ),
    Match(
        "食人魔魔法师",
        21,
        "https://cdn.dota2.com/apps/dota2/images/heroes/ogre_magi_lg.png",
        "0",
        "2021-05-22 22:34:52",
        "0",
        "2/12/15",
        "3670+90",
        "170",
        "3",
        "338",
        "436"
    ),
    Match(
        "树精卫士",
        21,
        "https://cdn.dota2.com/apps/dota2/images/heroes/windrunner_lg.png",
        "1",
        "2021-05-21 23:00:00",
        "0",
        "3/2/26",
        "3560+110",
        "35",
        "1",
        "275",
        "466"
    ),
    Match(
        "风行者",
        21,
        "https://cdn.dota2.com/apps/dota2/images/heroes/windrunner_lg.png",
        "0",
        "2021-05-22 22:34:52",
        "0",
        "2/12/15",
        "3670+90",
        "170",
        "3",
        "338",
        "436"
    ),
    Match(
        "树精卫士",
        21,
        "https://cdn.dota2.com/apps/dota2/images/heroes/windrunner_lg.png",
        "1",
        "2021-05-21 23:00:00",
        "0",
        "3/2/26",
        "3560+110",
        "35",
        "1",
        "275",
        "466"
    ),
    Match(
        "风行者",
        21,
        "https://cdn.dota2.com/apps/dota2/images/heroes/windrunner_lg.png",
        "1",
        "2021-05-22 22:34:52",
        "0",
        "2/12/15",
        "3670+90",
        "170",
        "3",
        "338",
        "436"
    ),
    Match(
        "树精卫士",
        21,
        "https://cdn.dota2.com/apps/dota2/images/heroes/windrunner_lg.png",
        "0",
        "2021-05-21 23:00:00",
        "0",
        "3/2/26",
        "3560+110",
        "35",
        "1",
        "275",
        "466"
    ),
    Match(
        "风行者",
        21,
        "https://cdn.dota2.com/apps/dota2/images/heroes/windrunner_lg.png",
        "1",
        "2021-05-22 22:34:52",
        "0",
        "2/12/15",
        "3670+90",
        "170",
        "3",
        "338",
        "436"
    ),
    Match(
        "树精卫士",
        21,
        "https://cdn.dota2.com/apps/dota2/images/heroes/windrunner_lg.png",
        "1",
        "2021-05-21 23:00:00",
        "0",
        "3/2/26",
        "3560+110",
        "35",
        "1",
        "275",
        "466"
    ),

    )
