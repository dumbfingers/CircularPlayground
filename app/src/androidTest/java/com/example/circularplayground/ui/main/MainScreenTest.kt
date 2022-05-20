package com.example.circularplayground.ui.main

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performSemanticsAction
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.circularplayground.R
import com.example.circularplayground.theme.CircularPlaygroundTheme
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class MainScreenTest {

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
    fun when_isLoading_isTrue_only_show_spinner_with_title() {
        val state = MainViewState(isLoading = true)
        show(state)
        composeTestRule.apply {
            onNodeWithText(context.getString(R.string.title_loading))
                .assertIsDisplayed()
            onNodeWithTag("loading")
                .assertIsDisplayed()
        }
    }

    @Test
    fun when_score_isLoaded_show_donut_with_score_title() {
        val state = MainViewState(
            isLoading = false,
            currentScore = 500,
            totalScore = 700
        )
        show(state)
        composeTestRule.apply {
            onNodeWithText(context.getString(R.string.title_credit_score, state.currentScore))
                .assertIsDisplayed()
            onNodeWithTag("donut")
                .assertIsDisplayed()
        }
    }

    @Test
    fun when_error_isNotEmpty_show_snackbar() {
        val state = MainViewState(
            error = "Error"
        )
        show(state)
        composeTestRule.apply {
            onNodeWithText(state.error)
                .assertIsDisplayed()
            onNodeWithText(context.getString(R.string.action_retry))
                .assertIsDisplayed()
                .assertHasClickAction()
        }
    }

    @Test
    fun when_error_isNotEmpty_verify_snackbar_retry_action() {
        val state = MainViewState(
            error = "Error"
        )
        val action: () -> Unit = mockk(relaxed = true)
        show(state = state, onRetry = action)
        composeTestRule.apply {
            onNodeWithText(context.getString(R.string.action_retry))
                .assertHasClickAction()
                .performClick()
            verify { action() }
        }
    }

    @Test
    fun when_click_donut_verify_openDetails_action() {
        val state = MainViewState(
            isLoading = false,
            clientRef = "ref-010",
            currentScore = 500,
            totalScore = 700
        )
        val action: (String) -> Unit = mockk(relaxed = true)
        show(state = state, openDetails = action)
        composeTestRule.apply {
            onNodeWithTag("donut")
                .onParent()
                .performSemanticsAction(SemanticsActions.OnClick)
            val slot = slot<String>()
            verify { action(capture(slot)) }
            assertThat(slot.captured).isEqualTo(state.clientRef)
        }
    }

    @Test
    fun when_in_error_state_disables_click() {
        val state = MainViewState(
            isLoading = false,
            error = "Error",
            currentScore = 500,
            totalScore = 700
        )
        val action: (String) -> Unit = mockk(relaxed = true)
        show(state = state, openDetails = action)
        composeTestRule.apply {
            onNodeWithTag("donut")
                .onParent()
                .assertHasClickAction()
                .assertIsNotEnabled()
        }
    }

    private fun show(
        state: MainViewState,
        openDetails: (String) -> Unit = {},
        onRetry: () -> Unit = {},
    ) {
        composeTestRule.setContent {
            CircularPlaygroundTheme {
                MainScreen(
                    viewState = state,
                    openDetails = openDetails,
                    onRetry = onRetry
                )
            }
        }
    }

}