package ir.miare.androidcodechallenge.data.mapper

import ir.miare.androidcodechallenge.data.remote.dto.LeagueDto
import ir.miare.androidcodechallenge.data.remote.dto.BaseListResponseDto
import ir.miare.androidcodechallenge.data.remote.dto.LeagueWithPlayersDto
import ir.miare.androidcodechallenge.data.remote.dto.PlayerDto
import ir.miare.androidcodechallenge.data.remote.dto.TeamDto
import ir.miare.androidcodechallenge.domain.base.BaseListResponseDomain
import ir.miare.androidcodechallenge.domain.model.League
import ir.miare.androidcodechallenge.domain.model.LeagueWithPlayers
import ir.miare.androidcodechallenge.domain.model.Player
import ir.miare.androidcodechallenge.domain.model.Team

fun LeagueDto.mapToDomain(): League =
    League(
        name = this.name ?: "",
        country = this.country ?: "",
        rank = this.rank ?: 0,
        totalMatches = this.totalMatches ?: 0
    )

fun TeamDto.mapToDomain(): Team =
    Team(
        name = this.name ?: "",
        rank = this.rank ?: 0
    )

fun PlayerDto.mapToDomain(): Player =
    Player(
        name = this.name ?: "",
        totalGoals = this.totalGoal ?: 0,
        team = this.team?.mapToDomain() ?: Team("", 0)
    )

fun LeagueWithPlayersDto.mapToDomain(): LeagueWithPlayers =
    LeagueWithPlayers(
        league = this.league?.mapToDomain() ?: League("", "", 0, 0),
        players = this.players?.map { it.mapToDomain() } ?: emptyList()
    )

fun BaseListResponseDto<LeagueWithPlayersDto>.mapToDomain(): BaseListResponseDomain<LeagueWithPlayers> =
    BaseListResponseDomain(
        result = result.map { it.mapToDomain() },
        page = page ?: 0, totalPages = totalPages ?: 0, totalResults = totalPages ?: 0
    )