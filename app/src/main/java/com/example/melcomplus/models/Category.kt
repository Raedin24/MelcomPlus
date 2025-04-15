////Category.kt
//package com.example.melcomplus.models
//
//data class Category(
//    val name: String,
//    val icon: String,
//    val type: String,
//    val items: List<Product>
//)


package com.example.melcomplus.models

data class Category(
    val name: String,
    val icon: String,
    val subcategories: List<Subcategory>
)