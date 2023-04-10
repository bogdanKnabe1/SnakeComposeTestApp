package com.test_task.snakecompose.domain.navigation

import com.test_task.snakecompose.domain.utils.*

sealed class Screen(val route: String) {
    object Start : Screen(route = START_SCREEN)
    object Web : Screen(route = WEB_LINK_ROUTE)
    object Menu : Screen(route = MENU_SCREEN)
    object HighScores : Screen(route = SCORES_SCREEN)
    object SetName : Screen(route = SET_NAME_SCREEN)
    object Empty : Screen(route = EMPTY_SCREEN)
}
