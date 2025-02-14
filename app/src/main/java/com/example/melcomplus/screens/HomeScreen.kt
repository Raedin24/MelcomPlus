//HomeScreen.kt
package com.example.melcomplus.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.melcomplus.data.CategoryRepository
import com.example.melcomplus.models.Category

@Composable
fun HomeScreen(onCategoryClick: (String) -> Unit) {
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp) // Inner padding for spacing the text
            ) {
                Text(
                    text = "Melcom Plus",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(CategoryRepository.categories) { category ->
                CategoryCard(
                    category = category,
                    onClick = { onCategoryClick(category.name) }
                )
            }
        }
    }
}


@Composable
fun CategoryCard(category: Category, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.primary)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(text = category.name, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onPrimary)
    }
}
