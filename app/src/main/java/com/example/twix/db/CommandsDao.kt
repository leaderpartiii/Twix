package com.example.twix.db

import androidx.room.Dao

import androidx.room.Insert
import androidx.room.Query

@Dao
interface CommandsDao {
    @Insert
    fun insertPerson(person: PersonEntity)

    @Query("UPDATE persons SET posts = :updatedPosts WHERE nick = :nickname")
    fun updatePosts(nickname: String, updatedPosts: MutableList<Post>)

    /*@Query("UPDATE persons SET posts = :updatedPosts,  = :likes WHERE nick = :nickname")
    fun updatePostsLikes(nickname: String, updatedPosts: MutableList<Post>, likes: Int)*/

    @Query("SELECT * FROM persons WHERE nick = :nickname")
    fun getPerson(nickname: String): PersonEntity

    @Query("SELECT * FROM persons WHERE nick = :nickname")
    fun getPersonPostById(nickname: String): PersonEntity

    @Query("SELECT * FROM persons")
    fun getAllPersons(): List<PersonEntity>


    @Query("DELETE FROM persons")
    fun clearPerson(): Int
}