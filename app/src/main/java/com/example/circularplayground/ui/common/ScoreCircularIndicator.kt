package com.example.circularplayground.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.circularplayground.R
import com.example.circularplayground.theme.CircularPlaygroundTheme

@Composable
internal fun ScoreCircularIndicator(
    modifier: Modifier = Modifier,
    currentScore: Int,
    totalScore: Int,
    isLoading: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(192.dp)
            .clickable(
                role = Role.Button,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = CircleShape,
                )
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            val annotatedString = if (isLoading) {
                AnnotatedString(stringResource(id = R.string.title_loading))
            } else {
                val progressString = currentScore.toString()
                val titleString = stringResource(id = R.string.title_credit_score, progressString)
                val index = titleString.indexOf(progressString)
                val style = MaterialTheme.typography.h2
                AnnotatedString(
                    text = titleString,
                    spanStyles = listOf(
                        AnnotatedString.Range(
                            item = SpanStyle(
                                fontSize = style.fontSize,
                                fontWeight = style.fontWeight,
                                color = MaterialTheme.colors.primary
                            ),
                            start = index,
                            end = index + progressString.length
                        )
                    )
                )
            }
            Text(
                text = annotatedString,
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center,
            )
        }

        val brush = SolidColor(MaterialTheme.colors.onSurface)
        Canvas(
            modifier = Modifier.fillMaxSize(0.95f),
            onDraw = {
                drawCircle(
                    brush = brush,
                    radius = size.width / 2f,
                    style = Stroke(width = 1.dp.toPx()),
                )
            },
        )
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(0.9f),
                strokeWidth = 2.dp,
            )
        } else {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(0.9f),
                progress = currentScore.toFloat() / totalScore.toFloat(),
                strokeWidth = 2.dp,
            )
        }
    }
}

@Preview
@Composable
private fun ScoreCircularIndicatorLoaded() {
    CircularPlaygroundTheme {
        ScoreCircularIndicator(isLoading = false, currentScore = 327, totalScore = 700) {}
    }
}

@Preview
@Composable
private fun ScoreCircularIndicatorLoading() {
    CircularPlaygroundTheme {
        ScoreCircularIndicator(isLoading = true, currentScore = 327, totalScore = 700) {}
    }
}