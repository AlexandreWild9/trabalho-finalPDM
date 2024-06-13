package com.example.trabalhofinal.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class ClientRepository(val db: FirebaseFirestore) {

    fun inserirCliente(client: Client) {
        db.collection("clientes")
            .document(client.cpf)
            .set(client)
    }

   suspend fun lerClientes(): List<Client> {
        val listaClientes = mutableListOf<Client>()
        try {
            val documents = db.collection("clientes").get().await()
            for (document in documents) {
                val client = document.toObject<Client>()
                client?.let {
                    listaClientes.add(it)
                    Log.i("tag", "CPF: ${it.cpf}, Nome: ${it.nome}, Endereço: ${it.endereco}, Instagram: ${it.instagram}")
                } ?: Log.w("tag", "Documento sem dados válidos: ${document.id}")
            }
            Log.d("tag", "Clientes carregados com sucesso")
        } catch (e: Exception) {
            Log.d("tag", "Erro ao carregar clientes: ", e)
        }
        return listaClientes
    }


    fun deletarCliente(cpf: String, context: Context) {
        db.collection("clientes")
            .document(cpf)
            .delete().addOnSuccessListener {
                Log.d("tag", "Cliente deletado com sucesso")
                Toast.makeText(context, "Cliente deletado com sucesso", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { exception ->
                Log.d("tag", "Erro ao deletar cliente: ", exception)
                Toast.makeText(context, "Erro ao deletar cliente", Toast.LENGTH_SHORT).show()
            }
    }
}
