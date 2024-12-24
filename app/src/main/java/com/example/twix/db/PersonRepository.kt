package com.example.twix.db

import android.content.Context

class PersonRepository(context: Context) {
    private val personDao = AppDatabase.getDatabase(context).commandsDao()

    fun addPerson(person: PersonEntity) {
        personDao.insertPerson(person)
    }

    fun getPersonByNickname(nickname: String): PersonEntity {
        return personDao.getPerson(nickname)
    }

    fun getAllPersons(): List<PersonEntity> {
        return personDao.getAllPersons()
    }

     fun addPost(nickname: String, newPost: Post, currentPosts: MutableList<Post>) {
        currentPosts.add(newPost)
        personDao.updatePosts(nickname, currentPosts)
    }

    private fun clearPersons() {
        personDao.clearPerson()
    }
}
