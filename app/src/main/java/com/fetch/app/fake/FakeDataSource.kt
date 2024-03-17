package com.fetch.app.fake

import com.fetch.app.model.Reward

object FakeDataSource {

    private val TEST_1 = Reward(id = 1, listId = 1, name = "test1")
    private val TEST_2 = Reward(id = 2, listId = 1, name = "test2")
    private val TEST_3 = Reward(id = 3, listId = 1, name = "")
    private val TEST_4 = Reward(id = 4, listId = 2, name = "test4")
    private val TEST_5 = Reward(id = 5, listId = 2, name = null)

    val rewards = listOf(TEST_1, TEST_2, TEST_3, TEST_4, TEST_5)

    val groupedRewards = mapOf(
        Pair(
            1, listOf(TEST_1, TEST_2)
        ),
        Pair(
            2, listOf(TEST_4)
        )
    )
}