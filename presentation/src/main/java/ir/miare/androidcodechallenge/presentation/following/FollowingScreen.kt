package ir.miare.androidcodechallenge.presentation.following

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ir.miare.androidcodechallenge.presentation.BasePage
import ir.miare.androidcodechallenge.presentation.PlayerItem

@Composable
fun FollowingScreen(viewModel: FollowingViewModel = hiltViewModel()) {
    BasePage(content = { data ->
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            if (data.followedPlayers.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillParentMaxSize()
                            .padding(horizontal = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No Player Found")
                    }
                }
            } else {
                itemsIndexed(data.followedPlayers) { index, player ->
                    PlayerItem(
                        modifier = Modifier.fillMaxWidth(),
                        onButtonClicked = {
                            viewModel.onUnFollowPlayerAction(playerName = it.name)
                        }, player = player, buttonTitle = "unfollow"
                    )
                    if (index != data.followedPlayers.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = Color.LightGray
                        )
                    }
                }

            }
        }
    }, uiModel = viewModel.uiStateFlow)
}