////Product.kt
//package com.example.melcomplus.models
//
//data class Product(
//    val name: String,
//    val details: String,
//    val price: Double,
//    val imageUrl: String
//)

package com.example.melcomplus.models

data class Product(
    val sku: String,
    val name: String,
    val details: String,
    val price: Double,
    val imageUrl: String,
    val type: String // "GROCERY" or "RESTAURANT"
)
