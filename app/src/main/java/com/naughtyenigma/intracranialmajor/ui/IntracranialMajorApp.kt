package com.naughtyenigma.intracranialmajor.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.systemBarsPadding
import com.naughtyenigma.intracranialmajor.ui.theme.IntracranialMajorTheme

@Composable
fun IntracranialMajorApp() {
    ProvideWindowInsets {
        IntracranialMajorTheme {
            Scaffold {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.systemBarsPadding()
                ) {
                    Text(text = "Hello Intracranial Major!")
                }
            }
        }
    }
}
