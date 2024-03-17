package com.fetch.app.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fetch.app.R
import com.fetch.app.model.Reward
import com.fetch.app.ui.theme.FetchTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: RewardsViewModel = hiltViewModel(),
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    when (val uiState = viewModel.rewardsUiState) {
        is RewardsUiState.Error -> ErrorScreen(
            modifier = modifier.fillMaxSize(),
            onRefresh = { viewModel.onRefresh() }
        )

        is RewardsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is RewardsUiState.Success -> RewardsListScreen(
            uiState.groupedRewards,
            onRefresh = { viewModel.onRefresh() },
            modifier = modifier.fillMaxSize(),
            contentPadding = contentPadding
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(72.dp)
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier, onRefresh: () -> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = stringResource(R.string.connection_error),
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(
            onClick = onRefresh,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text(text = stringResource(R.string.refresh))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RewardsListScreen(
    groupedRewards: Map<Int, List<Reward>>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    onRefresh: () -> Unit,
) {
    Box {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = contentPadding
        ) {
            groupedRewards.forEach { (listId, rewards) ->
                stickyHeader {
                    RewardHeader(listId = listId)
                }
                items(rewards, key = Reward::id) { reward ->
                    RewardItem(
                        reward = reward,
                        modifier = modifier,
                    )
                }
            }
        }
        ExtendedFloatingActionButton(
            onClick = onRefresh,
            icon = {
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = stringResource(R.string.refresh)
                )
            },
            text = {
                Text(text = stringResource(R.string.refresh))
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
    }
}

@Composable
fun RewardHeader(
    listId: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = "${stringResource(R.string.reward_list)} $listId",
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    )
}


@Composable
fun RewardItem(reward: Reward, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Medium)) {
                            append(stringResource(R.string.reward_id))
                        }
                        append("       ")
                        append(" : ")
                        append("${reward.id}")
                    },
                    Modifier.padding(2.dp)
                )
                Text(
                    buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Medium)) {
                            append(stringResource(R.string.reward_name))
                        }
                        append(" : ")
                        append("${reward.name}")
                    },
                    Modifier.padding(2.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    FetchTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    FetchTheme {
        ErrorScreen(onRefresh = {})
    }
}

@Preview(showBackground = true)
@Composable
fun RewardsListScreenPreview() {
    FetchTheme {
        val mockData = mapOf(
            Pair(
                1, listOf(Reward(id = 1, listId = 1, name = "mock1"))
            ),
            Pair(
                2, listOf(Reward(id = 2, listId = 1, name = "mock2"))
            )
        )
        RewardsListScreen(mockData, contentPadding = PaddingValues(0.dp)) { }
    }
}
