package ir.miare.androidcodechallenge.presentation.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import ir.miare.androidcodechallenge.domain.model.League
import ir.miare.androidcodechallenge.domain.model.PlayerListItem
import ir.miare.androidcodechallenge.presentation.BasePage
import ir.miare.androidcodechallenge.presentation.PlayerItem
import ir.miare.androidcodechallenge.presentation.PlayerPriorityChips

@Composable
fun PlayersScreen(viewModel: PlayerListViewModel = hiltViewModel()) {
    val likedList = viewModel.likedList


    BasePage(content = { data ->
        val pagingItems = data.sortedList.collectAsLazyPagingItems()

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    PlayerPriorityChips(
                        selectedOption = data.currentSortType,
                        onOptionSelected = { viewModel.onSortTypeChanged(sortType = it) }
                    )
                }
                if (pagingItems.loadState.refresh == LoadState.Loading) {
                    item {
                        Box(
                            modifier = Modifier.fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                items(
                    count = pagingItems.itemCount,
                    key = { index ->
                        when (val item = pagingItems[index]) {
                            is PlayerListItem.LeagueHeader -> "league_${item.league.name}"
                            is PlayerListItem.PlayerItem -> "player_${item.player.name}"
                            null -> "loading_$index"
                        }
                    }
                ) { index ->
                    when (val item = pagingItems[index]) {
                        is PlayerListItem.LeagueHeader -> {

                            LeagueHeaderComponent(
                                league = item.league,
                                modifier = Modifier.fillMaxWidth()
                            )

                        }

                        is PlayerListItem.PlayerItem -> {
                            Column {
                                PlayerItem(
                                    player = item.player,
                                    onButtonClicked = { viewModel.onFollowAction(player = it) },
                                    modifier = Modifier.fillMaxWidth(),
                                    buttonTitle = if (likedList.contains(item.player.name)) "Followed" else "Follow",
                                )
                                if (item.shouldShowDivider) {
                                    Divider(
                                        modifier = Modifier.fillMaxWidth(),
                                        thickness = 1.dp,
                                        color = Color.LightGray
                                    )
                                }
                            }
                        }

                        null -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            }
                        }
                    }
                }

                if (pagingItems.loadState.append == LoadState.Loading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }

    }, uiModel = viewModel.uiStateFlow)
}
@Composable
private fun LeagueHeaderComponent(modifier: Modifier = Modifier, league: League) {
    Row(
        modifier = modifier
            .background(color = Color.LightGray)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "${league.name} - Rank(${league.rank})")
    }
}