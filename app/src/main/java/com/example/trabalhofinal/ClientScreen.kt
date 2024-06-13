package com.example.trabalhofinal

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trabalhofinal.data.ClientRepository
import com.example.trabalhofinal.data.Client
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ClientScreen(navController: NavController, clientRepository: ClientRepository, context: Context) {
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
                .padding(innerPadding)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Cadastrar Cliente")

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
        }
    }
}

@Composable
fun MyApp(context: Context) {
    val db = FirebaseFirestore.getInstance()
    val clientRepository = ClientRepository(db)
}
