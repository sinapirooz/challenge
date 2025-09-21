package ir.miare.androidcodechallenge.data.remote

import androidx.paging.PagingSource
import ir.miare.androidcodechallenge.data.remote.api.PlayerApi
import ir.miare.androidcodechallenge.data.remote.dto.BaseListResponseDto
import ir.miare.androidcodechallenge.data.remote.dto.LeagueDto
import ir.miare.androidcodechallenge.data.remote.dto.LeagueWithPlayersDto
import ir.miare.androidcodechallenge.data.remote.dto.PlayerDto
import ir.miare.androidcodechallenge.data.remote.dto.TeamDto
import ir.miare.androidcodechallenge.data.remote.pageSource.SortingPlayerSource
import ir.miare.androidcodechallenge.domain.model.PlayerSortType
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class SortingPlayerSourceTest {

    private val fakeFootballApi: PlayerApi = mock()

    private fun provideApiResponse(): BaseListResponseDto<LeagueWithPlayersDto> {
        return BaseListResponseDto(
            result = listOf(
                LeagueWithPlayersDto(
                    league = LeagueDto(name = "Premier League", country = "England", rank = 1, totalMatches = 38),
                    players = listOf(
                        PlayerDto(
                            name = "Saka",
                            totalGoal = 15,
                            team = TeamDto(name = "Arsenal", rank = 1)
                        ),
                        PlayerDto(name = "Salah", totalGoal = 29, team = TeamDto(name = "Liverpool", rank = 2))
                    )
                ),
                LeagueWithPlayersDto(
                    league = LeagueDto(name = "Bundesliga", "Germany", rank = 2, totalMatches =  34),
                    players = listOf(
                        PlayerDto(name = "Musiala", totalGoal =  10, team =  TeamDto(name = "Bayern", rank = 1)),
                        PlayerDto(name = "Nkunku", totalGoal =  18, team =  TeamDto(name = "Leipzig", rank =  2))
                    )
                )
            ),
            totalPages = 1, page = 1
        )
    }

    @Test
    fun `TEAM_LEAGUE_RANK sorts leagues and players by rank`() = runTest {
        whenever(fakeFootballApi.fetchPlayers(0)).thenReturn(Response.success(provideApiResponse()))
        val source = SortingPlayerSource(fakeFootballApi, PlayerSortType.TEAM_LEAGUE_RANK)

        val params = PagingSource.LoadParams.Refresh(
            key = 1,
            loadSize = 5,
            placeholdersEnabled = false
        )
        val result = source.load(params) as PagingSource.LoadResult.Page

        val firstLeague = result.data[0]
        assertEquals("Premier League", firstLeague.league.name)
        assertEquals("Arsenal", firstLeague.players[0].team.name)
        assertEquals("Liverpool", firstLeague.players[1].team.name)

        val secondLeague = result.data[1]
        assertEquals("Bundesliga", secondLeague.league.name)
        assertEquals("Bayern", secondLeague.players[0].team.name)
        assertEquals("Leipzig", secondLeague.players[1].team.name)
    }

    @Test
    fun `MOST_GOALS sorts players in each league by total goals descending`() = runTest {
        whenever(fakeFootballApi.fetchPlayers(0)).thenReturn(Response.success(provideApiResponse()))
        val source = SortingPlayerSource(fakeFootballApi, PlayerSortType.MOST_GOALS)

        val params = PagingSource.LoadParams.Refresh(
            key = 1,
            loadSize = 5,
            placeholdersEnabled = false
        )
        val result = source.load(params) as PagingSource.LoadResult.Page

        val firstLeague = result.data[0]
        assertEquals("Premier League", firstLeague.league.name)
        assertEquals("Salah", firstLeague.players[0].name)
        assertEquals("Saka", firstLeague.players[1].name)

        val secondLeague = result.data[1]
        assertEquals("Bundesliga", secondLeague.league.name)
        assertEquals("Nkunku", secondLeague.players[0].name)
        assertEquals("Musiala", secondLeague.players[1].name)
    }

    @Test
    fun `AVERAGE_GOALS sorts leagues by average player goals descending`() = runTest {
        whenever(fakeFootballApi.fetchPlayers(0)).thenReturn(Response.success(provideApiResponse()))
        val source = SortingPlayerSource(fakeFootballApi, PlayerSortType.AVG_GOALS_LEAGUE)

        val params = PagingSource.LoadParams.Refresh(
            key = 1, loadSize = 5, placeholdersEnabled = false
        )
        val result = source.load(params) as PagingSource.LoadResult.Page

        val leagues = result.data
        assertEquals("Premier League", leagues[0].league.name)
        assertEquals("Bundesliga", leagues[1].league.name)
    }

}
