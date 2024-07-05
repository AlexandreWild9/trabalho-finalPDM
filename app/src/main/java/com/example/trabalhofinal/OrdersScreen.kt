package com.example.trabalhofinal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trabalhofinal.data.Client
import com.example.trabalhofinal.data.ClientRepository
import com.example.trabalhofinal.data.ItemPedido
import com.example.trabalhofinal.data.Order
import com.example.trabalhofinal.data.OrderRepository
import com.example.trabalhofinal.data.Product
import com.example.trabalhofinal.data.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(clientRepository: ClientRepository, productRepository: ProductRepository, orderRepository: OrderRepository, navController: NavController) {
    var id by remember { mutableStateOf("") }
    var selectedClient by remember { mutableStateOf<Client?>(null) }
    var listaClientes by remember { mutableStateOf<List<Client>>(emptyList()) }
    var listaProdutos by remember { mutableStateOf<List<Product>>(emptyList()) }
    var selectedProducts by remember { mutableStateOf(mutableMapOf<Product, Int>()) }

    // Carregar clientes e produtos ao iniciar a tela
    LaunchedEffect(Unit) {
        listaClientes = withContext(Dispatchers.IO) {
            clientRepository.lerClientes()
        }
        listaProdutos = withContext(Dispatchers.IO) {
            productRepository.lerProdutos()
        }
    }

    Scaffold(topBar = { AppTopBar(titulo = "Pedidos") }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(innerPadding)
        ) {
            Text(text = "Cadastrar Pedidos",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                textAlign = TextAlign.Center)

            OutlinedTextField(
                label = { Text(text = "ID do Pedido") },
                value = id,
                onValueChange = { id = it },
                modifier = Modifier.padding(vertical = 8.dp)
            )

            if (listaClientes.isNotEmpty()) {
                var expandedClientDropdown by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expandedClientDropdown,
                    onExpandedChange = { expandedClientDropdown = !expandedClientDropdown }
                ) {
                    OutlinedTextField(
                        value = selectedClient?.nome ?: "",
                        onValueChange = {},
                        label = { Text("Selecione o Cliente") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedClientDropdown) },
                        modifier = Modifier
                            .menuAnchor()
                            .padding(top = 8.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = expandedClientDropdown,
                        onDismissRequest = { expandedClientDropdown = false }
                    ) {
                        listaClientes.forEach { client ->
                            DropdownMenuItem(
                                text = { Text(text = client.nome) },
                                onClick = {
                                    selectedClient = client
                                    expandedClientDropdown = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                listaProdutos.forEach { product ->
                    ProductListItem(product, selectedProducts) { changedProduct, quantity ->
                        selectedProducts[changedProduct] = quantity
                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                Button(
                    onClick = {
                        val orderId = id.toIntOrNull() ?: 0
                        val itens = selectedProducts.map { (product, quantity) ->
                            ItemPedido(productID = product.id, productName = product.nome, quantity = quantity)
                        }
                        val order = Order(
                            id = orderId,
                            client = selectedClient ?: Client(),
                            itens = itens
                        )
                        orderRepository.inserirPedido(order)
                        navController.navigate("ListaPedidos")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Salvar Pedido")
                }
            } else {
                Text("Carregando clientes...")
            }
            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("ListaPedidos") }
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pedidos Cadastrados",
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp)
                        .padding(horizontal = 8.dp),

                    color = Color.Black
                )
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Ir para a página",
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
private fun ProductListItem(
    product: Product,
    selectedProducts: MutableMap<Product, Int>,
    onQuantityChanged: (Product, Int) -> Unit
) {
    val quantity = remember { mutableStateOf(selectedProducts[product] ?: 0) }

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = product.nome)

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "-",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        if (quantity.value > 0) {
                            quantity.value = quantity.value - 1
                            onQuantityChanged(product, quantity.value)
                        }
                    }
                    .padding(4.dp)
            )
            Text(
                text = "${quantity.value} pacotes (500g)",
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Text(
                text = "+",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        quantity.value = quantity.value + 1
                        onQuantityChanged(product, quantity.value)
                    }
                    .padding(4.dp)
            )
        }
    }
}