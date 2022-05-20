package com.example.circularplayground.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.circularplayground.R
import com.example.circularplayground.theme.CircularPlaygroundTheme
import com.example.circularplayground.ui.ScoreCircularIndicator

@Composable
internal fun MainScreen(
    openDetails: (clientRef: String) -> Unit,
) {
    val viewModel = hiltViewModel<MainViewModel>()
    val viewState by viewModel.state.collectAsState()
    MainScreen(
        viewState = viewState,
        openDetails = openDetails,
        onRetry = { viewModel.refresh() }
    )
}

@Composable
internal fun MainScreen(
    viewState: MainViewState,
    openDetails: (clientRef: String) -> Unit,
    onRetry: () -> Unit
) {
    val actionLabel = stringResource(id = R.string.action_retry)
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        content = {
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
    )

    if (viewState.error.isNotEmpty()) {
        LaunchedEffect(
            key1 = Unit,
            block = {
                val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = viewState.error,
                    actionLabel = actionLabel,
                )
                if (snackbarResult == SnackbarResult.ActionPerformed) {
                    onRetry()
                }
            },
        )
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
            openDetails = {},
            onRetry = {}
        )
    }
}
