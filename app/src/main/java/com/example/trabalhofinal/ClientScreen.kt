package com.example.trabalhofinal

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trabalhofinal.data.ClientRepository
import com.example.trabalhofinal.data.Client
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ClientScreen(
    navController: NavController,
    clientRepository: ClientRepository,
    context: Context
) {
    var cpf by remember { mutableStateOf("") }
    var nome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var instagram by remember { mutableStateOf("") }

    Scaffold(
        topBar = { AppTopBar(titulo = "Clientes") }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Cadastrar Cliente",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                value = cpf,
                onValueChange = { cpf = it },
                label = { Text("CPF") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.padding(top = 8.dp)
            )

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") },
                modifier = Modifier.padding(top = 8.dp)
            )

            OutlinedTextField(
                value = telefone,
                onValueChange = { telefone = it },
                label = { Text("Telefone") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.padding(top = 8.dp)
            )

            OutlinedTextField(
                value = endereco,
                onValueChange = { endereco = it },
                label = { Text("Endereço") },
                modifier = Modifier.padding(top = 8.dp)
            )

            OutlinedTextField(
                value = instagram,
                onValueChange = { instagram = it },
                label = { Text("Instagram") },
                modifier = Modifier.padding(top = 8.dp)
            )

            Button(
                onClick = {
                    val client = Client(cpf, nome, telefone, endereco, instagram)
                    clientRepository.inserirCliente(client)

                    navController.navigate("ListaClientes")
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Cadastrar")
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("ListaClientes") }
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Clientes Cadastrados",
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
fun MyApp(context: Context) {
    val db = FirebaseFirestore.getInstance()
    val clientRepository = ClientRepository(db)
}
