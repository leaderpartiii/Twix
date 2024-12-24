package com.example.twix.papers

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.twix.db.PersonEntity

import com.example.twix.db.PersonRepository
import com.example.twix.db.Post
import com.example.twix.feed.CreatePostScreen
import com.example.twix.feed.FeedScreen
import com.example.twix.profile.ProfileScreen
import com.example.twix.ui.theme.TwixTheme

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val person = intent.getParcelableExtra<PersonEntity>("person")
            MainScreen(person)
        }
    }
}

@Composable
private fun MainScreen(
    personEntity: PersonEntity? = null
) {
    val context = LocalContext.current;
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "profile",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("feed") { FeedScreen(personEntity) }
            composable("profile") {
                ProfileScreen(
                    personEntity = personEntity,
                    onCreatePostClick = { navController.navigate("createPost") },
                    onWatchMessage = { navController.navigate("showMessage/$it") }
                )
            }
            composable("createPost") {
                CreatePostScreen(
                    onPostCreated = { newPost ->
                        personEntity?.let {
                            val repository = PersonRepository(context)
                            repository.addPost(it.nick, Post(newPost), it.posts)
                        }
                        navController.popBackStack()
                    },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("showMessage/text") { backStackEntry ->
                val text = backStackEntry.arguments?.getString("text") ?: "Default Text"
                Log.d("Debug", text)
                CreateShowMessageScreen(text = text)
            }
        }
    }
}

@Composable
fun CreateShowMessageScreen(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = 40.sp)
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("feed", "Лента", Icons.Filled.Home),
        BottomNavItem("profile", "Профиль", Icons.Filled.Person)
    )

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        val currentRoute = currentRoute(navController)
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}


data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Preview
@Composable
private fun GreetingPreview() {
    TwixTheme {
        MainScreen()
    }
}