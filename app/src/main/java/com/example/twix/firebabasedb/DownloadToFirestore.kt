package com.example.twix.firebabasedb


import android.util.Log
import com.example.twix.db.PersonEntity
import com.example.twix.db.Post
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

fun savePerson(person: PersonEntity) {
    val db = FirebaseFirestore.getInstance()

    val personMap = hashMapOf(
        "nick" to person.nick,
        "login" to person.login,
        "password" to person.password,
        "dateRegister" to person.dateRegister,
        "description" to person.description,
        "posts" to person.posts.map { post ->
            hashMapOf(
                "content" to post.content,
                "createdAt" to post.createdAt,
                "likes" to post.likes,
                "retweets" to post.retweets
            )
        }
    )

    db.collection("persons").document(person.nick)
        .set(personMap)
}

fun updatePerson(person: PersonEntity) {
    val db = FirebaseFirestore.getInstance()

    val postMap = hashMapOf(
        "nick" to person.nick,
        "login" to person.login,
        "password" to person.password,
        "dateRegister" to person.dateRegister,
        "description" to person.description,
        "posts" to person.posts.map { post ->
            hashMapOf(
                "content" to post.content,
                "createdAt" to post.createdAt,
                "likes" to post.likes,
                "retweets" to post.retweets
            )
        }
    )

    db.collection("persons")
        .document(person.nick)
        .update("posts", FieldValue.arrayUnion(postMap))

}

fun updatePersonPosts(nickname: String, posts: MutableList<Post>) {
    val db = FirebaseFirestore.getInstance()

    val personMap = hashMapOf(
        "posts" to posts.map { post ->
            hashMapOf(
                "content" to post.content,
                "createdAt" to post.createdAt,
                "likes" to post.likes,
                "retweets" to post.retweets
            )
        }
    )

    db.collection("persons")
        .document(nickname)
        .set(personMap, SetOptions.merge())
}

fun getAllUsersFromFirebase(onSuccess: (List<PersonEntity>) -> Unit, onFailure: () -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("persons")
        .get()
        .addOnSuccessListener { documents ->
            val users = documents.mapNotNull { document ->
                document.toObject(PersonEntity::class.java)
            }
            onSuccess(users)
        }
        .addOnFailureListener {
            onFailure()
        }
}

fun getPersonByNickname(
    nickname: String,
    onSuccess: (PersonEntity?) -> Unit,
    onFailure: () -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    if (nickname.isBlank()) {
        onFailure()
        return
    }
    db.collection("persons")
        .document(nickname)
        .get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                val person = document.toObject(PersonEntity::class.java)
                if (person != null) {
                    Log.d("Debug", person.nick)
                    Log.d("Debug", person.password)
                }
                onSuccess(person)
            } else {
                onSuccess(null)
            }
        }
        .addOnFailureListener {
            onFailure()
        }
}
