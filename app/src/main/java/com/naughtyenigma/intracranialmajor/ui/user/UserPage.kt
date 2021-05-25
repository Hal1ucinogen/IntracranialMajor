package com.naughtyenigma.intracranialmajor.ui.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.naughtyenigma.intracranialmajor.R
import com.naughtyenigma.intracranialmajor.model.Match
import com.naughtyenigma.intracranialmajor.model.User
import com.naughtyenigma.intracranialmajor.model.matches
import com.naughtyenigma.intracranialmajor.model.userSample
import com.naughtyenigma.intracranialmajor.ui.component.AvatarImage
import com.naughtyenigma.intracranialmajor.ui.component.IntracranialMajorDivider
import com.naughtyenigma.intracranialmajor.ui.theme.Ocean3
import com.naughtyenigma.intracranialmajor.ui.theme.Shadow4
import kotlin.math.max
import kotlin.math.min

private val BottomBarHeight = 56.dp
private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val ImageOverlap = 115.dp
private val MinTitleOffset = 56.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = ImageOverlap + MinTitleOffset + GradientScroll
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 150.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)

@Composable
fun UserPage() {
    val user = remember { userSample }
    Box(modifier = Modifier.fillMaxSize()) {
        val scroll = rememberScrollState(0)
        Header()
        Body(matches, scroll)
        Title(user, scroll.value)
        Avatar(user.avatarUrl, scroll.value)
    }
}

@Composable
private fun Header() {
    Spacer(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    listOf(Shadow4, Ocean3)
                )
            )
    )
}

@Composable
private fun Body(
    matches: List<Match>,
    scroll: ScrollState
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(MinTitleOffset)
        )
        Column(
            modifier = Modifier.verticalScroll(scroll)
        ) {
            Spacer(Modifier.height(GradientScroll))
            Surface(modifier = Modifier.fillMaxSize()) {
                Column {
                    Spacer(Modifier.height(ImageOverlap))
                    Spacer(Modifier.height(TitleHeight))
                    Spacer(Modifier.height(16.dp))
                    matches.forEach { match ->
                        MatchItem(match = match)
                    }
                    Spacer(
                        modifier = Modifier
                            .padding(bottom = BottomBarHeight)
                            .navigationBarsPadding(left = false, right = false)
                            .height(8.dp)
                    )
                }
            }

        }
    }
}

@Composable
private fun MatchItem(
    match: Match,
    modifier: Modifier = Modifier
) {

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 24.dp)
    ) {
        val (heroAvatar, divider, resultSymbol, heroName, kda, spacer, mmr) = createRefs()
        createVerticalChain(heroName, spacer, kda, chainStyle = ChainStyle.Packed)
        AvatarImage(
            imageUrl = match.heroIcon,
            contentDescription = null,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .size(64.dp, 36.dp)
                .constrainAs(heroAvatar) {
                    top.linkTo(parent.top, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                }
        )
        Text(
            text = match.heroName,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.constrainAs(heroName) {
                linkTo(
                    start = heroAvatar.end,
                    startMargin = 16.dp,
                    end = parent.end,
                    endMargin = 16.dp,
                    bias = 0f
                )
            }
        )
        Text(
            text = match.kda,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSecondary,
            modifier = Modifier.constrainAs(kda) {
                linkTo(
                    start = heroAvatar.end,
                    startMargin = 16.dp,
                    end = parent.end,
                    endMargin = 16.dp,
                    bias = 0f
                )
            }
        )
        Spacer(
            Modifier
                .height(8.dp)
                .constrainAs(spacer) {
                    linkTo(top = heroName.bottom, bottom = kda.top)
                }
        )
        Text(
            text = match.integral,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onSecondary,
            modifier = Modifier.constrainAs(mmr) {
                top.linkTo(parent.top, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 16.dp)
                end.linkTo(resultSymbol.start, margin = 16.dp)
            }
        )
        Image(
            painter = painterResource(id = if (match.win) R.drawable.ic_match_win else R.drawable.ic_match_lose),
            contentDescription = null,
            modifier = Modifier
                .size(36.dp)
                .constrainAs(resultSymbol) {
                    top.linkTo(parent.top, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    end.linkTo(parent.end)
                }
        )
        IntracranialMajorDivider(
            Modifier.constrainAs(divider) {
                linkTo(start = parent.start, end = parent.end)
                top.linkTo(parent.bottom)
            }
        )
    }
}

@Composable
private fun Title(user: User, scroll: Int) {
    val maxOffset = with(LocalDensity.current) { MaxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }
    val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = TitleHeight)
            .statusBarsPadding()
            .graphicsLayer { translationY = offset }
            .background(color = MaterialTheme.colors.background)
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = user.nickName,
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onSurface,
            modifier = HzPadding
        )
        Text(
            text = "MMR - ${user.integral}",
            style = MaterialTheme.typography.subtitle2,
            fontSize = 20.sp,
            color = MaterialTheme.colors.onSecondary,
            modifier = HzPadding
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "Rank - ${user.rank}",
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary,
            modifier = HzPadding
        )
        Spacer(Modifier.height(8.dp))
        IntracranialMajorDivider()
    }
}

@Composable
private fun Avatar(url: String, scroll: Int) {
    val collapseRange = with(LocalDensity.current) { (MaxTitleOffset - MinTitleOffset).toPx() }
    val collapseFraction = (scroll / collapseRange).coerceIn(0f, 1f)
    CollapsingImageLayout(
        collapseFraction,
        HzPadding.then(Modifier.statusBarsPadding())
    ) {
        AvatarImage(
            imageUrl = url,
            contentDescription = "avatar",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CollapsingImageLayout(
    collapseFraction: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurableList, constraints ->
        check(measurableList.size == 1)

        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable = measurableList[0].measure(Constraints.fixed(imageWidth, imageWidth))

        val imageY = lerp(MinTitleOffset, MinImageOffset, collapseFraction).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth - imageWidth) / 2, // centered when expanded
            constraints.maxWidth - imageWidth, // right aligned when collapsed
            collapseFraction
        )
        layout(
            width = constraints.maxWidth,
            height = imageY + imageWidth
        ) {
            imagePlaceable.place(imageX, imageY)
        }
    }
}
