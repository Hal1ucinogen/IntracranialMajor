package com.naughtyenigma.intracranialmajor.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.naughtyenigma.intracranialmajor.ui.home.HomeSections
import com.naughtyenigma.intracranialmajor.ui.match.MatchDetail
import com.naughtyenigma.intracranialmajor.ui.user.UserPage

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val MATCH_DETAIL_ROUTE = "match"
    const val MATCH_ID_KEY = "matchId"
}

@Composable
fun IMNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.HOME_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = MainDestinations.HOME_ROUTE,
            startDestination = HomeSections.RANK.route
        ) {
            addHomeGraph(
                onMatchSelected = { matchId: Long, from: NavBackStackEntry ->
                    if (from.isLifecycleResumed()) {
                        navController.navigate("${MainDestinations.MATCH_DETAIL_ROUTE}/$matchId")
                    }
                },
                modifier = modifier
            )
        }
        composable(
            route = "${MainDestinations.MATCH_DETAIL_ROUTE}/{${MainDestinations.MATCH_ID_KEY}}",
            arguments = listOf(navArgument(MainDestinations.MATCH_ID_KEY) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val matchId = arguments.getLong(MainDestinations.MATCH_ID_KEY)
            MatchDetail(matchId = matchId, onUpPressed = { navController.navigateUp() })
        }
    }
}

private fun NavGraphBuilder.addHomeGraph(
    onMatchSelected: (Long, NavBackStackEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    composable(HomeSections.RANK.route) {

    }
    composable(HomeSections.TOP.route) {

    }
    composable(HomeSections.USER.route) {
        UserPage(modifier)
    }
}

private fun NavBackStackEntry.isLifecycleResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
