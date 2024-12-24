package com.example.twix.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PostConverter {
    @TypeConverter
    fun fromPostList(posts: MutableList<Post>): String {
        return Gson().toJson(posts)
    }

    @TypeConverter
    fun toPostList(data: String): MutableList<Post>? {
        val listType = object : TypeToken<List<Post>>() {}.type
        return Gson().fromJson(data, listType)
    }
}
