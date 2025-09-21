package ir.miare.androidcodechallenge.data

import ir.miare.androidcodechallenge.data.mapper.mapToDomain
import ir.miare.androidcodechallenge.data.remote.dto.LeagueDto
import ir.miare.androidcodechallenge.data.remote.dto.LeagueWithPlayersDto
import ir.miare.androidcodechallenge.data.remote.dto.PlayerDto
import ir.miare.androidcodechallenge.data.remote.dto.TeamDto
import ir.miare.androidcodechallenge.domain.model.League
import ir.miare.androidcodechallenge.domain.model.LeagueWithPlayers
import ir.miare.androidcodechallenge.domain.model.Player
import ir.miare.androidcodechallenge.domain.model.Team
import org.junit.Assert.assertEquals
import org.junit.Test

class MapToDomainTest {

    @Test
    fun `LeagueDto maps to League correctly`() {
        val dto = LeagueDto(name = "Bundesliga", country = "Germany", rank = 2, totalMatches = 34)
        val expected = League(name = "Bundesliga", country = "Germany", rank = 2, totalMatches = 34)
        assertEquals(expected, dto.mapToDomain())
    }

    @Test
    fun `TeamDto maps to Team correctly`() {
        val dto = TeamDto(name = "Bayern", rank = 1)
        val expected = Team(name = "Bayern", rank = 1)
        assertEquals(expected, dto.mapToDomain())
    }

    @Test
    fun `PlayerDto maps to Player correctly`() {
        val dto = PlayerDto(
            name = "Musiala",
            totalGoal = 10,
            team = TeamDto(name = "Bayern", rank = 1)
        )
        val expected = Player(
            name = "Musiala",
            totalGoals = 10,
            team = Team(name = "Bayern", rank = 1)
        )
        assertEquals(expected, dto.mapToDomain())
    }

    @Test
    fun `LeagueWithPlayersDto maps to LeagueWithPlayers correctly`() {
        val dto = LeagueWithPlayersDto(
            league = LeagueDto(name = "Premier League", country = "England", rank = 1, totalMatches = 38),
            players = listOf(
                PlayerDto(name = "Saka", totalGoal = 15, team = TeamDto(name = "Arsenal", rank = 1)),
                PlayerDto(name = "Salah", totalGoal = 29, team = TeamDto(name = "Liverpool", rank = 2))
            )
        )
        val expected = LeagueWithPlayers(
            league = League(name = "Premier League", country = "England", rank = 1, totalMatches = 38),
            players = listOf(
                Player(name = "Saka", totalGoals = 15, team = Team(name = "Arsenal", rank = 1)),
                Player(name = "Salah", totalGoals = 29, team = Team(name = "Liverpool", rank = 2))
            )
        )
        assertEquals(expected, dto.mapToDomain())
    }

    @Test
    fun `mapToDomain handles nulls and empty lists`() {
        val playerDto = PlayerDto(name = null, totalGoal = null, team = null)
        val player = playerDto.mapToDomain()
        assertEquals("", player.name)
        assertEquals(0, player.totalGoals)
        assertEquals("", player.team.name)
        assertEquals(0, player.team.rank)

        val leagueWithPlayersDto = LeagueWithPlayersDto(league = null, players = null)
        val lwPlayers = leagueWithPlayersDto.mapToDomain()
        assertEquals("", lwPlayers.league.name)
        assertEquals("", lwPlayers.league.country)
        assertEquals(0, lwPlayers.league.rank)
        assertEquals(0, lwPlayers.league.totalMatches)
        assertEquals(emptyList<Player>(), lwPlayers.players)
    }
}
