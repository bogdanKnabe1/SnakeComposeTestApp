package com.test_task.snakecompose.presentation.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.test_task.snakecompose.domain.navigation.Screen
import com.test_task.snakecompose.domain.utils.DEFAULT_LINK
import com.test_task.snakecompose.domain.utils.SetStatusBarTransparent
import com.test_task.snakecompose.domain.utils.web_link_key
import com.test_task.snakecompose.presentation.screens.*
import com.test_task.snakecompose.presentation.theme.SnakeTheme

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnakeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetStatusBarTransparent(color = MaterialTheme.colorScheme.primaryContainer)
                    SetUpNavigation()
                }
            }
        }
    }

    @Composable
    fun SetUpNavigation() {
        navController = rememberNavController()
        SetupNavigation()
    }

    @Composable
    private fun SetupNavigation() {
        NavHost(navController = navController, startDestination = Screen.Start.route) {
            composable(Screen.Start.route) {
                StartScreen(
                    onGamePassReceived = {
                        // If we have game_pass flag, load game
                        navController.navigate(Screen.Menu.route) {
                            popUpTo(Screen.Start.route) { inclusive = true }
                        }
                    }, onWebViewReceived = { webLink ->
                        // If we have web view, load web url
                        if (webLink.isNotEmpty()) {
                            val link = Uri.encode(webLink)
                            val route = "web_screen/$link"

                            navController.navigate(route) {
                                popUpTo(Screen.Start.route) { inclusive = true }
                            }
                        } else {
                            navController.navigate(Screen.Empty.route) {
                                popUpTo(Screen.Start.route) { inclusive = true }
                            }
                        }
                    })
            }


            composable(route = Screen.Web.route, arguments = listOf(navArgument(web_link_key) {
                type = NavType.StringType
                nullable = false
                defaultValue = DEFAULT_LINK
            })) { backStackEntry ->
                val webLink = backStackEntry.arguments?.getString(web_link_key).orEmpty()
                // Start New WebView Activity
                if (webLink.isNotEmpty()) {
                    val context = LocalContext.current

                    LaunchedEffect(true) {
                        val intent = Intent(context, WebScreenActivity::class.java)
                        intent.putExtra(web_link_key, webLink)
                        context.startActivity(intent)
                    }
                } else {
                    navController.navigate(Screen.Empty.route) {
                        popUpTo(Screen.Start.route) { inclusive = true }
                    }
                }
            }

            composable(Screen.Menu.route) {
                MenuScreen(
                    onHighScoresClick = { navController.navigate(Screen.HighScores.route) },
                    onSetNameClick = { navController.navigate(Screen.SetName.route) },
                )
            }
            composable(Screen.HighScores.route) { HighScoreScreen(onBackClick = { navController.popBackStack() }) }
            composable(Screen.SetName.route) {
                SetNameScreen(
                    onBackClick = { navController.popBackStack() },
                    onSaveClick = { navController.popBackStack() })
            }

            composable(Screen.Empty.route) {
                EmptyScreen {
                    navController.navigate(Screen.Start.route) {
                        popUpTo(Screen.Start.route) { inclusive = true }
                    }
                }
            }
        }
    }
}