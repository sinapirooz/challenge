package ir.miare.androidcodechallenge.presentation.following

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.miare.androidcodechallenge.domain.repository.PlayerRepository
import ir.miare.androidcodechallenge.presentation.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(private val playerRepository: PlayerRepository) :
    BaseViewModel<FollowingUiModel>(
        FollowingUiModel()
    ) {

    init {
        consume(dataResult = playerRepository.getFollowedPlayers(), onSuccess = {
            submitUiState(state = uiModel.copy(followedPlayers = it))
        })
    }

    fun onUnFollowPlayerAction(playerName : String) {
       viewModelScope.launch {
           playerRepository.unFollowPlayer(playerName = playerName)
       }
    }

}