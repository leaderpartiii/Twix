package com.example.twix.db

import android.content.Context
import com.example.twix.firebabasedb.updatePerson
import com.example.twix.firebabasedb.updatePersonPosts

class PersonRepository(context: Context) {
    private val personDao = AppDatabase.getDatabase(context).commandsDao()

    enum class Ops { INC_LIKES, DEC_LIKES, INC_TWEETS, DEC_TWEETS }

    fun addPerson(person: PersonEntity) {
        personDao.insertPerson(person)
        updatePerson(person)
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
        updatePersonPosts(nickname, currentPosts)
    }

    private fun clearPersons() {
        personDao.clearPerson()
    }

    private fun updatePostStatus(nickname: String, targetPost: Post, ops: Ops) {
        val person = personDao.getPerson(nickname)

        val updatedPosts = person.posts.map { post ->
            if (post.content == targetPost.content && post.createdAt == targetPost.createdAt) {
                when (ops) {
                    Ops.INC_LIKES -> post.copy(likes = post.likes + 1)
                    Ops.DEC_LIKES -> post.copy(likes = post.likes - 1)
                    Ops.INC_TWEETS -> post.copy(retweets = post.retweets + 1)
                    Ops.DEC_TWEETS -> post.copy(retweets = post.retweets - 1)
                }

            } else {
                post
            }
        }.toMutableList()

        personDao.updatePosts(nickname, updatedPosts)
    }

    fun incrementLikes(nickname: String, targetPost: Post) =
        updatePostStatus(nickname, targetPost, Ops.INC_LIKES)


    fun decrementLikes(nickname: String, targetPost: Post) =
        updatePostStatus(nickname, targetPost, Ops.DEC_LIKES)

    fun incrementRetweets(nickname: String, targetPost: Post) =
        updatePostStatus(nickname, targetPost, Ops.INC_TWEETS)


    fun decrementRetweets(nickname: String, targetPost: Post) =
        updatePostStatus(nickname, targetPost, Ops.DEC_TWEETS)

}
