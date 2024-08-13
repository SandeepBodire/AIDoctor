package com.example.aidoctor

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aidoctor.modalClass.Selection
import com.example.aidoctor.storageFunctions.UserDetails
import com.example.aidoctor.ui.theme.AIDoctorTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIDoctorTheme {
                MainScreen(::gotoLogin)
            }
        }
    }

    private fun gotoLogin(value: Int) {

        when (value) {
            1 -> {
                startActivity(Intent(this, HomeActivity::class.java))
            }

            2 -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}

@Composable
fun MainScreen(onLoginClick: (value: Int) -> Unit) {
    var showSplash by remember { mutableStateOf(true) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(3000) // 3 seconds delay
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
    } else {
        if (UserDetails.getLoginStatus(context)) {
            onLoginClick.invoke(1)
        } else {
            NavigationScreen(onLoginClick)
        }
    }
}

@Composable
fun SplashScreen() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = context.getString(R.string.app_title),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.robotic),
                contentDescription = "AI Doctor",
            )
        }
    }

}

//Previous Code
@Composable
fun NavigationScreen(onLoginClick: (value: Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Button(
                onClick = {
                    Selection.selectedButton = 1
                    onLoginClick.invoke(2)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Sign Up", fontSize = 18.sp)
            }

            Button(
                onClick = {
                    Selection.selectedButton = 0
                    onLoginClick.invoke(2)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Login", fontSize = 18.sp)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NavigationScreen(onLoginClick = {})
}