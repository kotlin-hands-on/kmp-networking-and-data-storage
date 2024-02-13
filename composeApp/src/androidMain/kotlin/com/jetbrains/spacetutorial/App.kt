package com.jetbrains.spacetutorial

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jetbrains.spacetutorial.theme.AppTheme
import com.jetbrains.spacetutorial.theme.app_theme_successful
import com.jetbrains.spacetutorial.theme.app_theme_unsuccessful
import org.koin.androidx.compose.koinViewModel

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun App() {
    val viewModel = koinViewModel<AppViewModel>()
    val state by viewModel.state
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = { viewModel.loadLaunches() }
    )
    AppTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(
                        "SpaceX Launches",
                        style = MaterialTheme.typography.headlineLarge
                    )
                })
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .pullRefresh(pullRefreshState)
            ) {
                LazyColumn {
                    items(state.launches) {
                        Column(modifier = Modifier.padding(all = 16.dp)) {
                            Text(
                                modifier = Modifier.padding(bottom = 8.dp),
                                text = "${it.missionName} - ${it.launchYear}",
                                style = MaterialTheme.typography.headlineSmall
                            )
                            if (it.launchSuccess == true) {
                                Text(
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    text = "Successful",
                                    color = app_theme_successful
                                )
                            } else {
                                Text(
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    text = "Unsuccessful",
                                    color = app_theme_unsuccessful
                                )
                            }
                            Text(
                                text = it.details ?: ""
                            )
                        }
                        HorizontalDivider()
                    }
                }

                PullRefreshIndicator(
                    refreshing = state.isLoading,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    backgroundColor = if (state.isLoading) Color.LightGray else Color.White,
                )
            }
        }
    }
}