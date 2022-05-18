package com.example.circularplayground.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.circularplayground.MainViewModel
import com.example.circularplayground.MainViewState
import com.example.circularplayground.ui.theme.CircularPlaygroundTheme
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
internal fun MainScreen(
    openDetails: (clientRef: String) -> Unit,
) {
    val viewModel = hiltViewModel<MainViewModel>()
    val viewState by viewModel.state.collectAsState()
    MainScreen(
        viewState = viewState,
        openDetails = openDetails
    )
}

@Composable
internal fun MainScreen(
    viewState: MainViewState,
    openDetails: (clientRef: String) -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.secondary) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ScoreCircularIndicator(
                currentScore = viewState.currentScore,
                totalScore = viewState.totalScore,
                isLoading = viewState.isLoading,
                onClick = {
                    openDetails(viewState.clientRef)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    CircularPlaygroundTheme {
        MainScreen(
            viewState = MainViewState(
                isLoading = false,
                currentScore = 327,
                totalScore = 700,
                error = ""
            ),
            openDetails = {}
        )
    }
}
