package com.example.twix.db

import androidx.room.Dao

import androidx.room.Insert
import androidx.room.Query

@Dao
interface CommandsDao {
    @Insert
    fun insertPerson(image: PersonEntity)

    @Query("SELECT * FROM persons")
    fun getAllPersons(): List<PersonEntity>

    @Query("DELETE FROM persons")
    fun clearPerson(): Int
}