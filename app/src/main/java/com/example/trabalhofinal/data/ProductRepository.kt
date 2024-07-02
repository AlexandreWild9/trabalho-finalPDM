package com.example.trabalhofinal.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class ProductRepository(val db: FirebaseFirestore) {

    fun inserirProduto(produto: Product) {

        db.collection("produtos")
            .document(produto.id.toString())
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

    suspend fun buscarProdutoPorId(idProduto: Int): Product? {
        return try {
            val document = db.collection("produtos")
                .document(idProduto.toString())
                .get()
                .await()

            document.toObject(Product::class.java)
        } catch (e: Exception) {
            // Lidar com exceções aqui, como logar o erro
            null
        }
    }

    fun deletarProduto(nome: String) {
        db.collection("produtos")
            .document(nome)
            .delete()
    }
}