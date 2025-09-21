package ir.miare.androidcodechallenge.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.flatMap
import ir.miare.androidcodechallenge.domain.model.LeagueWithPlayers
import ir.miare.androidcodechallenge.domain.model.Player
import ir.miare.androidcodechallenge.domain.model.PlayerSortType
import ir.miare.androidcodechallenge.domain.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.domain.base.DataResult
import ir.miare.androidcodechallenge.domain.model.PlayerListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerListViewModel @Inject constructor(private val playerRepository: PlayerRepository) : BaseViewModel<PlayerListUiModel>(PlayerListUiModel()) {

    var likedList by mutableStateOf<List<String>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            playerRepository.getFollowedPlayers().collectLatest { result ->
                if (result is DataResult.Success) {
                    likedList = result.data.map { it.name }
                }
            }
        }
    }
    init {
        onSortTypeChanged(sortType = PlayerSortType.ALL)
    }



    fun onFollowAction(player: Player) {
        viewModelScope.launch {
            playerRepository.followPlayer(player = player)
        }
    }

    fun onSortTypeChanged(sortType: PlayerSortType) {
        submitUiState(state = uiModel.copy(currentSortType = sortType))
        val sort: Flow<PagingData<PlayerListItem>> =
            playerRepository.getPlayers(sortType = sortType)
                .map { pagingData ->
                    pagingData.flatMap { leagueWithPlayers ->
                        getLeagueWithPlayers(leagueWithPlayers)
                    }
                }
                .cachedIn(viewModelScope)
        submitUiState(state = uiModel.copy(sortedList = sort))
    }

    private fun getLeagueWithPlayers(leagueWithPlayers: LeagueWithPlayers): List<PlayerListItem> {
        val items = mutableListOf<PlayerListItem>()

        leagueWithPlayers.league.let { league ->
            items.add(PlayerListItem.LeagueHeader(league))
        }

        leagueWithPlayers.players.let { players ->
            players.forEachIndexed { index, player ->
                val shouldShowDivider = index != players.lastIndex
                items.add(PlayerListItem.PlayerItem(player, shouldShowDivider))
            }
        }

        return items
    }
}