package ir.miare.androidcodechallenge.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <M : UiModel> BasePage(
    content: @Composable (data: M) -> Unit,
    uiModel: StateFlow<M> ,
    backgroundColor: Color = Color.Unspecified,
    title : String? = null,
) {
    val isBottomNavigationVisible = remember { mutableStateOf(true) }
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource,
            ): Offset {
                if (available.y < 0.1f) {
                    isBottomNavigationVisible.value =
                        consumed.y.toDouble() == 0.0 && available.y.toDouble() >= 0.0
                }
                return super.onPostScroll(consumed, available, source)
            }

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                isBottomNavigationVisible.value = available.y >= 0.1f
                return super.onPreScroll(available, source)
            }
        }
    }
    Scaffold(
        modifier = Modifier
            .nestedScroll(nestedScrollConnection),
        containerColor = backgroundColor,
        topBar = {
            title?.let { txt ->
                TopAppBar(title = {
                    Text(text = txt)
                })
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            content(
                uiModel.collectAsState().value
            )
        }
    }
}
