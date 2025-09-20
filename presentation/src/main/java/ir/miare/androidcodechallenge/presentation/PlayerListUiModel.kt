package ir.miare.androidcodechallenge.presentation

import androidx.paging.PagingData
import ir.miare.androidcodechallenge.domain.model.PlayerListItem
import ir.miare.androidcodechallenge.domain.model.PlayerSortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class PlayerListUiModel(
    val currentSortType: PlayerSortType = PlayerSortType.ALL,
    val sortedList: Flow<PagingData<PlayerListItem>> = emptyFlow()
): UiModel