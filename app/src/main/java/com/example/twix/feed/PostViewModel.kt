package com.example.twix.feed

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.twix.db.Post


class PostViewModel(val author: String = "Unknown") : ViewModel() {
    private val _posts = mutableStateListOf<Post>()
    val posts: List<Post> get() = _posts

    fun addPost(post: Post) {
        _posts.add(post)
    }
}
