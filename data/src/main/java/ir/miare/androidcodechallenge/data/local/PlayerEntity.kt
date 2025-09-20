package ir.miare.androidcodechallenge.data.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey @ColumnInfo(name = "playerName") val name: String,
    val totalGoals: Int,
    @Embedded val team: TeamEntity
)

data class TeamEntity(
    @ColumnInfo(name = "teamName") val name: String,
    @ColumnInfo(name = "teamRank") val rank: Int
)
