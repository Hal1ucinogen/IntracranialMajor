package com.naughtyenigma.intracranialmajor.ui.home

import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.insets.navigationBarsPadding
import com.naughtyenigma.intracranialmajor.R
import com.naughtyenigma.intracranialmajor.ui.component.IMSurface
import com.naughtyenigma.intracranialmajor.ui.theme.IMTheme

private val TextIconSpacing = 2.dp
private val BottomNavLabelTransformOrigin = TransformOrigin(0f, 0.5f)
private val BottomNavHeight = 56.dp
private val BottomNavIndicatorShape = RoundedCornerShape(percent = 50)
private val BottomNavigationItemPadding = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)


enum class HomeSections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    RANK(R.string.home_rank, Icons.Outlined.List, "home/rank"),
    TOP(R.string.home_top, Icons.Outlined.DateRange, "home/top"),
    USER(R.string.home_user, Icons.Outlined.AccountCircle, "home/user")
}

@Composable
fun IMBottomBar(
    navController: NavController,
    tabs: Array<HomeSections>,
    color: Color = IMTheme.colors.iconPrimary,
    contentColor: Color = IMTheme.colors.iconInteractive
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val sections = remember { HomeSections.values() }
    val routes = remember { sections.map { it.route } }
    if (currentRoute in routes) {
        val currentSection = sections.first { it.route == currentRoute }
        IMSurface(color = color, contentColor = contentColor) {
            val springSpec = SpringSpec<Float>(stiffness = 800f, dampingRatio = 0.8f)
            IMBottomNavLayout(
                selectedIndex = currentSection.ordinal,
                itemCount = routes.size,
                animSpec = springSpec,
                indicator = { IMBottomNavIndicator() },
                modifier = Modifier.navigationBarsPadding(start = false, end = false)
            ) {
                tabs.forEach { section ->
                    val selected = section == currentSection
                    val tint by animateColorAsState(
                        if (selected) {
                            IMTheme.colors.iconInteractive
                        } else {
                            IMTheme.colors.iconInteractiveInactive
                        }
                    )
                    IMBottomNavItem(
                        icon = {
                            Icon(
                                imageVector = section.icon,
                                tint = tint,
                                contentDescription = null
                            )
                        },
                        text = {
                            Text(
                                text = stringResource(section.title),
                                color = tint,
                                style = MaterialTheme.typography.button,
                                maxLines = 1
                            )
                        },
                        selected = selected,
                        onSelected = {
                            if (section.route != currentRoute) {
                                navController.navigate(section.route) {
                                    launchSingleTop = true
                                    restoreState = true
                                    popUpTo(findStartDestination(navController.graph).id) {
                                        saveState = true
                                    }
                                }
                            }
                        },
                        animSpec = springSpec,
                        modifier = BottomNavigationItemPadding.clip(BottomNavIndicatorShape)
                    )
                }
            }
        }
    }
}

private tailrec fun findStartDestination(graph: NavDestination): NavDestination {
    return if (graph is NavGraph) findStartDestination(graph.startDestination!!) else graph
}

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)

@Composable
private fun IMBottomNavLayout(
    selectedIndex: Int,
    itemCount: Int,
    animSpec: AnimationSpec<Float>,
    indicator: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val selectionFractions = remember(itemCount) {
        List(itemCount) { i ->
            Animatable(if (i == selectedIndex) 1f else 0f)
        }
    }
    selectionFractions.forEachIndexed { index, selectionFraction ->
        val target = if (index == selectedIndex) 1f else 0f
        LaunchedEffect(target, animSpec) {
            selectionFraction.animateTo(target, animSpec)
        }
    }
    val indicatorIndex = remember { Animatable(0f) }
    val targetIndicatorIndex = selectedIndex.toFloat()
    LaunchedEffect(targetIndicatorIndex) {
        indicatorIndex.animateTo(targetIndicatorIndex, animSpec)
    }
    Layout(
        modifier = modifier.height(BottomNavHeight),
        content = {
            content()
            Box(Modifier.layoutId("indicator"), content = indicator)
        }
    ) { measurableList, constraints ->
        check(itemCount == (measurableList.size - 1))
        val unselectedWidth = constraints.maxWidth / (itemCount + 1)
        val selectedWidth = unselectedWidth * 2
        val indicatorMeasurable = measurableList.first { it.layoutId == "indicator" }
        val itemPlaceableList = measurableList
            .filterNot { it == indicatorMeasurable }
            .mapIndexed { index, measurable ->
                val width = lerp(unselectedWidth, selectedWidth, selectionFractions[index].value)
                measurable.measure(constraints.copy(minWidth = width, maxWidth = width))
            }
        val indicatorPlaceable = indicatorMeasurable.measure(
            constraints.copy(minWidth = selectedWidth, maxWidth = selectedWidth)
        )
        layout(
            width = constraints.maxWidth,
            height = itemPlaceableList.maxByOrNull { it.height }?.height ?: 0
        ) {
            val indicatorLeft = indicatorIndex.value * unselectedWidth
            indicatorPlaceable.placeRelative(x = indicatorLeft.toInt(), y = 0)
            var x = 0
            itemPlaceableList.forEach { placeable ->
                placeable.placeRelative(x = x, y = 0)
                x += placeable.width
            }
        }
    }
}

@Composable
private fun IMBottomNavIndicator(
    strokeWidth: Dp = 2.dp,
    color: Color = IMTheme.colors.iconInteractive,
    shape: Shape = BottomNavIndicatorShape
) {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .then(BottomNavigationItemPadding)
            .border(strokeWidth, color, shape)
    )
}

@Composable
private fun IMBottomNavItem(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    selected: Boolean,
    onSelected: () -> Unit,
    animSpec: AnimationSpec<Float>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.selectable(selected = selected, onClick = onSelected),
        contentAlignment = Alignment.Center
    ) {
        val animationProgress by animateFloatAsState(if (selected) 1f else 0f, animSpec)
        IMBottomNavItemLayout(icon = icon, text = text, animationProgress = animationProgress)
    }
}

@Composable
private fun IMBottomNavItemLayout(
    icon: @Composable BoxScope.() -> Unit,
    text: @Composable BoxScope.() -> Unit,
    @FloatRange(from = 0.0, to = 1.0) animationProgress: Float
) {
    Layout(
        content = {
            Box(
                modifier = Modifier
                    .layoutId("icon")
                    .padding(horizontal = TextIconSpacing),
                content = icon
            )
            val scale = lerp(0.6f, 1f, animationProgress)
            Box(
                modifier = Modifier
                    .layoutId("text")
                    .padding(horizontal = TextIconSpacing)
                    .graphicsLayer {
                        alpha = animationProgress
                        scaleX = scale
                        scaleY = scale
                        transformOrigin = BottomNavLabelTransformOrigin
                    },
                content = text
            )
        }
    ) { measurableList, constraints ->
        val iconPlaceable = measurableList.first { it.layoutId == "icon" }.measure(constraints)
        val textPlaceable = measurableList.first { it.layoutId == "text" }.measure(constraints)
        placeIconAndText(
            iconPlaceable,
            textPlaceable,
            constraints.maxWidth,
            constraints.maxHeight,
            animationProgress
        )
    }
}

private fun MeasureScope.placeIconAndText(
    iconPlaceable: Placeable,
    textPlaceable: Placeable,
    width: Int,
    height: Int,
    @FloatRange(from = 0.0, to = 1.0) animationProgress: Float
): MeasureResult {
    val iconY = (height - iconPlaceable.height) / 2
    val textY = (height - textPlaceable.height) / 2
    val textWidth = textPlaceable.width * animationProgress
    val iconX = (width - textWidth - iconPlaceable.width) / 2
    val textX = iconX + iconPlaceable.width
    return layout(width, height) {
        iconPlaceable.placeRelative(iconX.toInt(), iconY)
        if (animationProgress != 0f) {
            textPlaceable.placeRelative(textX.toInt(), textY)
        }
    }
}
