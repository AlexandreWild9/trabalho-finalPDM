package com.example.trabalhofinal

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.trabalhofinal.data.Product
import com.example.trabalhofinal.data.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ListProductsScreen(navController: NavHostController, productRepository: ProductRepository, context: Context) {
    var listaProdutos by remember { mutableStateOf<List<Product>>(emptyList()) }

    LaunchedEffect(Unit) {
        listaProdutos = withContext(Dispatchers.IO) {
            productRepository.lerProdutos()
        }
    }

    Text(text = "Lista de Produtos")
    Column(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.wrapContentHeight()
        ) {
            items(listaProdutos){
                    produto ->
                ProductItem(produto)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductItem(produto: Product) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {


    }
}


