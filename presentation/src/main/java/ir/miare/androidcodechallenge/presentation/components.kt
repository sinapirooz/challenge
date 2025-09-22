package ir.miare.androidcodechallenge.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.miare.androidcodechallenge.domain.model.Player
import ir.miare.androidcodechallenge.domain.model.PlayerSortType

@Composable
fun PlayerPriorityChips(
    modifier: Modifier = Modifier,
    selectedOption: PlayerSortType,
    onOptionSelected: (PlayerSortType) -> Unit
) {
    FlowRow(modifier = modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        PlayerSortType.entries.forEach { option ->
            FilterChip(
                selected = option == selectedOption,
                onClick = { onOptionSelected(option) },
                label = { Text(text = option.name) },
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun PlayerItem (
    modifier: Modifier = Modifier,
    player: Player,
    buttonTitle : String,
    onButtonClicked: (player: Player) -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "${player.name} - Goals(${player.totalGoals})", maxLines = 1)
            Text(text = "${player.team.name} - Rank(${player.team.rank})", maxLines = 1)
        }
        Button(onClick = { onButtonClicked(player) }) {
            Text(text = buttonTitle)
        }
    }
}