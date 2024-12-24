package com.example.twix.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.twix.db.PersonEntity

@Composable
fun FeedScreen(personEntity: PersonEntity?) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val viewModel = PostViewModel(personEntity?.nick ?: "Unknown")
        PostScreen(viewModel = viewModel)
    }
}