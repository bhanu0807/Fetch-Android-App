package com.fetch.app.ui.screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.fetch.app.repository.RewardsRepository
import com.fetch.app.model.Reward
import com.fetch.app.ui.screen.RewardsUiState.Error
import com.fetch.app.ui.screen.RewardsUiState.Loading
import com.fetch.app.ui.screen.RewardsUiState.Success
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val TAG = "RewardsViewModel"

sealed interface RewardsUiState {
    data object Loading : RewardsUiState
    data class Error(val throwable: Throwable) : RewardsUiState
    data class Success(val groupedRewards: Map<Int, List<Reward>>) : RewardsUiState
}

@HiltViewModel
class RewardsViewModel @Inject constructor(
    private val rewardsRepository: RewardsRepository
) : ViewModel() {

    var rewardsUiState: RewardsUiState by mutableStateOf(Loading)
        private set

    private var originalRewards: List<Reward> = emptyList() // Stores full rewards list
    private var _filteredRewards: List<Reward> = emptyList() // Stores filtered rewards
    val filteredRewards: List<Reward> get() = _filteredRewards // Public getter

    init {
        getRewards()
    }

    private fun getRewards() {
        viewModelScope.launch {
            rewardsUiState = Loading
            rewardsUiState = try {
                originalRewards = rewardsRepository.getGroupedRewards().values.flatten() // Convert to List
                _filteredRewards = originalRewards // Initially, show all rewards
                Success(rewardsRepository.getGroupedRewards()) // Update UI state
            } catch (e: Exception) {
                Error(e)
            }
        }
    }

    fun onRefresh() = getRewards()

    fun sortList(query: String) {
        _filteredRewards = originalRewards.filter { reward ->
            reward.name?.contains(query, ignoreCase = true) ?: false
        }
        rewardsUiState = Success(_filteredRewards.groupBy { it.listId }) // Update UI state
    }
}
