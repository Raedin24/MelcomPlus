package com.example.melcomplus.models

data class Subcategory(
    val name: String,
    val imageUrl: String,
    val products: List<Product>
)