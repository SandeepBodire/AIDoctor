package com.example.aidoctor.fragments

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aidoctor.R
import com.example.aidoctor.storageFunctions.UserDetails
import com.example.aidoctor.ui.theme.AIDoctorTheme

class HomeFragment : Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ComposeView>(R.id.composeView).setContent {
            AIDoctorTheme {
                MyApp(::onCardSelected)
            }

        }
    }

    private fun onCardSelected(value: Int) {
        when (value) {
            0 -> navigateToFragment(QuestionsFragment())
            1 -> navigateToFragment(WhyFirstAidFragment())
            2 -> navigateToFragment(AboutUsFragment())
            3 -> navigateToFragment(ProfileFragment())
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main, fragment)
            .commit()
    }
}


@Composable
fun MyApp(onQuestionsClick: (value: Int) -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("chooseService") { onQuestionsClick.invoke(0) }
        composable("why") { onQuestionsClick.invoke(1) }
        composable("aboutUs") { onQuestionsClick.invoke(2) }
        composable("profile") { onQuestionsClick.invoke(3) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(context.getString(R.string.app_title)) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Blue,
                    titleContentColor = Color.White
                )
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.robotic),
                        contentDescription = "AI",
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "Hi there! Your AI doctor, trained by medical professionals, is me.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Text(
                    text = "In what way may I assist you for first Aid? ",
                    style = MaterialTheme.typography.bodyLarge
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    HomeCard(navController, "Questions", "choose_service_image", "chooseService")
                    HomeCard(navController, "Why First aid?", "why_image", "why")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    HomeCard(navController, "About Us", "about_us_image", "aboutUs")
                    HomeCard(navController, "Profile", "profile_image", "profile")
                }
            }
        }
    )
}

@Composable
fun HomeCard(navController: NavController, title: String, imageName: String, route: String) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(150.dp)
            .clickable { navController.navigate(route) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(
                    id = when (title) {
                        "Questions" -> R.drawable.ic_questions
                        "Why First aid?" -> R.drawable.ic_firstaid
                        "About Us" -> R.drawable.ic_aboutus
                        "Profile" -> R.drawable.ic_profile
                        else -> R.drawable.robotic
                    }
                ),
                contentDescription = title,
                modifier = Modifier
                    .height(64.dp)
                    .width(64.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeFragmentPreview() {
    MyApp(onQuestionsClick = {})
}