package com.jetbrains.spacetutorial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jetbrains.spacetutorial.entity.RocketLaunch
import com.jetbrains.spacetutorial.theme.AppTheme
import com.jetbrains.spacetutorial.theme.app_theme_successful
import com.jetbrains.spacetutorial.theme.app_theme_unsuccessful
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    val viewModel = koinViewModel<RocketLaunchViewModel>()
    val state by remember { viewModel.state }
    val coroutineScope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(false) }
    val pullToRefreshState = rememberPullToRefreshState()

    AppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Space Launches",
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                )
            }
        ) { padding ->
            PullToRefreshBox(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                state = pullToRefreshState,
                isRefreshing = isRefreshing,
                onRefresh = {
                    isRefreshing = true
                    coroutineScope.launch {
                        viewModel.loadLaunches()
                        isRefreshing = false
                    }
                }
            ) {
                if (state.isLoading && !isRefreshing) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text("Loading...", style = MaterialTheme.typography.bodyLarge)
                    }
                } else {
                    LazyColumn {
                        items(state.launches) { launch: RocketLaunch ->
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = launch.missionName,
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Text(
                                    text = if (launch.status.id == 3) "Successful" else "Unsuccessful",
                                    color = if (launch.status.id == 3) app_theme_successful else app_theme_unsuccessful
                                )
                                Text(
                                    text = "Launch year: ${launch.launchYear}"
                                )
                                val details = launch.status.description
                                if (details.isNotBlank()) {
                                    Text(details)
                                }
                            }
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}
