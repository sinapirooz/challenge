package ir.miare.androidcodechallenge.data.mapper

import ir.miare.androidcodechallenge.data.local.PlayerEntity
import ir.miare.androidcodechallenge.data.local.TeamEntity
import ir.miare.androidcodechallenge.domain.model.Player
import ir.miare.androidcodechallenge.domain.model.Team

fun Player.mapToEntity() = PlayerEntity(
    name = name,
    totalGoals = totalGoals,
    team = team.mapToEntity()
)

fun Team.mapToEntity() = TeamEntity(
    name = name,
    rank = rank
)

fun PlayerEntity.mapToDomain() = Player(
    name = name,
    totalGoals = totalGoals,
    team = team.mapToDomain()
)

fun TeamEntity.mapToDomain() = Team(
    name = name,
    rank = rank
)

