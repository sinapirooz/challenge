package ir.miare.androidcodechallenge.domain.model

sealed class PlayerListItem {
    data class LeagueHeader(val league: League) : PlayerListItem()
    data class PlayerItem(val player: Player, val shouldShowDivider: Boolean) : PlayerListItem()
}