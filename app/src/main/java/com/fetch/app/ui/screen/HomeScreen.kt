package com.fetch.app.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
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
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    var sortAscending by remember { mutableStateOf(true) }
    var searchById by remember { mutableStateOf(false) }
    var filteredRewards by remember { mutableStateOf<Map<Int, List<Reward>>>(emptyMap()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Search by ID or Name") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { })
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Sorting Options
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { sortAscending = !sortAscending }) {
                Text(if (sortAscending) "Sort: Ascending" else "Sort: Descending")
            }
            Button(onClick = { searchById = !searchById }) {
                Text(if (searchById) "Search by ID" else "Search by Name")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Submit Button to Apply Filter
        Button(
            onClick = {
                filteredRewards = viewModel.sortList(searchText.text, sortAscending, searchById)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Handle UI States
        when (val uiState = viewModel.rewardsUiState) {
            is RewardsUiState.Error -> ErrorScreen(
                modifier = modifier.fillMaxSize(),
                onRefresh = { viewModel.onRefresh() }
            )

            is RewardsUiState.Loading -> LoadingScreen(
                modifier = modifier.fillMaxSize()
            )

            is RewardsUiState.Success -> RewardsListScreen(
                groupedRewards = if (filteredRewards.isEmpty()) uiState.groupedRewards else filteredRewards,
                onRefresh = { viewModel.onRefresh() },
                modifier = modifier.fillMaxSize(),
                contentPadding = contentPadding
            )
        }
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
                    RewardItem(reward = reward, modifier = modifier)
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
fun RewardHeader(listId: Int, modifier: Modifier = Modifier) {
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
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                        append("Reward ID: ")
                    }
                    append("${reward.id}")
                },
                Modifier.padding(2.dp)
            )
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                        append("Reward Name: ")
                    }
                    append(reward.name ?: "No Name")
                },
                Modifier.padding(2.dp)
            )
        }
    }
}

fun RewardsViewModel.sortList(query: String, ascending: Boolean, searchById: Boolean): Map<Int, List<Reward>> {
    return if (rewardsUiState is RewardsUiState.Success) {
        val rewards = (rewardsUiState as RewardsUiState.Success).groupedRewards.values.flatten()

        val filteredList = when {
            query.isEmpty() -> rewards // No filtering if query is empty
            searchById -> rewards.filter { it.id.toString().contains(query) }
            else -> rewards.filter { it.name?.contains(query, ignoreCase = true) == true }
        }

        val sortedList = if (ascending) {
            filteredList.sortedWith(compareBy { if (searchById) it.id else it.name ?: "" })
        } else {
            filteredList.sortedWith(compareByDescending { if (searchById) it.id else it.name ?: "" })
        }

        sortedList.groupBy { it.listId }
    } else {
        emptyMap()
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
