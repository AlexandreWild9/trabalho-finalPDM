package com.example.trabalhofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import com.example.trabalhofinal.ui.theme.TrabalhoFinalTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trabalhofinal.data.ClientRepository
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val db = FirebaseFirestore.getInstance()
        val clientRepository = ClientRepository(db)

        super.onCreate(savedInstanceState)
        setContent {
            TrabalhoFinalTheme {

                val navController = rememberNavController()
                //val clientList = remember { clientRepository.listaClientes }


                NavHost(navController = navController, startDestination = "home") {
                    composable(route = "home") {
                        HomeScreen(navController = navController)
                    }
                    composable(route = "client") {
                        ClientScreen(
                            navController = navController,
                            clientRepository = clientRepository,
                            context = this@MainActivity
                        )
                    }
                    composable(route = "product") {
                        ProductScreen()
                    }
                    composable(route = "orders")
                    {
                        OrdersScreen()
                    }
                    composable(route = "ListaClientes")
                    {
                        ListaClientesScreen(navController, clientRepository, context = this@MainActivity)
                    }
                }
            }

        }

    }
}
