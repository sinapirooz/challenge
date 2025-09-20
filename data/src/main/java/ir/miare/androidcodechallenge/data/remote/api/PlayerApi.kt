package ir.miare.androidcodechallenge.data.remote.api

import ir.miare.androidcodechallenge.data.remote.dto.BaseListResponseDto
import ir.miare.androidcodechallenge.data.remote.dto.LeagueWithPlayersDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlayerApi {
    @GET("players")
    suspend fun fetchPlayers(@Query("page") page: Int): Response<BaseListResponseDto<LeagueWithPlayersDto>>
}