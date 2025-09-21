package ir.miare.androidcodechallenge.data.remote

import androidx.paging.PagingSource
import ir.miare.androidcodechallenge.data.mapper.mapToDomain
import ir.miare.androidcodechallenge.data.remote.api.PlayerApi
import ir.miare.androidcodechallenge.data.remote.dto.BaseListResponseDto
import ir.miare.androidcodechallenge.data.remote.dto.LeagueDto
import ir.miare.androidcodechallenge.data.remote.dto.LeagueWithPlayersDto
import ir.miare.androidcodechallenge.data.remote.dto.PlayerDto
import ir.miare.androidcodechallenge.data.remote.dto.TeamDto
import ir.miare.androidcodechallenge.data.remote.pageSource.PlayerPagingSource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class PlayerPagingSourceTest {

    private val fakeFootballApi: PlayerApi = mock()

    private fun createFakeApiResponse(): BaseListResponseDto<LeagueWithPlayersDto> {
        return BaseListResponseDto(
            result = listOf(
                LeagueWithPlayersDto(
                    league = LeagueDto(name = "Premier League", country = "England", rank = 1, totalMatches = 38),
                    players = listOf(
                        PlayerDto(name = "Saka", totalGoal = 15, team = TeamDto(name = "Arsenal", rank = 1)),
                        PlayerDto(name = "Salah", totalGoal = 29, team = TeamDto(name = "Liverpool", rank = 2))
                    )
                )
            ),
            totalPages = 3,
            page = 1
        )
    }

    @Test
    fun `load returns page with mapped data and correct nextKey`() = runTest {
        val page = 1
        val fakeResponse = createFakeApiResponse()
        whenever(fakeFootballApi.fetchPlayers(page)).thenReturn(Response.success(fakeResponse))
        val pagingSource = PlayerPagingSource(fakeFootballApi)

        val params = PagingSource.LoadParams.Refresh(
            key = page,
            loadSize = 10,
            placeholdersEnabled = false
        )
        val result = pagingSource.load(params)

        val expectedLeagues = fakeResponse.result.map { it.mapToDomain() }
        val expected = PagingSource.LoadResult.Page(
            data = expectedLeagues,
            prevKey = null,
            nextKey = 2
        )

        assert(result is PagingSource.LoadResult.Page)
        val pageResult = result as PagingSource.LoadResult.Page
        assertEquals(expected.data, pageResult.data)
        assertEquals(expected.nextKey, pageResult.nextKey)
        assertEquals(expected.prevKey, pageResult.prevKey)
    }

    @Test
    fun `load returns error when api fails`() = runTest {
        whenever(fakeFootballApi.fetchPlayers(1)).thenReturn(Response.error(500, okhttp3.ResponseBody.create(null, "")))
        val pagingSource = PlayerPagingSource(fakeFootballApi)

        val params = PagingSource.LoadParams.Refresh<Int>(
            key = 1, loadSize = 10, placeholdersEnabled = false
        )

        val result = pagingSource.load(params)

        assert(result is PagingSource.LoadResult.Error)
    }
}
