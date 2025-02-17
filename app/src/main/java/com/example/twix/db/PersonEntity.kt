package com.example.twix.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "persons")
data class PersonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nick: String = "",
    val login: String = "",
    val password: String = "",
    val dateRegister: String = "",
    val description: String = "",
    val avatar: Int = 0,
    val posts: MutableList<Post> = mutableListOf()
) : Parcelable