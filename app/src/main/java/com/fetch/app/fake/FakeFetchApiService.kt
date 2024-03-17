package com.fetch.app.fake

import com.fetch.app.model.Reward
import com.fetch.app.service.FetchApiService

class FakeFetchApiService : FetchApiService {
    override suspend fun getRewards(): List<Reward> = FakeDataSource.rewards
}