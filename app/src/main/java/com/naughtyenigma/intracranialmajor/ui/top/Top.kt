package com.naughtyenigma.intracranialmajor.ui.top

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.naughtyenigma.intracranialmajor.model.TopCollection
import com.naughtyenigma.intracranialmajor.model.TopItem
import com.naughtyenigma.intracranialmajor.model.topCollections
import com.naughtyenigma.intracranialmajor.ui.component.IMSurface
import com.naughtyenigma.intracranialmajor.ui.component.IMDivider
import com.naughtyenigma.intracranialmajor.ui.theme.IMTheme

private val ItemCardWidth = 170.dp
private val ItemCardPadding = 16.dp

@Composable
fun Top() {
    val topCollections = remember { topCollections }
    TopList(topCollections)
}

@Composable
private fun TopList(
    topCollections: List<TopCollection>,
    modifier: Modifier = Modifier
) {
    IMSurface(modifier = modifier.fillMaxSize()) {
        LazyColumn() {
            itemsIndexed(topCollections) { index, item ->
                if (index > 0) {
                    IMDivider()
                }
            }
        }
    }
}

@Composable
private fun TopCollection(
    topCollection: TopCollection,
    modifier: Modifier = Modifier,
    index: Int = 0
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .heightIn(min = 56.dp)
                .padding(start = 24.dp)
        ) {
            Text(
                text = topCollection.title,
                style = MaterialTheme.typography.h6,
                color = IMTheme.colors.brand,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.Start)
            )
            IconButton(onClick = { }, modifier = Modifier.align(Alignment.CenterVertically)) {
                Icon(
                    imageVector = Icons.Outlined.ArrowForward,
                    contentDescription = null,
                    tint = IMTheme.colors.brand
                )
            }
        }
        val scroll = rememberScrollState(0)
        val gradient = if ((index / 2) % 2 == 0) {
            IMTheme.colors.gradient6_1
        } else {
            IMTheme.colors.gradient6_2
        }
        val gradientWidth = with(LocalDensity.current) {
            (6 * (ItemCardWidth + ItemCardPadding).toPx())
        }
        LazyRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(ItemCardPadding),
            contentPadding = PaddingValues(horizontal = 24.dp)
        ) {
            itemsIndexed(topCollection.items) { i, topItem ->

            }
        }
    }
}

@Composable
private fun TopUserItem(
    topItem:TopItem,
    onItemClick:(Long) -> Unit,
    index:Int,

) {

}
