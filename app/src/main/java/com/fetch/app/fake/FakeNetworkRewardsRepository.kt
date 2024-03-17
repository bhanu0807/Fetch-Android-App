package com.fetch.app.fake

import com.fetch.app.model.Reward
import com.fetch.app.repository.RewardsRepository

class FakeNetworkRewardsRepository : RewardsRepository {
    override suspend fun getGroupedRewards(): Map<Int, List<Reward>> = FakeDataSource.groupedRewards
}