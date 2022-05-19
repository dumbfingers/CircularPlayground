package com.example.circularplayground.ui.detail

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.circularplayground.R
import com.example.circularplayground.theme.CircularPlaygroundTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class DetailScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var context: Context

    @Before
    fun setup() {
        hiltRule.inject()
        context = composeTestRule.activity
    }

    @After
    fun breakDown() {
        composeTestRule.activityRule.scenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun when_init_show_all_titles() {
        show(DetailViewState())
        composeTestRule.apply {
            onNodeWithText(context.getString(R.string.title_credit_status))
                .assertIsDisplayed()
            onNodeWithText(context.getString(R.string.title_days_next_report))
                .assertIsDisplayed()
            onNodeWithText(context.getString(R.string.title_short_term_credit_usage))
                .assertIsDisplayed()
            onNodeWithText(context.getString(R.string.title_change_short_term_credit))
                .assertIsDisplayed()
            onNodeWithText(context.getString(R.string.title_long_term_credit_usage))
                .assertIsDisplayed()
            onNodeWithText(context.getString(R.string.title_change_long_term_credit))
                .assertIsDisplayed()
            onAllNodesWithContentDescription("icon indicator for short term credit changes")[0]
                .assertIsDisplayed()
            onAllNodesWithContentDescription("icon indicator for short term credit changes")[1]
                .assertIsDisplayed()
        }
    }

    @Test
    fun show_detailData() {
        val detailViewState = DetailViewState(
            currentShortTermUtilisation = 44,
            changeInShortTerm = 500,
            currentLongTermUtilisation = null,
            changeInLongTerm = -300,
            daysUntilNextReport = 20,
        )
        show(detailViewState)
        composeTestRule.apply {
            onNodeWithText(detailViewState.daysUntilNextReport.toString())
                .assertIsDisplayed()
            val currentShort = detailViewState.currentShortTermUtilisation
            if (currentShort != null) {
                onAllNodes(hasProgressBarRangeInfo(rangeInfo = ProgressBarRangeInfo(current = currentShort/100f, range = 0f..1f)))[0]
                    .assertIsDisplayed()
            }
            onNodeWithText(detailViewState.changeInShortTerm.toString())
                .assertIsDisplayed()
            val currentLongTermUtilisation = detailViewState.currentLongTermUtilisation?.toFloat()
            if (currentLongTermUtilisation != null) {
                onAllNodes(hasProgressBarRangeInfo(rangeInfo = ProgressBarRangeInfo(current = currentLongTermUtilisation/100f, range = 0f..1f)))[0]
                    .assertIsDisplayed()
            }
            onNodeWithText(detailViewState.changeInLongTerm.toString())
                .assertIsDisplayed()
        }
    }

    private fun show(state: DetailViewState) {
        composeTestRule.setContent {
            CircularPlaygroundTheme {
                DetailScreen(
                    viewState = state
                )
            }
        }
    }
}