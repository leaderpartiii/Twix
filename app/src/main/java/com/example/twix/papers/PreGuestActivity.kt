package com.example.twix.papers

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twix.db.PersonEntity
import com.example.twix.firebabasedb.getPersonByNickname
import com.example.twix.firebabasedb.savePerson

class PreGuestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                onLoginSuccess = {
                    val intent = Intent(this, ProfileActivity::class.java).apply {
                        putExtra("person", it)
                    }
                    savePerson(it)
                    startActivity(intent)
                },
                onShowError = { alertForBlankField(this, it) }
            )
        }
    }
}


@Composable
fun LoginScreen(
    onLoginSuccess: (PersonEntity) -> Unit,
    onShowError: (message: String) -> Unit
) {
    var nickname by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Enter your credentials", fontWeight = FontWeight.Bold, fontSize = 20.sp)

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = nickname,
            onValueChange = { nickname = it },
            label = { Text("Nickname") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isLoading = true
                var user: PersonEntity?
                getPersonByNickname(
                    nickname = nickname,
                    onSuccess = {
                        user = it
                        Log.d("Debug", user.toString())
                        if (user != null && user!!.password == password) {
                            onLoginSuccess(user!!)
                        } else {
                            if (user != null)
                                onShowError("Password wrong")
                            onShowError("User not found")
                        }
                        isLoading = false
                    },
                    onFailure = { onShowError("Fields doesn't be blank") }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = nickname.isNotEmpty() && password.isNotEmpty() && !isLoading
        ) {
            Text("Log In")
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}



