package ir.miare.androidcodechallenge.data.remote.pageSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ir.miare.androidcodechallenge.data.mapper.mapToDomain
import ir.miare.androidcodechallenge.data.remote.api.PlayerApi
import ir.miare.androidcodechallenge.domain.model.LeagueWithPlayers

class PlayerPagingSource(
    private val api: PlayerApi
) : PagingSource<Int, LeagueWithPlayers>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LeagueWithPlayers> {
        val page = params.key ?: 1
        return try {
            val response = api.fetchPlayers(page = page)
            if (response.isSuccessful) {
                val body = response.body()
                val totalPages = body?.totalPages ?: page
                LoadResult.Page(
                    data = body!!.result.map { it.mapToDomain() },
                    prevKey = null,
                    nextKey = if (page < totalPages) page.plus(1) else null
                )
            } else {
                LoadResult.Error(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LeagueWithPlayers>): Int? = state.anchorPosition
}

