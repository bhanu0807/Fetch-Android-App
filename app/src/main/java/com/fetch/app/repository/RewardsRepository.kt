package com.fetch.app.repository

import com.fetch.app.model.Reward
import com.fetch.app.service.FetchApiService
import javax.inject.Inject

interface RewardsRepository {
    suspend fun getGroupedRewards(): Map<Int, List<Reward>>
}

class NetworkRewardsRepository @Inject constructor(
    private val fetchApiService: FetchApiService
) : RewardsRepository {
    override suspend fun getGroupedRewards(): Map<Int, List<Reward>> =
        fetchApiService.getRewards()
            // Remove `null` and blank named rewards
            .filter { reward: Reward -> !reward.name.isNullOrBlank() }
            // Sort first by `listId` and then by `name`
            .sortedWith(
                compareBy(
                    { reward: Reward -> reward.listId },
                    { reward: Reward -> reward.name })
                )
            // Group rewards by `listId`
            .groupBy { reward: Reward -> reward.listId }
}
