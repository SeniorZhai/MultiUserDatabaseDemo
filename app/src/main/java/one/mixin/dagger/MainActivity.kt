package one.mixin.dagger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import one.mixin.dagger.ui.home.HomeScreen
import one.mixin.dagger.ui.login.LoginScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    
                    Scaffold(
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = "login",
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable("login") {
                                LoginScreen(
                                    onNavigateToHome = { username ->
                                        navController.navigate("home/$username") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    }
                                )
                            }
                            
                            composable(
                                route = "home/{username}",
                                arguments = listOf(
                                    navArgument("username") { type = NavType.StringType }
                                )
                            ) {
                                HomeScreen(
                                    onNavigateToLogin = {
                                        navController.navigate("login") {
                                            popUpTo("home/{username}") { inclusive = true }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
} 