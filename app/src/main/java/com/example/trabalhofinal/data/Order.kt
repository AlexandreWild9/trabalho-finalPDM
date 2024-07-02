package com.example.trabalhofinal.data

import com.google.firebase.Timestamp

data class Order(
    val id: Int = 0,
    val data: Timestamp = Timestamp.now(),
    val client: Client = Client(),
    val itens: List<ItemPedido> = listOf()
) {
    // Construtor sem argumentos necessário para o Firebase Firestore
    constructor() : this(0, Timestamp.now(), Client(), listOf())
}

data class ItemPedido(
    val productID: Int = 0,
    val productName: String = "",
    val quantity: Int = 0
) {
    // Construtor sem argumentos necessário para o Firebase Firestore
    constructor() : this(0, "", 0)
}
