package com.alphadevdigital.movieapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alphadevdigital.movieapp.android.ui.MainViewModel
import com.alphadevdigital.movieapp.android.ui.detailview_screen.DetailScreen
import com.alphadevdigital.movieapp.android.ui.explore_screen.ExploreScreen
import com.alphadevdigital.movieapp.android.ui.home_screen.HomeScreen
import com.alphadevdigital.movieapp.android.ui.theme.MyApplicationTheme
import com.alphadevdigital.movieapp.android.ui.video_screen.PlaylistScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme(darkTheme = true) {
                val navController = rememberNavController()
                val mainViewModel: MainViewModel = viewModel()

                Scaffold(
                    bottomBar = {
                        BottomNavigation(
                            backgroundColor = MaterialTheme.colors.primaryVariant,
                        ) {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            items.forEach { screen ->
                                BottomNavigationItem(
                                    icon = {
                                        Icon(
                                            screen.icon!!,
                                            contentDescription = screen.name,
                                            tint = Color.White
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = screen.name,
                                            color = Color.White,
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                    },
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.HomeRoute.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Screen.HomeRoute.route) {
                            HomeScreen(navController = navController, mainViewModel = mainViewModel)
                        }

                        composable(route = Screen.ExploreRoute.route) {
                            ExploreScreen(
                                navController = navController,
                                mainViewModel = mainViewModel
                            )
                        }

                        composable(route = Screen.PlaylistRoute.route) {
                            PlaylistScreen(navController = navController)
                        }
                        composable(
                            route = Screen.DetailRoute.route,
                        ) {
                            DetailScreen(
                                viewModel = mainViewModel
                            )
                        }
                    }
                }
            }
        }
    }

    sealed class Screen(val name: String, val route: String, val icon: ImageVector?) {
        object HomeRoute : Screen("Home", "home", Icons.Filled.Home)
        object ExploreRoute : Screen("Explore", "explore", Icons.Filled.Search)
        object PlaylistRoute : Screen("Playlist", "playlist", Icons.Rounded.PlayArrow)
        object DetailRoute : Screen("Movie", "detail", null)
    }

    private val items = listOf(
        Screen.HomeRoute,
        Screen.ExploreRoute,
        Screen.PlaylistRoute
    )

}