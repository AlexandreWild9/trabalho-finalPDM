package com.example.trabalhofinal.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class ProductRepository(val db: FirebaseFirestore) {

    fun inserirProduto(produto: Product) {


        db.collection("produtos")
            .document(produto.nome)
            .set(produto)
    }

    suspend fun lerProdutos() : List<Product> {
        val listProduct = mutableListOf<Product>()
        try {
            val documents = db.collection("produtos").get().await()
            for (document in documents) {
                val product = document.toObject<Product>()
                product?.let {
                    listProduct.add(it)
                }
            }
            Log.d("tag", "Produtos carregados com sucesso")
        } catch (e: Exception) {
            Log.i("tag", "erro: ${e.message}")

        }
        return listProduct

    }
}