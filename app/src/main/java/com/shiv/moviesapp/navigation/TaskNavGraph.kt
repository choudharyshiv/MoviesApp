package com.shiv.moviesapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.shiv.moviesapp.ui.bookmarks.BookmarksScreen
import com.shiv.moviesapp.ui.detail.DetailScreen
import com.shiv.moviesapp.ui.home.HomeScreen
import com.shiv.moviesapp.ui.search.SearchScreen

@Composable
fun MovieNavGraph(navController: NavHostController) {

    val bottomNavItems = listOf(
        Triple(Routes.Home, Icons.Filled.Home, "Home"),
        Triple(Routes.Search, Icons.Filled.Search, "Search"),
        Triple(Routes.Bookmarks, Icons.Filled.Bookmark, "Bookmarks")
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBottomBar = currentDestination?.route != Routes.Detail.route &&
            !currentDestination?.route.orEmpty().startsWith("detail/")

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { (route, icon, label) ->
                        NavigationBarItem(
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label) },
                            selected = currentDestination?.hierarchy?.any { it.route == route.route } == true,
                            onClick = {
                                navController.navigate(route.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.Home.route) {
                HomeScreen(onMovieClick = { movieId ->
                    navController.navigate(Routes.Detail.createRoute(movieId))
                })
            }
            composable(Routes.Search.route) {
                SearchScreen(onMovieClick = { movieId ->
                    navController.navigate(Routes.Detail.createRoute(movieId))
                })
            }
            composable(Routes.Bookmarks.route) {
                BookmarksScreen(onMovieClick = { movieId ->
                    navController.navigate(Routes.Detail.createRoute(movieId))
                })
            }
            composable(
                route = Routes.Detail.route,
                arguments = listOf(navArgument("movieId") { type = NavType.IntType }),
                deepLinks = listOf(navDeepLink { uriPattern = "moviesapp://detail/{movieId}" })
            ) {
                DetailScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
