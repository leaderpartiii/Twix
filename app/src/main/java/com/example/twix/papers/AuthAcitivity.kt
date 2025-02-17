package com.example.twix.papers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.twix.db.PersonEntity
import com.example.twix.db.PersonRepository
import com.example.twix.firebabasedb.savePerson
import com.example.twix.ui.theme.TwixTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

import android.app.AlertDialog
import android.util.Log
import androidx.compose.runtime.saveable.rememberSaveable

private const val AMOUNT_OF_RANDOM = 8
class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                MainScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

fun alertForBlankField(context: Context, message: String) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle("Предупреждение")
    builder.setMessage(message)
    builder.setPositiveButton("ОК") { dialog, _ ->
        dialog.dismiss()
    }
    val dialog = builder.create()
    dialog.show()
}

private fun getDrawableIdByName(context: Context, name: String): Int {
    return context.resources.getIdentifier(name, "drawable", context.packageName)
}

private fun getRandomAvatar(context: Context): Int {
    val nameAvatar: String = "cat" + (1..AMOUNT_OF_RANDOM).random() + "_icon"
    val id =  getDrawableIdByName(context, nameAvatar)
    if (id == 0) {
        Log.e("getDrawableIdByName", "Drawable not found for name: $nameAvatar")
    }
    return id
}

fun goToProfile(
    context: Context,
    nicknameInput: String,
    loginInput: String,
    passwordInput: String,
    descriptionInput: String
) {

    val myDate: Date = Calendar.getInstance().time
    val formatterMonthYear = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    val formattedMonthYear = formatterMonthYear.format(myDate)
    val avatar = getRandomAvatar(context)
    val newPerson = PersonEntity(
        nick = nicknameInput,
        login = loginInput,
        password = passwordInput,
        dateRegister = formattedMonthYear,
        description = descriptionInput,
        avatar = avatar,
        posts = ArrayList()
    )
    val intent = Intent(context, ProfileActivity::class.java).apply {
        putExtra("person", newPerson)
    }

    val repository = PersonRepository(context)
    repository.addPerson(newPerson)
    savePerson(newPerson)

    context.startActivity(intent)
}

@Composable
fun AuthScreen() {
    val context = LocalContext.current

    var login by rememberSaveable { mutableStateOf("") }
    var nickname by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .padding(top = 256.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = login,
                onValueChange = { login = it },
                label = { Text("Login") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = nickname,
                onValueChange = { nickname = it },
                label = { Text("Nickname") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = {
                if (nickname.isBlank() || login.isBlank() || password.isBlank()) {
                    alertForBlankField(context, "Fields doesn't be blank")
                } else
                    goToProfile(context, nickname, login, password, description)
            }) {
                Text(text = "Confirm")
            }
        }
    }
}

@Composable
private fun MainScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Tittle()
        AuthScreen()
    }
}

@Preview
@Composable
private fun GreetingPreview() {
    TwixTheme {
        MainScreen()
    }
}
