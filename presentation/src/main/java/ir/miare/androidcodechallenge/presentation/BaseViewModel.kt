package ir.miare.androidcodechallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.miare.androidcodechallenge.domain.base.DataResult
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

open class BaseViewModel<M : UiModel> constructor(private val initUiModel: M) : ViewModel() {

    private val _uiStateFlow: MutableStateFlow<M> by lazy {
        MutableStateFlow(initUiModel)
    }
    val uiStateFlow : StateFlow<M> = _uiStateFlow

    private val _singleEventFlow = MutableSharedFlow<UiEvent>()
    val singleEventFlow : Flow<UiEvent> = _singleEventFlow

        protected var uiModel by Delegates.observable(initUiModel) { _, _, newViewState ->
        viewModelScope.launch {
            _uiStateFlow.emit(newViewState)
        }
    }

    protected fun <R> consume(
        dataResult: Flow<DataResult<R>>,
        onSuccess: ((result: R) -> Unit)? = null,
        onLoadingChanged: ((isLoading: Boolean) -> M)? = null,
        onError: ((error: DataResult<R>) -> Unit)? = null,
        onConsumerCancelled: (() -> Unit)? = null,
    ): Job {
        return viewModelScope.launch {
            dataResult.collect { result ->
                when (result) {
                    is DataResult.Success -> {
                        onLoadingChanged?.invoke(false)?.let { uiModel ->
                            submitUiState(state = uiModel)
                        }
                        onSuccess?.invoke(result.data)
                    }

                    is DataResult.Loading -> {
                        onLoadingChanged?.invoke(true)?.let { uiModel ->
                            submitUiState(state = uiModel)
                        }
                    }

                    else -> {
                        onLoadingChanged?.invoke(false)?.let { uiModel ->
                            submitUiState(uiModel)
                        }
                        onError?.invoke(result)
                    }
                }
            }
        }
    }

    protected fun <R> suspendConsume(
        dataResult: suspend () -> Flow<DataResult<R>>,
        onSuccess: (result: R?) -> Unit,
        onLoadingChanged: ((isLoading: Boolean) -> M)? = null,
        onError: ((error: DataResult<R>) -> Unit)? = null,
        onConsumerCancelled: (() -> Unit)? = null,
    ): Job {
        return viewModelScope.launch {
            dataResult.invoke().collect { result ->
                try {
                    when (result) {
                        is DataResult.Success -> {
                            onSuccess.invoke(result.data)
                        }

                        is DataResult.Loading -> {
                            onLoadingChanged?.invoke(true)?.let { uiModel ->
                                submitUiState(state = uiModel)
                            }
                        }

                        else -> {
                            onLoadingChanged?.invoke(false)?.let { uiModel ->
                                submitUiState(uiModel)
                            }
                            onError?.invoke(result)
                        }
                    }
                } catch (_: CancellationException) {
                    onConsumerCancelled?.invoke()
                }
            }
        }
    }


    protected fun submitUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _singleEventFlow.emit(event)
        }
    }

    protected fun submitUiState(state: M) {
        uiModel = state
    }
}

