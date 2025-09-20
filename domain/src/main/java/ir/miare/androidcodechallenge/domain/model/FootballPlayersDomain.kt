package ir.miare.androidcodechallenge.domain.model
data class League(
    val name: String,
    val country: String,
    val rank: Int,
    val totalMatches: Int
)

data class Team(
    val name: String,
    val rank: Int
)

data class Player(
    val name: String,
    val totalGoals: Int,
    val team: Team
)

data class LeagueWithPlayers(
    val league: League,
    val players: List<Player>
)
