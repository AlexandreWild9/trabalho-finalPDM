// HomeScreen.kt
package com.example.trabalhofinal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            Text(
                text = "Café Ouro Negro de Minas",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 44.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.secondary
            )

            HomeCard(
                title = "Cadastrar Clientes",
                subtitle = "Clientes Cadastrados",
                onClickTitle = { navController.navigate("client") },
                onClickSubtitle = { navController.navigate("ListaClientes") }
            )

            HomeCard(
                title = "Cadastrar Produtos",
                subtitle = "Produtos Cadastrados",
                onClickTitle = { navController.navigate("product") },
                onClickSubtitle = { navController.navigate("ListaProdutos") }
            )

            HomeCard(
                title = "Cadastrar Pedidos",
                subtitle = "Pedidos Cadastrados",
                onClickTitle = { navController.navigate("orders") },
                onClickSubtitle = { navController.navigate("ListaPedidos") }
            )
        }
    }
}

@Composable
fun HomeCard(
    title: String,
    subtitle: String,
    onClickTitle: () -> Unit,
    onClickSubtitle: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClickTitle() }
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Ir para a página",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp, color = Color.Black),
                modifier = Modifier
                    .clickable { onClickSubtitle() }
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }
    }
}
