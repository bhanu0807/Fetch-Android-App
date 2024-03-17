package com.fetch.app.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fetch.app.fake.FakeNetworkRewardsRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var viewModel: RewardsViewModel

    @Before
    fun setup() {
        viewModel = RewardsViewModel(
            rewardsRepository = FakeNetworkRewardsRepository()
        )
        composeTestRule.setContent {
            HomeScreen(viewModel = viewModel)
        }
    }

    @Test
    fun whenLoaded_ListItemsExist() {
        composeTestRule.onNodeWithText("List 1").assertExists()
        composeTestRule.onNodeWithText("List 2").assertExists()
    }

}