package com.example.trabalhofinal

import ProductScreen
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddReaction
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Blender
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.sharp.AddReaction
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.trabalhofinal.ui.theme.TrabalhoFinalTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.example.trabalhofinal.data.ClientRepository
import com.example.trabalhofinal.data.OrderRepository
import com.example.trabalhofinal.data.ProductRepository
import com.example.trabalhofinal.ui.screens.HomeScreen
import com.google.firebase.firestore.FirebaseFirestore


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val db = FirebaseFirestore.getInstance()
        val clientRepository = ClientRepository(db)
        val productRepository = ProductRepository(db)
        val orderRepository = OrderRepository(db)


        super.onCreate(savedInstanceState)
        setContent {
            TrabalhoFinalTheme {

                val navController = rememberNavController()
                val items = remember {
                    listOf(
                        Pair("Home", Icons.Filled.Home),
                        Pair("Clientes", Icons.Filled.AddReaction),
                        Pair("Produtos", Icons.Filled.Coffee),
                        Pair("Pedidos", Icons.Filled.ListAlt),
                    )
                }
                var selectedItem by remember {
                    mutableStateOf(items.first())
                }


                Column(Modifier.fillMaxSize()) {
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        Modifier.weight(1f)
                    ) {
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
                            ProductScreen(navController = navController, productRepository)
                        }
                        composable(route = "orders")
                        {
                            OrdersScreen(clientRepository, productRepository, orderRepository, navController)
                        }
                        composable(route = "ListaClientes")
                        {
                            ListaClientesScreen(
                                navController,
                                clientRepository,
                                context = this@MainActivity
                            )
                        }
                        composable(route = "ListaProdutos")
                        {
                            ListProductsScreen(navController, productRepository, context = this@MainActivity)
                        }
                        composable(route = "ListaPedidos")
                        {
                            OrderListScreen(navController, orderRepository, context = this@MainActivity)
                        }
                    }
                    BottomAppBar(actions = {
                        items.forEach { item ->
                            val text = item.first
                            val icon = item.second
                            NavigationBarItem(
                                selected = selectedItem == item,
                                onClick = {
                                    selectedItem = item
                                    val route =  when(text) {
                                        "Home" -> "home"
                                        "Clientes" -> "client"
                                        "Produtos" -> "product"
                                        "Pedidos" -> "orders"
                                        else -> {"home"}
                                    }
                                    navController.navigate(route, navOptions = navOptions {
                                        launchSingleTop = true
                                        popUpTo("home")

                                    })
                                },
                                icon = {
                                    Icon(icon, contentDescription = null)
                                },
                                label = {
                                    Text(text = text)
                                })
                        }

                    })
                }
            }
        }


    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(titulo: String) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.inversePrimary,
        ),
        title = { Text(text = titulo) },
    )

}
