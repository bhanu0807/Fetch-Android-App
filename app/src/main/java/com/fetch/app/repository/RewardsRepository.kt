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
            .filter { reward: Reward -> !reward.name.isNullOrBlank() }
            .sortedWith(
                compareBy(
                    { reward: Reward -> reward.listId },
                    { reward: Reward -> reward.name })
                )
            .groupBy { reward: Reward -> reward.listId }
}
