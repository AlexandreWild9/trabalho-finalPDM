// HomeScreen.kt
package com.example.trabalhofinal.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trabalhofinal.AppTopBar

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = { AppTopBar(titulo = "Home") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Café Ouro Negro de Minas")
            Button(onClick = {
                navController.navigate("client")
            }) {
                Text(text = "Clientes")
            }
            Button(onClick = {
                navController.navigate("product")
            }) {
                Text(text = "Produtos")
            }
            Button(onClick = {
                navController.navigate("orders")
            }) {
                Text(text = "Pedidos")
            }
        }
    }
}
