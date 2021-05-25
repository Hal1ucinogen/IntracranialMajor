package com.naughtyenigma.intracranialmajor.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.google.accompanist.insets.ProvideWindowInsets
import com.naughtyenigma.intracranialmajor.ui.theme.IntracranialMajorTheme
import com.naughtyenigma.intracranialmajor.ui.user.UserPage

@Composable
fun IntracranialMajorApp() {
    ProvideWindowInsets {
        IntracranialMajorTheme {
            Scaffold {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    UserPage()
                }
            }
        }
    }
}
