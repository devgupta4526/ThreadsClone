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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.underground.threadsclone.R
import com.underground.threadsclone.Utils.SharedPref
import com.underground.threadsclone.navigation.Routes
import com.underground.threadsclone.viewModel.AddThreadsViewModel

@Composable
fun AddThread(navHostController: NavHostController) {

    val threadsViewModel: AddThreadsViewModel = viewModel()
    val isPosted by threadsViewModel.isPosted.observeAsState(false)


    val context = LocalContext.current

    var thread by remember {
        mutableStateOf("")
    }

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }


    val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        android.Manifest.permission.READ_MEDIA_IMAGES
    } else {

        android.Manifest.permission.READ_EXTERNAL_STORAGE

    }
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

    LaunchedEffect(isPosted) {
        if (isPosted!!) {
            thread = ""
            imageUri = null
            Toast.makeText(context, "Thread Posted", Toast.LENGTH_SHORT).show()
            navHostController.navigate(Routes.Home.route) {
                popUpTo(Routes.AddThreads.route) {
                    inclusive = true
                }

            }
        }

    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val (crosspic, text, username, attachMedia,
            edittext, logo, replytext, button, imageBox) = createRefs()

        Image(painter = painterResource(id = R.drawable.baseline_close_24),
            contentDescription = "close",
            modifier = Modifier
                .constrainAs(crosspic) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .clickable {
                    navHostController.navigate(Routes.Home.route) {
                        popUpTo(Routes.AddThreads.route) {
                            inclusive = true
                        }

                    }

                })

        Text(
            text = "Add Thread", style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
            ), modifier = Modifier.constrainAs(text) {
                top.linkTo(crosspic.top)
                start.linkTo(crosspic.end, margin = 12.dp)
                bottom.linkTo(crosspic.bottom)
            }
        )

        Image(painter = rememberAsyncImagePainter(model = SharedPref.getImage(context)),
            contentDescription = "logo",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(text.bottom)
                    start.linkTo(parent.start)
                }
                .size(32.dp)
                .clip(CircleShape), contentScale = ContentScale.Crop)

        Text(
            text = SharedPref.getUsername(context), style = TextStyle(
                fontSize = 20.sp,
            ), modifier = Modifier.constrainAs(username) {
                top.linkTo(logo.top)
                start.linkTo(logo.end, margin = 12.dp)
                bottom.linkTo(logo.bottom)
            }
        )

        basicTextFieldWithHints(
            hint = "Start A New Thread...",
            value = thread,
            onValueChange = { thread = it },
            modifier = Modifier
                .constrainAs(edittext) {
                    top.linkTo(username.bottom)
                    start.linkTo(username.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 12.dp, vertical = 12.dp)
        )


        if (imageUri == null) {
            Image(painter = painterResource(id = R.drawable.baseline_attachment_24),
                contentDescription = "close",
                modifier = Modifier
                    .constrainAs(attachMedia) {
                        top.linkTo(edittext.top)
                        start.linkTo(edittext.start)
                    }
                    .clickable {

                        val isGranted = ContextCompat.checkSelfPermission(
                            context, permissionToRequest
                        ) == PackageManager.PERMISSION_GRANTED

                        if (isGranted) {
                            launcher.launch("image/*")
                        } else {
                            permissionLauncher.launch(permissionToRequest)
                        }

                    })
        } else {

            Box(modifier = Modifier
                .background(Color.Gray)
                .padding(12.dp)
                .constrainAs(imageBox) {
                    top.linkTo(edittext.bottom)
                    start.linkTo(edittext.start)
                    end.linkTo(parent.end)
                }
                .size(250.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(model = imageUri),
                    contentDescription = "image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        , contentScale = ContentScale.Crop
                )

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "remove image",
                    modifier = Modifier
                        .align(
                            Alignment.TopEnd
                        )
                        .clickable {
                            imageUri = null
                        }
                )
            }

        }

        Text(
            text = "Any One Can Reply..", style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 24.sp,
            ), modifier = Modifier.constrainAs(replytext) {
                start.linkTo(parent.start, margin = 12.dp)
                bottom.linkTo(parent.bottom, margin = 12.dp)
            }
        )

        TextButton(onClick = {
            if (imageUri == null) {
                threadsViewModel.saveData(
                    thread,
                    FirebaseAuth.getInstance().currentUser!!.uid,
                    ""
                )
            } else {
                threadsViewModel.saveImage(thread,FirebaseAuth.getInstance().currentUser!!.uid,imageUri!!)
            }
        },
            modifier = Modifier.constrainAs(button) {
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }) {
            Text(
                text = "Post", style = TextStyle(
                    fontSize = 20.sp
                )
            )


        }

    }

}


@Composable
fun basicTextFieldWithHints(
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier
) {

    Box(modifier = modifier) {
        if (value.isEmpty()) {
            Text(text = hint, color = Color.Gray)
        }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle.Default.copy(color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )
    }


}

@Preview(showBackground = true)
@Composable
fun AddThreadsView() {
//    AddThread()
}