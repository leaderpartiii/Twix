package com.example.twix.papers

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.twix.R
import com.example.twix.ui.theme.TwixTheme

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TwixTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Status(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Tittle() {
    Box(
        modifier = Modifier
            .fillMaxHeight(0.145f)
            .fillMaxWidth(0.358f),
        contentAlignment = Alignment.TopStart
    ) {
        Text(
            text = "Twix",
            modifier = Modifier.align(Alignment.Center),
            fontSize = 40.sp
        )
    }
}

@Composable
fun CenterTittle() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Text(
            text = "Let’s you in",
            modifier = Modifier
                .padding(top = 177.dp),
            fontSize = 40.sp
        )
    }

}

@Composable
fun ButtonsReg() {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .padding(top = 338.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = {/*TODO*/ }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(300.dp)
                        .border(BorderStroke(1.dp, SolidColor(Color.Black)))
                        .padding(top = 12.dp, bottom = 12.dp, start = 30.dp, end = 30.dp),
                ) {
                    Text(text = "Continue with VK   ", fontSize = 16.sp)
                    Image(
                        painter = painterResource(id = R.drawable.vk_icons),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                    )
                }
            }

            Button(onClick = {/*TODO*/ }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(300.dp)
                        .border(BorderStroke(1.dp, SolidColor(Color.Black)))
                        .padding(top = 12.dp, bottom = 12.dp, start = 30.dp, end = 30.dp),
                ) {
                    Text(text = "Continue with Google   ", fontSize = 16.sp)
                    Image(
                        painter = painterResource(id = R.drawable.google_icons),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                    )
                }
            }

            Button(onClick = { context.startActivity(Intent(context, AuthActivity::class.java)) }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(300.dp)
                        .border(BorderStroke(1.dp, SolidColor(Color.Black)))
                        .padding(top = 12.dp, bottom = 12.dp, start = 30.dp, end = 30.dp),
                ) {
                    Text(text = "Continue as guest   ", fontSize = 16.sp)
                    Image(
                        painter = painterResource(id = R.drawable.an_icons),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                    )
                }
            }

            Button(onClick = {
                context.startActivity(Intent(context, PreGuestActivity::class.java))
            }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(300.dp)
                        .border(BorderStroke(1.dp, SolidColor(Color.Black)))
                        .padding(top = 12.dp, bottom = 12.dp, start = 30.dp, end = 30.dp),
                ) {
                    Text(text = "I have account   ", fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun Status(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Tittle()
        CenterTittle()
        ButtonsReg()
    }
}

@Preview(showBackground = true, name = "Aizat", group = "FirstScreen")
@Composable
private fun GreetingPreview() {
    TwixTheme {
        Status()
    }
}

