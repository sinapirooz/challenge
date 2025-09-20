package ir.miare.androidcodechallenge.data.repositoryImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import ir.miare.androidcodechallenge.data.local.PlayerDao
import ir.miare.androidcodechallenge.data.mapper.mapToDomain
import ir.miare.androidcodechallenge.data.mapper.mapToEntity
import ir.miare.androidcodechallenge.data.remote.api.PlayerApi
import ir.miare.androidcodechallenge.data.remote.pageSource.PlayerPagingSource
import ir.miare.androidcodechallenge.data.remote.pageSource.SortingPlayerSource
import ir.miare.androidcodechallenge.domain.base.DataResult
import ir.miare.androidcodechallenge.domain.model.LeagueWithPlayers
import ir.miare.androidcodechallenge.domain.model.Player
import ir.miare.androidcodechallenge.domain.model.PlayerSortType
import ir.miare.androidcodechallenge.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val api: PlayerApi,
    private val playerDao: PlayerDao,
) : PlayerRepository {

    override fun getPlayers(
        sortType: PlayerSortType
    ): Flow<PagingData<LeagueWithPlayers>> {
        return if (sortType == PlayerSortType.ALL) {
            Pager(
                config = PagingConfig(pageSize = 10),
                pagingSourceFactory = { PlayerPagingSource(api = api) }).flow
        } else {
            Pager(
                config = PagingConfig(pageSize = 5, prefetchDistance = 2),
                pagingSourceFactory = { SortingPlayerSource(api = api, sortType = sortType) }).flow
        }
    }

    override suspend fun followPlayer(player: Player) {
        playerDao.insertPlayer(player = player.mapToEntity())
    }

    override suspend fun unFollowPlayer(playerName : String) {
        playerDao.deletePlayer(playerName = playerName)
    }

    override fun getFollowedPlayers(): Flow<DataResult<List<Player>>> =
        playerDao.getAllPlayers().map { players ->
            DataResult.Success(players.map { it.mapToDomain() })
        }

}