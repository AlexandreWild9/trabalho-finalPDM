package com.example.trabalhofinal.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ClientRepository(private val db: FirebaseFirestore) {

    suspend fun inserirCliente(client: Client): Result<Void?> {
        return try {
            db.collection("clientes")
                .add(client)
                .await()
            Result.success(null)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
