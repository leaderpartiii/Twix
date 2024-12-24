package com.example.twix

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.twix.db.PersonRepository
import com.example.twix.firebabasedb.savePerson
import com.example.twix.papers.ProfileActivity
import com.example.twix.papers.RegisterActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository = PersonRepository(this)
        val persons = repository.getAllPersons()
        if (persons.isEmpty()) {
            startActivity(Intent(this, RegisterActivity::class.java))
        } else {
            val intent = Intent(this, ProfileActivity::class.java).apply {
                putExtra("person", persons.first())
                savePerson(persons.first());
            }
            startActivity(intent)
        }
    }
}



