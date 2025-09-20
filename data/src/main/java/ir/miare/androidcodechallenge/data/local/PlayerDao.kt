package ir.miare.androidcodechallenge.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: PlayerEntity)

    @Query("SELECT * FROM players")
    fun getAllPlayers(): Flow<List<PlayerEntity>>


    @Query("DELETE FROM players WHERE playerName = :playerName")
    suspend fun deletePlayer(playerName : String)

}
