package com.example.trabalhofinal

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.trabalhofinal.data.Product
import com.example.trabalhofinal.data.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ListProductsScreen(
    navController: NavHostController, productRepository: ProductRepository, context: Context
) {
    var listaProdutos by remember { mutableStateOf<List<Product>>(emptyList()) }

    LaunchedEffect(Unit) {
        listaProdutos = withContext(Dispatchers.IO) {
            productRepository.lerProdutos()
        }
    }

    fun deleteProduct(nome: String) {
        productRepository.deletarProduto(nome)
        // Atualiza a lista local para refletir a exclusão imediatamente.
        listaProdutos = listaProdutos.filter { it.nome != nome }
    }

    Scaffold(
        topBar = { AppTopBar(titulo = "Produtos Cadastrados") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.wrapContentHeight().padding(0.dp, 32.dp)
            ) {
                items(listaProdutos) { produto ->
                    ProductItem(produto, onDeleteConfirmed = { deleteProduct(produto.nome) })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductItem(produto: Product, onDeleteConfirmed: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Id: ${produto.id}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = "Nome: ${produto.nome}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = "Tipo de grão: ${produto.tipoGrao}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Torra: ${produto.torra}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { showDialog = true },
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
    if (showDialog) {
        AlertDialogProduct(
            onDismissRequest = { showDialog = false },
            onConfirmation = {
                onDeleteConfirmed()
                showDialog = false
            },
            dialogTitle = "Confirmar Exclusão",
            dialogText = "Tem certeza que deseja excluir este produto?"
        )
    }
}


@Composable
fun AlertDialogProduct(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    androidx.compose.material3.AlertDialog(
        icon = {
            Icon(Icons.Default.Delete, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Descartar")
            }
        }
    )
}