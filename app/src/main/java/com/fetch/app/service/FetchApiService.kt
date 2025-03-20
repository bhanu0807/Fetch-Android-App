package com.fetch.app.service

import com.fetch.app.model.Reward
import retrofit2.http.GET
    
interface FetchApiService {

    @GET("hiring.json")
    suspend fun getRewards(): List<Reward>

}
