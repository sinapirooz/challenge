package ir.miare.androidcodechallenge.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import ir.miare.androidcodechallenge.data.local.PlayerDao
import ir.miare.androidcodechallenge.data.remote.api.PlayerApi
import ir.miare.androidcodechallenge.data.remote.dto.BaseListResponseDto
import ir.miare.androidcodechallenge.data.remote.dto.LeagueWithPlayersDto
import ir.miare.androidcodechallenge.data.repositoryImpl.PlayerRepositoryImpl
import ir.miare.androidcodechallenge.domain.base.DataResult
import ir.miare.androidcodechallenge.domain.model.Player
import ir.miare.androidcodechallenge.domain.model.Team
import ir.miare.androidcodechallenge.domain.repository.PlayerRepository
import ir.miare.androidcodechallenge.data.local.PlayerDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class FollowUnFollowTest {


    private lateinit var db: PlayerDatabase
    private lateinit var dao: PlayerDao
    private lateinit var repository: PlayerRepository

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PlayerDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.playerDao()
        val fakeApi : PlayerApi = object : PlayerApi{
            override suspend fun fetchPlayers(page: Int): Response<BaseListResponseDto<LeagueWithPlayersDto>> {
                TODO("Not yet implemented")
            }
        }
        repository = PlayerRepositoryImpl(api = fakeApi, playerDao = dao)
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun followPlayer_insertsToDb_and_getFollowedPlayers_returnsIt() = runTest {
        val player = Player(
            name = "Robert Lewandowski",
            totalGoals = 15,
            team = Team(name = "Barcelona", rank = 1)
        )

        repository.followPlayer(player)
        val result = repository.getFollowedPlayers().first()
        assertTrue(result is DataResult.Success)
        val playerList = (result as DataResult.Success).data
        assertTrue(playerList.any { it.name == "Robert Lewandowski" && it.team.name == "Barcelona" })
    }

    @Test
    fun unFollowPlayer_removesFromDb() = runTest {
        val player = Player(
            name = "Saka",
            totalGoals = 15,
            team = Team(name = "Arsenal", rank = 1)
        )
        repository.followPlayer(player)
        repository.unFollowPlayer(player.name)
        val result = repository.getFollowedPlayers().first()
        assertTrue(result is DataResult.Success)
        val playerList = (result as DataResult.Success).data
        assertTrue(playerList.none { it.name == "Saka" })
    }
}
