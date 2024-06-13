import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trabalhofinal.AppTopBar
import com.example.trabalhofinal.data.Product
import com.example.trabalhofinal.data.ProductRepository

@Composable
fun ProductScreen(navController: NavController, ProductRepository: ProductRepository) {
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
            Text(text = "Cadastro Produto")

            OutlinedTextField(value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") },
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(text = "Tipo de grão", modifier = Modifier.padding(top = 16.dp))

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
                val produto = Product(nome, selectedGrao, selectedTorra)
                ProductRepository.inserirProduto(produto)
 /*               navController.navigate("ListaProdutos") {
                }*/
            }) {
                Text(text = "Salvar")
            }
        }
    }
}
