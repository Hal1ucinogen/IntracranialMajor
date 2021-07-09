package com.naughtyenigma.intracranialmajor.model

import androidx.compose.runtime.Immutable

@Immutable
data class TopCollection(
    val title: String,
    val items: List<TopItem>
)

private val collectionSample = TopCollection("胜率榜", topItemSamples)

val topCollections = listOf(collectionSample, collectionSample, collectionSample, collectionSample)
