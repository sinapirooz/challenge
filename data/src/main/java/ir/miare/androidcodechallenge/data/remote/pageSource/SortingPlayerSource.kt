package ir.miare.androidcodechallenge.data.remote.pageSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ir.miare.androidcodechallenge.data.mapper.mapToDomain
import ir.miare.androidcodechallenge.data.remote.api.PlayerApi
import ir.miare.androidcodechallenge.domain.model.LeagueWithPlayers
import ir.miare.androidcodechallenge.domain.model.PlayerSortType

class SortingPlayerSource(
    private val api: PlayerApi,
    private val sortType: PlayerSortType
) : PagingSource<Int, LeagueWithPlayers>() {

    override fun getRefreshKey(state: PagingState<Int, LeagueWithPlayers>): Int? = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LeagueWithPlayers> {
        return try {
            val response = api.fetchPlayers(page = 0)
            if (response.isSuccessful) {
                val body = response.body()
                LoadResult.Page(
                    data = sortLeaguesWithPlayers(
                        list = body!!.result.map { it.mapToDomain() },
                        sortType = sortType
                    ),
                    prevKey = null,
                    nextKey = null
                )
            } else {
                LoadResult.Error(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun sortLeaguesWithPlayers(
        list: List<LeagueWithPlayers>,
        sortType: PlayerSortType
    ): List<LeagueWithPlayers> {
        return when (sortType) {
            PlayerSortType.TEAM_LEAGUE_RANK -> {
                list.sortedBy { it.league.rank }
                    .map { leagueWithPlayers ->
                        leagueWithPlayers.copy(
                            players = leagueWithPlayers.players.sortedBy { it.team.rank }
                        )
                    }
            }


        PlayerSortType.MOST_GOALS -> {
            list.map { leagueWithPlayers ->
                leagueWithPlayers.copy(
                    players = leagueWithPlayers.players.sortedByDescending { it.totalGoals }
                )
            }
        }

            else -> {
                list.sortedByDescending { leagueWithPlayers ->
                    val players = leagueWithPlayers.players
                    if (players.isNotEmpty())
                        players.map { it.totalGoals }.average()
                    else
                        0.0
                }

            }
        }
    }
}