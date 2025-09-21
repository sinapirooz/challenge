package ir.miare.androidcodechallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.miare.androidcodechallenge.presentation.PlayersScreen
import ir.miare.androidcodechallenge.presentation.following.FollowingScreen
import ir.miare.androidcodechallenge.theme.FootbalPlayerTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FootbalPlayerTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.PlayerList,
        BottomNavItem.FollowedPlayers
    )
    Scaffold(
        bottomBar = {
            NavigationBar {
                val  currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentDestination == item.route,
                        onClick = {
                            if (currentDestination != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {},
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.PlayerList.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.PlayerList.route) { PlayersScreen() }
            composable(BottomNavItem.FollowedPlayers.route) { FollowingScreen()}
        }
    }
}

sealed class BottomNavItem(val route: String, val label: String) {
    object PlayerList : BottomNavItem("players", "Players")
    object FollowedPlayers : BottomNavItem("following", "Following")
}
