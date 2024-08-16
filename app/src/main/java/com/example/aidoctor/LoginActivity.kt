package com.example.aidoctor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aidoctor.modalClass.Selection
import com.example.aidoctor.modalClass.User
import com.example.aidoctor.storageFunctions.UserDetails
import com.google.firebase.database.FirebaseDatabase


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var startDes = ""
        startDes = if (Selection.selectedButton == 0) {
            "login"
        } else {
            "signup"
        }

        setContent {

            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = startDes) {
                composable("signup") { SignUpScreen(navController) }
                composable("login") { LoginScreen() }
            }

        }
    }
}


@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 32.dp)
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Enter Gmail") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    signInWithEmail(email, password, context)
                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Login")
        }
    }
}

private fun signInWithEmail(email: String, password: String, context: Context) {
    val db = FirebaseDatabase.getInstance()
    val sanitizedUid = email.replace(".", ",")
    Log.e("SanitizedUid", sanitizedUid)
    val ref = db.getReference("Users").child(sanitizedUid)

    ref.get().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val userData = task.result?.getValue(User::class.java)
            if (userData != null) {
                if (userData.password == password) {
                    //Save User Details
                    saveUserDetails(userData,context)

                    context.startActivity(Intent(context, HomeActivity::class.java))
                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Invalid Password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "No user data found", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Data retrieval failed
            Toast.makeText(
                context,
                "Failed to retrieve user data: ${task.exception?.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

fun saveUserDetails(user: User,context: Context)
{
    UserDetails.saveLoginStatus(context = context,true)
    UserDetails.saveName(context,user.name)
    UserDetails.saveGender(context,user.gender)
    UserDetails.saveEmail(context,user.email)
//    UserDetails.saveDateOfBirth(context,user.dateOfBirth)
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
//    SignUpScreen()
}

@Composable
fun SignUpScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // State to hold the selected gender
    var selectedGender by remember { mutableStateOf("Male") }
    // List of gender options
    val genderOptions = listOf("Male", "Female", "Other")

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Register",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 32.dp).align(Alignment.CenterHorizontally)
        )
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Text(text = "Select Gender", style = MaterialTheme.typography.bodyMedium)

        // Gender options as radio buttons
        genderOptions.forEach { gender ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (gender == selectedGender),
                        onClick = { selectedGender = gender }
                    )
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (gender == selectedGender),
                    onClick = { selectedGender = gender }
                )
                Text(
                    text = gender,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }


        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                when {
                    name.isEmpty() -> {
                        Toast.makeText(context, "Please enter your name", Toast.LENGTH_SHORT).show()
                    }
                    selectedGender.isEmpty() -> {
                        Toast.makeText(context, "Please select your gender", Toast.LENGTH_SHORT).show()
                    }
                    email.isEmpty() -> {
                        Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
                    }
                    password.isEmpty() -> {
                        Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        signUpUser(User(name, selectedGender, email, password), context, navController)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Sign Up")
        }
    }
}

private fun signUpUser(user: User, context: Context,navController: NavController) {
    val db = FirebaseDatabase.getInstance()
    val ref = db.getReference("Users")

    ref.child(user.email.replace(".", ",")).setValue(user)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {

                navController.navigate("login") {
                    popUpTo("signup") { inclusive = true }
                }

                Toast.makeText(context, "User Registered Successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    context,
                    "User Registration Failed: ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        .addOnFailureListener { exception ->
            Toast.makeText(
                context,
                "User Registration Failed: ${exception.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
}



