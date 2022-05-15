package com.example.circularplayground.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.circularplayground.MainViewState
import com.example.circularplayground.ui.theme.CircularPlaygroundTheme

@Composable
internal fun MainScreen(
    viewState: MainViewState,
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.secondary) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ScoreCircularIndicator(
                currentScore = viewState.currentScore,
                totalScore = viewState.totalScore,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    CircularPlaygroundTheme {
        MainScreen(
            MainViewState(327, 700)
        )
    }
}
