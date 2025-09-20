package ir.miare.androidcodechallenge.data.remote.dto

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LeagueDto(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("rank")
    val rank: Int? = null,
    @SerializedName("total_matches")
    val totalMatches: Int? = null
)

@Keep
data class TeamDto(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("rank")
    val rank: Int? = null
)

@Keep
data class PlayerDto(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("total_goal")
    val totalGoal: Int? = null,
    @SerializedName("team")
    val team: TeamDto? = null
)

@Keep
data class LeagueWithPlayersDto(
    @SerializedName("league")
    val league: LeagueDto? = null,
    @SerializedName("players")
    val players: List<PlayerDto>? = null
)
