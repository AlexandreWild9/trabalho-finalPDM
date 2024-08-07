package com.example.trabalhofinal

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.trabalhofinal.data.Client
import com.example.trabalhofinal.data.ClientRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

@Composable
fun ListaClientesScreen(
    navController: NavHostController,
    clientRepository: ClientRepository,
    context: Context
) {
    var listaClientes by remember { mutableStateOf<List<Client>>(emptyList()) }

    LaunchedEffect(Unit) {
        listaClientes = withContext(IO) {
            clientRepository.lerClientes()
        }
    }

    fun deleteClient(cpf: String) {
        clientRepository.deletarCliente(cpf, context)
        // Atualiza a lista local para refletir a exclusão imediatamente.
        listaClientes = listaClientes.filter { it.cpf != cpf }
    }

    Scaffold(
        topBar = { AppTopBar(titulo = "Clientes Cadastrados") }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(0.dp, 32.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listaClientes) { client ->
                    ClientItem(client, onDeleteConfirmed = { deleteClient(client.cpf) })
                }
            }
        }
    }
}
