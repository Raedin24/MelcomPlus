package com.example.melcomplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun SearchScreen() {
    var query by remember { mutableStateOf("") }
    val products = listOf("Laptop", "Phone", "Headphones", "Smartwatch")
    val filteredProducts = products.filter { it.contains(query, ignoreCase = true) }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search Products") }
        )
        LazyColumn {
            items(filteredProducts) { product ->
                Text(text = product, modifier = Modifier.padding(16.dp))
            }
        }
    }
}
