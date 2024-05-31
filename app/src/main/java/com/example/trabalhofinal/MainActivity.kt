package com.example.trabalhofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.trabalhofinal.ui.theme.TrabalhoFinalTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrabalhoFinalTheme {

                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    composable(route = "home") {
                        HomeScreen(navController = navController)
                    }
                    composable(route = "client") {
                        ClientScreen()
                    }
                    composable(route = "product"){
                        ProductScreen()
                    }
                    composable(route = "orders")
                    {
                        OrdersScreen()
                    }
                }
            }
        }

    }

}
