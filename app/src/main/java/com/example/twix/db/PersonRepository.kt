package com.example.twix.db

import android.content.Context
import com.example.twix.firebabasedb.updatePerson
import com.example.twix.firebabasedb.updatePersonPosts

class PersonRepository(context: Context) {
    private val personDao = AppDatabase.getDatabase(context).commandsDao()

    enum class Ops { incLikes, decLikes, incTweets, decTweets }

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
                    Ops.incLikes -> post.copy(likes = post.likes + 1)
                    Ops.decLikes -> post.copy(likes = post.likes - 1)
                    Ops.incTweets -> post.copy(retweets = post.retweets + 1)
                    Ops.decTweets -> post.copy(retweets = post.retweets - 1)
                }

            } else {
                post
            }
        }.toMutableList()

        personDao.updatePosts(nickname, updatedPosts)
    }

    fun incrementLikes(nickname: String, targetPost: Post) =
        updatePostStatus(nickname, targetPost, Ops.incLikes)


    fun decrementLikes(nickname: String, targetPost: Post) =
        updatePostStatus(nickname, targetPost, Ops.decLikes)

    fun incrementRetweets(nickname: String, targetPost: Post) =
        updatePostStatus(nickname, targetPost, Ops.incTweets)


    fun decrementRetweets(nickname: String, targetPost: Post) =
        updatePostStatus(nickname, targetPost, Ops.decTweets)

}
