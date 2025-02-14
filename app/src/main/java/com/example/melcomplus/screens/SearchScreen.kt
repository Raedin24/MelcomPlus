package com.example.melcomplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.melcomplus.data.CategoryRepository

@Composable
fun SearchScreen() {
    var query by remember { mutableStateOf("") }

    // Fetch products from all categories
    val products = CategoryRepository.categories.flatMap { it.items }

    val filteredProducts = products.filter { it.name.contains(query, ignoreCase = true) }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search Products") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
        LazyColumn {
            items(filteredProducts) { product ->
                Text(text = product.name, modifier = Modifier.padding(16.dp))
            }
        }
    }
}
