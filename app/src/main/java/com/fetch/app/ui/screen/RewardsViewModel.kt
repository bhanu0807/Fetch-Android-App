package com.fetch.app.ui.screen

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

    init {
        getRewards(rewardsRepository)
    }

    private fun getRewards(rewardsRepository: RewardsRepository) {
        viewModelScope.launch {
            rewardsUiState = Loading
            rewardsUiState = try {
                Success(rewardsRepository.getGroupedRewards())
            } catch (e: HttpException) {
                Error(e)
            } catch (e: IOException) {
                Error(e)
            }
        }
    }

    fun onRefresh() = getRewards(rewardsRepository)
}
