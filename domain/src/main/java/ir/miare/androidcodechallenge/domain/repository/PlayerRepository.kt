package ir.miare.androidcodechallenge.domain.repository

import androidx.paging.PagingData
import ir.miare.androidcodechallenge.domain.base.DataResult
import ir.miare.androidcodechallenge.domain.model.LeagueWithPlayers
import ir.miare.androidcodechallenge.domain.model.Player
import ir.miare.androidcodechallenge.domain.model.PlayerSortType
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    fun getPlayers(sortType : PlayerSortType): Flow<PagingData<LeagueWithPlayers>>

    suspend fun followPlayer(player: Player)

    suspend fun unFollowPlayer(playerName : String)

    fun getFollowedPlayers(): Flow<DataResult<List<Player>>>
}