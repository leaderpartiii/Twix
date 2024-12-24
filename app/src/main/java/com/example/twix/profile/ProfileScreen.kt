package com.example.twix.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twix.R
import com.example.twix.ui.theme.TwixTheme
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.twix.db.PersonEntity
import com.example.twix.db.Post
import com.example.twix.db.PersonRepository

@Composable
fun ProfileBox(nickname: String, login: String, dateRegister: String, description: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 36.dp)
            .padding(12.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.cat4_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(100))
            )
        }
        Column(
            Modifier.padding(top = 96.dp, start = 28.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(nickname, fontSize = 20.sp)
            Text("@$login", fontSize = 12.sp, color = Color.Gray)
            Text("Joined $dateRegister", fontSize = 12.sp, color = Color.Gray)
            Text(description, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun ProfileScreen(
    personEntity: PersonEntity? = null, onCreatePostClick: () -> Unit
) {
    val nickname: String = personEntity?.nick ?: "Unknown"
    val login: String = personEntity?.login ?: "Unknown"
    val dateRegister: String = personEntity?.dateRegister ?: "Unknown"
    val description: String = personEntity?.description ?: "This could be your description"
    val posts: List<Post> = personEntity?.posts?.takeIf { it.isNotEmpty() } ?: listOf(
        Post("This could be your first post"),
        Post("First post "),
        Post("Another cool post "),
        Post("Loving Twix!")
    )


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onCreatePostClick() },
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Image(painterResource(R.drawable.add_text_icon), contentDescription = null);
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .height(80.dp)
                .background(Color(0xFF93D1FF))
                .shadow(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Twix", fontSize = 40.sp)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.50f)
                    .padding(top = 64.dp)
                    .background(Color(0xFFF5F5F5))
                    .shadow(4.dp),
            ) {
                ProfileBox(nickname, login, dateRegister, description)
            }
            PostList(nickname, login, posts)
        }
    }
}

@Composable
fun PostList(nickname: String, login: String, posts: List<Post>) {
    LazyColumn {
        items(posts.reversed()) { post ->
            PostItem(post = post, nickname, login)
            Divider(color = Color.LightGray, thickness = 1.dp)
        }
    }
}

@Composable
fun PostItem(post: Post, nickname: String, login: String) {
    var likes by remember { mutableIntStateOf(post.likes) }
    var retweets by remember { mutableIntStateOf(post.retweets) }
    val person = PersonRepository(LocalContext.current)

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.fillMaxHeight(), contentAlignment = Alignment.CenterStart) {
            Image(
                painter = painterResource(id = R.drawable.cat4_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .padding(start = 8.dp, top = 8.dp)
                    .clip(RoundedCornerShape(100))
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = (56 + 12).dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = nickname, fontSize = 16.sp, color = Color.Black)
                Text(text = "@$login", fontSize = 12.sp, color = Color.Gray)
                Text(text = post.createdAt, fontSize = 12.sp, color = Color.Gray)
            }
            Text(text = post.content, fontSize = 12.sp, color = Color.Black)
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        IconButton(onClick = { likes++;person.incrementLikes(nickname, post) }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.heart_stroke_icon),
                    contentDescription = null
                )
                Text(text = likes.toString())
            }
        }
        IconButton(onClick = { retweets++; person.incrementRetweets(nickname, post) }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.retweet_stroke_icon),
                    contentDescription = null
                )
                Text(text = retweets.toString())
            }
        }
    }
}


@Preview
@Composable
private fun GreetingPreview() {
    TwixTheme {
        ProfileScreen(onCreatePostClick = { })
    }
}