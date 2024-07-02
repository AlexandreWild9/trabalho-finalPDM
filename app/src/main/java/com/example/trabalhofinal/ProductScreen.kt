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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trabalhofinal.AppTopBar
import com.example.trabalhofinal.data.Product
import com.example.trabalhofinal.data.ProductRepository

@Composable
fun ProductScreen(navController: NavController, ProductRepository: ProductRepository) {
    var id by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var selectedGrao by remember { mutableStateOf("") }
    var selectedTorra by remember { mutableStateOf("") }



    Scaffold(topBar = { AppTopBar(titulo = "Produtos") }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(innerPadding),
        ) {
            Text(text = "Cadastro Produto",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                textAlign = TextAlign.Center)

            OutlinedTextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("ID") },
                modifier = Modifier.padding(top = 8.dp)
            )
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome do café") },
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(text = "Tipo de grão", modifier = Modifier.padding(top = 16.dp),
                fontSize = 20.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                RadioButton(
                    selected = selectedGrao == "Arabica",
                    onClick = { selectedGrao = "Arabica" })
                Text(text = "Árabica do cerrado")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = selectedGrao == "Conillon",
                    onClick = { selectedGrao = "Conillon" })
                Text(text = "Conillon")
            }
            Text(
                text = "Ponto da torra",
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = selectedTorra == "média",
                    onClick = { selectedTorra = "média" })
                Text(text = "Média")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = selectedTorra == "forte",
                    onClick = { selectedTorra = "forte" })
                Text(text = "Forte")
            }
            Button(onClick = {
                val productId = id.toIntOrNull() ?: 0
                val produto = Product(productId, nome, selectedGrao, selectedTorra)
                ProductRepository.inserirProduto(produto)
                navController.navigate("ListaProdutos") {
                }
            }) {
                Text(text = "Salvar")
            }
            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("ListaProdutos") }
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Produtos Cadastrados",
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
