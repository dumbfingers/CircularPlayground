package com.example.circularplayground.ui.detail

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.circularplayground.R
import com.example.circularplayground.theme.CircularPlaygroundTheme

@Composable
internal fun DetailScreen() {
    val viewModel = hiltViewModel<DetailViewModel>()
    val viewState by viewModel.state.collectAsState()
    DetailScreen(viewState = viewState)
}

@Composable
internal fun DetailScreen(
    viewState: DetailViewState,
) {
    val currentShortTermUtilisation by remember(key1 = viewState.currentShortTermUtilisation) {
        mutableStateOf(viewState.currentShortTermUtilisation)
    }
    val currentLongTermUtilisation by remember(key1 = viewState.currentLongTermUtilisation) {
        mutableStateOf(viewState.currentLongTermUtilisation)
    }
    val shortTermTint = getIconTint(progress = currentShortTermUtilisation?.toFloat())
    val longTermTint = getIconTint(progress = currentLongTermUtilisation?.toFloat())

    Surface(color = MaterialTheme.colors.secondary) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.title_credit_status),
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center
            )
            InfoCard(
                modifier = Modifier.fillMaxWidth(),
                titleRes = R.string.title_days_next_report,
                content = "${viewState.daysUntilNextReport ?: 0}"
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ProgressBarCard(
                    modifier = Modifier.weight(0.5f),
                    title = AnnotatedString(stringResource(id = R.string.title_short_term_credit_usage)),
                    progress = currentShortTermUtilisation?.toFloat()
                )
                InfoCard(
                    modifier = Modifier.weight(0.5f),
                    titleRes = R.string.title_change_short_term_credit,
                    content = "${viewState.changeInShortTerm ?: 0}",
                    change = viewState.changeInShortTerm ?: 0,
                    tint = shortTermTint
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ProgressBarCard(
                    modifier = Modifier.weight(0.5f),
                    title = AnnotatedString(stringResource(id = R.string.title_long_term_credit_usage)),
                    progress = currentLongTermUtilisation?.toFloat()
                )
                InfoCard(
                    modifier = Modifier.weight(0.5f),
                    titleRes = R.string.title_change_long_term_credit,
                    content = "${viewState.changeInLongTerm ?: 0}",
                    change = viewState.changeInLongTerm ?: 0,
                    tint = longTermTint
                )
            }
        }
    }
}

@Composable
private fun getIconTint(progress: Float?): Color {
    return if (progress != null && progress > 50f) {
        MaterialTheme.colors.error
    } else {
        MaterialTheme.colors.secondary
    }
}

@Composable
private fun ProgressBarCard(
    modifier: Modifier = Modifier,
    title: AnnotatedString,
    progress: Float? = null,
) {
    Card(modifier = modifier.defaultMinSize(minHeight = 108.dp), elevation = 8.dp) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle1,
            )
            if (progress != null) {
                LinearProgressIndicator(progress = progress / 100f)
            }
        }
    }
}

@Composable
private fun InfoCard(
    modifier: Modifier = Modifier,
    @StringRes
    titleRes: Int,
    content: String,
    change: Long,
    tint: Color,
) {
    val iconRes = if (change > 0) {
        R.drawable.ic_baseline_thumb_down_24
    } else {
        R.drawable.ic_baseline_thumb_up_24
    }
    Card(modifier = modifier.defaultMinSize(minHeight = 108.dp), elevation = 8.dp) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(text = stringResource(id = titleRes))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterHorizontally)) {
                Text(
                    text = content,
                    color = tint,
                    style = MaterialTheme.typography.subtitle2,
                )
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = "icon indicator for short term credit changes",
                    tint = tint
                )
            }
        }
    }
}

@Composable
private fun InfoCard(
    modifier: Modifier = Modifier,
    @StringRes
    titleRes: Int,
    content: String,
) {
    Card(modifier = modifier, elevation = 8.dp) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = titleRes),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = content,
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDetailScreen() {
    CircularPlaygroundTheme {
        DetailScreen(viewState = DetailViewState())
    }
}

@Preview
@Composable
private fun PreviewProgressCard() {
    CircularPlaygroundTheme {
        ProgressBarCard(
            title = AnnotatedString(text = stringResource(id = R.string.title_long_term_credit_usage)),
            progress = 44.0f
        )
    }
}