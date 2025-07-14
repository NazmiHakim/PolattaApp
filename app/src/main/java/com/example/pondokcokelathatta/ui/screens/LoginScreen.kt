package com.example.pondokcokelathatta.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pondokcokelathatta.ui.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = viewModel(),
    onLoginSuccess: (String) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by authViewModel.authState.collectAsState()

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                authViewModel.signInWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Handle error
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (authState is AuthViewModel.AuthState.Loading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { authViewModel.signInWithEmailPassword(email, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login with Email")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    val signInIntent = authViewModel.getGoogleSignInIntent()
                    googleSignInLauncher.launch(signInIntent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign in with Google")
            }
        }

        LaunchedEffect(authState) {
            if (authState is AuthViewModel.AuthState.Authenticated) {
                onLoginSuccess((authState as AuthViewModel.AuthState.Authenticated).role)
            } else if (authState is AuthViewModel.AuthState.Error) {
                // Optionally show an error message
            }
        }
    }
}