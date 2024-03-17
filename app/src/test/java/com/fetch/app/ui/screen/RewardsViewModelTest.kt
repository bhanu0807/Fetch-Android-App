package com.fetch.app.ui.screen

import com.fetch.app.fake.FakeDataSource
import com.fetch.app.fake.FakeNetworkRewardsRepository
import com.fetch.app.rule.TestDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RewardsViewModelTest {

    @get:Rule
    val testDispatcher = TestDispatcherRule()

    private lateinit var viewModel: RewardsViewModel

    @Before
    fun setUp() {
        viewModel = RewardsViewModel(
            rewardsRepository = FakeNetworkRewardsRepository()
        )
    }

    @Test
    fun `when repository returns data, verify Success UiState`() = runTest {
        assertEquals(
            RewardsUiState.Success(FakeDataSource.groupedRewards),
            viewModel.rewardsUiState
        )
    }

}
