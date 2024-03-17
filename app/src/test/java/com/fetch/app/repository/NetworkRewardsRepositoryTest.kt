package com.fetch.app.repository

import com.fetch.app.fake.FakeDataSource
import com.fetch.app.fake.FakeFetchApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NetworkRewardsRepositoryTest {

    private lateinit var rewardsRepository: NetworkRewardsRepository

    @Before
    fun setUp() {
        rewardsRepository = NetworkRewardsRepository(fetchApiService = FakeFetchApiService())
    }

    @Test
    fun `when fetch api returns data, verify filtering, sorting, and grouping`() {
        runTest {
            val actual = rewardsRepository.getGroupedRewards()
            val expected = FakeDataSource.groupedRewards
            assertEquals(expected, actual)
        }
    }
}
