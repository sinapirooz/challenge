package ir.miare.androidcodechallenge.presentation.following


import ir.miare.androidcodechallenge.domain.model.Player
import ir.miare.androidcodechallenge.presentation.UiModel

data class FollowingUiModel(
    val followedPlayers: List<Player> = emptyList()
) : UiModel
