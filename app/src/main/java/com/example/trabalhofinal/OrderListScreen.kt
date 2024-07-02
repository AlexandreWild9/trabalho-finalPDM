package com.example.trabalhofinal

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.trabalhofinal.data.Order
import com.example.trabalhofinal.data.OrderRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun OrderListScreen(
    navController: NavHostController, orderRepository: OrderRepository, context: Context
) {
    var listaPedidos by remember { mutableStateOf<List<Order>>(emptyList()) }

    LaunchedEffect(Unit) {
        listaPedidos = withContext(Dispatchers.IO) {
            orderRepository.lerPedidos()
        }
    }

    fun deleteOrder(id: Int) {
        orderRepository.deletarPedido(id)
        listaPedidos = listaPedidos.filter { it.id != id }
    }


    Scaffold(
        topBar = { AppTopBar(titulo = "Pedidos Realizados") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(listaPedidos) { pedido ->
                    PedidoItem(pedido = pedido, onDeleteConfirmed = { deleteOrder(pedido.id) })
                }
            }
        }
    }
}

@Composable
fun PedidoItem(pedido: Order, onDeleteConfirmed: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Id do Pedido: ${pedido.id}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Data: ${formatDate(pedido.data)}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "Cliente: ${pedido.client.nome}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                pedido.itens.forEach { itemPedido ->
                    Text(text = "Produto: ${itemPedido.productName}")
                    Text(text = "Quantidade: ${itemPedido.quantity}")
                }
            }
        }
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .size(32.dp)
                .clickable { showDialog = true },
            tint = MaterialTheme.colorScheme.error
        )
    }
    if (showDialog) {
        AlertDialogPedido(
            onDismissRequest = { showDialog = false },
            onConfirmation = {
                onDeleteConfirmed()
                showDialog = false
            },
            dialogTitle = "Confirmar Exclusão",
            dialogText = "Tem certeza que deseja excluir este pedido?"
        )
    }
}


@Composable
fun AlertDialogPedido(
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

fun formatDate(timestamp: Timestamp): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(timestamp.toDate())
}
