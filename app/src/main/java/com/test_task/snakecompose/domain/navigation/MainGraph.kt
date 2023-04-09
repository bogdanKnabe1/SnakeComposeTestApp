package com.test_task.snakecompose.domain.navigation

import com.test_task.snakecompose.domain.utils.WEB_LINK_ROUTE

sealed class Screen(val route: String) {
    object Start : Screen(route = "start_screen")
    object Web : Screen(route = WEB_LINK_ROUTE)
    object Menu : Screen(route = "menu_screen")
    object HighScores : Screen(route = "scores_screen")
    object SetName : Screen(route = "set_name_screen")
    object Empty : Screen(route = "empty_screen")
}
