package com.example.aidoctor.fragments

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.aidoctor.R
import com.example.aidoctor.ui.theme.AIDoctorTheme


class WhyFirstAidFragment : Fragment(R.layout.fragment_why_first_aid) {
    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ComposeView>(R.id.composeWhyFirstAid).setContent {
            AIDoctorTheme {
                WhyFirstAidScreen(::navigateToHomeFragment)
            }

        }
    }

    private fun navigateToHomeFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main, HomeFragment())
            .commit()
    }

}


@Composable
fun WhyFirstAidScreen(onBackClicked: () -> Unit) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(26.dp)
                .background(Color.Blue)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue)
                .padding(vertical = 6.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = "AI",
                modifier = Modifier
                    .width(36.dp)
                    .height(36.dp)
                    .clickable {
                        onBackClicked.invoke()
                        Log.e("TAG", "On Back Image Clicked")
                    }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = context.getString(R.string.app_title),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp)
            ) {
                Text(
                    text = "Why first aid?",
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 12.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )

                val firstAidInfo = context.getString(R.string.first_aid_info)

                JustifiedText(
                    text = firstAidInfo,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }

    }

}