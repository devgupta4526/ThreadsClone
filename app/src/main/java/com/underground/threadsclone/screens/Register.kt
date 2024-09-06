package com.underground.threadsclone.screens

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.underground.threadsclone.R
import com.underground.threadsclone.navigation.Routes
import com.underground.threadsclone.viewModel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(navHostController: NavHostController) {

    var name by remember {
        mutableStateOf("")
    }

    var username by remember {
        mutableStateOf("")
    }

    var bio by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val authViewModel : AuthViewModel = viewModel()
    val firebaseUser by authViewModel.firebaseUser.observeAsState(null)

    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_IMAGES
    } else {

        android.Manifest.permission.READ_EXTERNAL_STORAGE

    }

    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->

            if (isGranted) {

            } else {

            }
        }

    LaunchedEffect(firebaseUser){
        if(firebaseUser != null){
            navHostController.navigate(Routes.BottomNav.route) {
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Register Here", style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp
            )
        )

        Box(modifier = Modifier.height(50.dp))

        Image(painter = if (imageUri == null) painterResource(id = R.drawable.user) else rememberAsyncImagePainter(
            model = imageUri
        ), contentDescription = "user",
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(Color.Gray)
                .clickable {

                    val isGranted = ContextCompat.checkSelfPermission(
                        context, permissionToRequest
                    ) == PackageManager.PERMISSION_GRANTED

                    if (isGranted) {
                        launcher.launch("image/*")
                    } else {
                        permissionLauncher.launch(permissionToRequest)
                    }

                }, contentScale = ContentScale.Crop)

        OutlinedTextField(value = username, onValueChange = { username = it }, label = {
            Text(text = "Username")
        }, keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ), modifier = Modifier.fillMaxWidth(), singleLine = true
        )

        Box(modifier = Modifier.height(20.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = {
            Text(text = "Name")
        }, keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ), modifier = Modifier.fillMaxWidth(), singleLine = true
        )

        Box(modifier = Modifier.height(20.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = {
            Text(text = "Email")
        }, keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ), modifier = Modifier.fillMaxWidth(), singleLine = true
        )

        Box(modifier = Modifier.height(20.dp))

        OutlinedTextField(value = password, onValueChange = { password = it }, label = {
            Text(text = "Password")
        }, keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ), modifier = Modifier.fillMaxWidth(), singleLine = true
        )

        Box(modifier = Modifier.height(30.dp))

        ElevatedButton(onClick = {

            if (name.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() || imageUri == null) {
                Toast.makeText(context, "Fill All Details", Toast.LENGTH_SHORT).show()
            }
            else{
                authViewModel.register(email, password, username,imageUri, name,context)
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Register", style = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                )
            )
        }

        Box(modifier = Modifier.height(30.dp))

        TextButton(onClick = {
            navHostController.navigate(Routes.Login.route) {
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }) {
            Text(
                text = "Already Registered? Login Here", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Blue
                )
            )
        }


    }


}


@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
//    Register()

}