package com.naughtyenigma.intracranialmajor.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.naughtyenigma.intracranialmajor.ui.component.IMScaffold
import com.naughtyenigma.intracranialmajor.ui.home.HomeSections
import com.naughtyenigma.intracranialmajor.ui.home.IMBottomBar
import com.naughtyenigma.intracranialmajor.ui.theme.IntracranialMajorTheme

@Composable
fun IntracranialMajorApp() {
    ProvideWindowInsets {
        IntracranialMajorTheme {
            val tabs = remember { HomeSections.values() }
            val navController = rememberNavController()
            IMScaffold(
                bottomBar = {
                    IMBottomBar(
                        navController = navController,
                        tabs = tabs
                    )
                }
            ) { innerPaddingModifier ->
                IMNavGraph(
                    navController = navController,
                    modifier = Modifier.padding(innerPaddingModifier)
                )
            }
        }
    }
}
