package com.example.trabalhofinal.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class OrderRepository(val db: FirebaseFirestore) {

    fun inserirPedido(order: Order) {
        db.collection("pedidos")
            .document(order.id.toString())
            .set(order)
    }

    suspend fun lerPedidos(): List<Order> {
        var listOrders = mutableListOf<Order>()
        try {
            val documents = db.collection("pedidos").get().await()
            for (document in documents) {
                val pedido = document.toObject<Order>()
                pedido?.let {
                    listOrders.add(it)
                }
            }
        } catch (e: Exception) {
            Log.i("tag", "erro: ${e.message}")
        }
        return listOrders
    }

    fun deletarPedido(id: Int) {
        db.collection("pedidos").document(id.toString()).delete()
            .addOnSuccessListener { Log.d("tag", "Pedido deletado com sucesso!") }
            .addOnFailureListener { e -> Log.w("tag", "Erro ao deletar pedido", e) }
    }
}
