package com.example.melcomplus.viewmodels

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SearchViewModel : ViewModel() {
    var recentSearches by mutableStateOf(listOf<String>())
        private set

    fun addToRecentSearches(query: String) {
        if (query.isNotBlank() && query !in recentSearches) {
            recentSearches = (listOf(query) + recentSearches.toList()).take(3)
        }
    }
}
