package com.example.trabalhofinal.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class ClientRepository(val db: FirebaseFirestore) {

    fun inserirCliente(client: Client) {
        db.collection("clientes")
            .document(client.cpf)
            .set(client)
    }

    fun lerClientes(callback: (MutableList<Client>) -> Unit) {
        val listaClientes = mutableListOf<Client>()
        db.collection("clientes")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val client = document.toObject<Client>()
                    if (client != null) {
                        listaClientes.add(client)
                        Log.i("tag", "CPF: ${client.cpf}, Nome: ${client.nome}, Endereço: ${client.endereco}, Instagram: ${client.instagram}")
                    } else {
                        Log.w("tag", "Documento sem dados válidos: ${document.id}")
                    }
                }
                Log.d("tag", "Clientes carregados com sucesso")
                callback(listaClientes)
            }
            .addOnFailureListener { exception ->
                Log.d("tag", "Erro ao carregar clientes: ", exception)
                callback(mutableListOf())
            }
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
