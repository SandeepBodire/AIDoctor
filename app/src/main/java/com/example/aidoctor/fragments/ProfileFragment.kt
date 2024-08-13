package com.example.aidoctor.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.aidoctor.LoginActivity
import com.example.aidoctor.R
import com.example.aidoctor.storageFunctions.UserDetails
import com.example.aidoctor.ui.theme.AIDoctorTheme

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ComposeView>(R.id.composeViewProfile).setContent {
            AIDoctorTheme {
                ProfileScreen(::navigateToHomeFragment, ::onLogoutClicked)
            }
        }
    }

    private fun navigateToHomeFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main, HomeFragment())
            .commit()
    }

    private fun onLogoutClicked() {
        UserDetails.saveLoginStatus(requireContext(), false)
        Toast.makeText(requireContext(), "Logout Successful", Toast.LENGTH_SHORT).show()

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}

@Composable
fun ProfileScreen(onBackClicked: () -> Unit, onLogoutConfirmed: () -> Unit) {
    val context = LocalContext.current

    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        LogoutDialog(
            onConfirm = {
                showLogoutDialog = false
                onLogoutConfirmed()
            },
            onDismiss = { showLogoutDialog = false }
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
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
                        onBackClicked()
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileDetailRow(title = "Name", value = UserDetails.getName(context)!!)
            ProfileDetailRow(title = "Gender", value = UserDetails.getGender(context)!!)
            ProfileDetailRow(title = "Email", value = UserDetails.getEmail(context)!!)

            Button(
                onClick = {
                    showLogoutDialog = true
                },
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_logout_24),
                    contentDescription = "Logout",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout")
            }
        }
    }
}

@Composable
fun LogoutDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Logout")
        },
        text = {
            Text("Are you sure you want to logout?")
        },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("No")
            }
        }
    )
}

@Composable
fun ProfileDetailRow(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = ":",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(2f)
        )
    }
}
