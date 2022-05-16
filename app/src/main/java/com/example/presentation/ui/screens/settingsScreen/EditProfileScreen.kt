package com.example.presentation.ui.screens.ProfileScreen

import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.domain.model.Video
import com.example.presentation.MainActivityViewModel
import com.example.presentation.ui.screens.Components.VerticalSpacer
import com.example.presentation.ui.screens.settingsScreen.SettingsScreenViewModel
import com.example.presentation.ui.theme.AppTheme
import com.example.utility.TAG
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toTimeUnit


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EditProfile(
    navigateBackProfile: () -> Unit,
    mainActivityViewModel: MainActivityViewModel,
    settingsScreenViewModel: SettingsScreenViewModel
) {
    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    var isOpen by remember {
        mutableStateOf(false)
    }
    val onActivateAlert: (Boolean) -> Unit = { isOpen = it }

    // Profile Image
    val emptyImageUri = Uri.parse("file://dev/null")
    var imageUri by remember { mutableStateOf(emptyImageUri) }
    var permissions = listOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
        permissions = listOf(Manifest.permission.CAMERA)
    }

    val permissionsState = rememberMultiplePermissionsState(permissions)


    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { MyAppBar(navigateBackToProfile = navigateBackProfile, text = "Edit Profile") }
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
            ) {
                SettingProfile(
                    onActivateAlert = onActivateAlert,
                    imageUri = imageUri,
                    permissionsState = permissionsState
                )
                VerticalSpacer(space = AppTheme.dimens.grid_2)
                SettingsInput(
                    labelText = "Name",
                    inputValue = name,
                    onValueChange = { newName -> name = newName }
                )
                VerticalSpacer(space = AppTheme.dimens.grid_2)
                SettingsInput(
                    labelText = "Your Email",
                    inputValue = email,
                    onValueChange = { newEmail -> email = newEmail }
                )
                VerticalSpacer(space = AppTheme.dimens.grid_2)
                SettingsInput(
                    labelText = "Password",
                    inputValue = password,
                    onValueChange = { newPassword ->
                        password = newPassword
                    })
                VerticalSpacer(space = 16.dp)
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AppTheme.colors.profileHighLightColor,
                        contentColor = AppTheme.colors.onProfileButtonColor
                    )
                ) {
                    Text(text = "Save Now", style = AppTheme.typography.NetflixF20W500)
                }
            }
        }
        if (permissionsState.allPermissionsGranted && isOpen) {
            CameraCapture(
                //isOpen = isOpen,
                closeCameraHandler = onActivateAlert,
                modifier = Modifier,
                onImageFile = { file -> imageUri = file }
            )
        } else {
            val rational = getTextToShowGivenPermissions(
                permissions = permissionsState.revokedPermissions,
                shouldShowRationale = permissionsState.shouldShowRationale
            )
            Rationale(text = rational, onCancel = { onActivateAlert.invoke(false) }, isOpen = isOpen)
        }

    }


}

@OptIn(ExperimentalPermissionsApi::class)
private fun getTextToShowGivenPermissions(
    permissions: List<PermissionState>,
    shouldShowRationale: Boolean
): String {
    val revokedPermissionsSize = permissions.size
    if (revokedPermissionsSize == 0) return ""

    val textToShow = StringBuilder().apply {
        append("The ")
    }
    val permissionsText = if (revokedPermissionsSize == 1) "permission" else "permissions"
    val anotherPermissionsText = if (revokedPermissionsSize == 1) "it" else "all of them"

    for (i in permissions.indices) {
        textToShow.append(permissions[i].permission)
        when {
            revokedPermissionsSize > 1 && i == revokedPermissionsSize - 2 -> {
                textToShow.append(", and ")
            }
            i == revokedPermissionsSize - 1 -> {
                textToShow.append(" ")
            }
            else -> {
                textToShow.append(", ")
            }
        }
    }

    textToShow.append(if (revokedPermissionsSize == 1) "permission is" else "permissions are")
    textToShow.append(
        if (shouldShowRationale) {
            " important. Please grant $anotherPermissionsText for the app to function properly."
        } else {
            " denied. Go to settings to allow the  $permissionsText."
        }
    )
    return textToShow.toString()
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SettingProfile(
    onActivateAlert: (Boolean) -> Unit,
    imageUri: Uri?,
    permissionsState: MultiplePermissionsState
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
//    var profilePicture by remember {
//        mutableStateOf(R.drawable.profile_picture)
//    }
//    val onActivateAlert: (Boolean) -> Unit = { isOpen = it }

    //val imageBitmap = imageUri.getImageFromMediaStore(context = context)

    val showImage = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(imageUri)
            .crossfade(true)
            .build(),
    )
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .clickable {
                    if (!permissionsState.allPermissionsGranted) {
                        permissionsState.launchMultiplePermissionRequest()
                    }
                    onActivateAlert.invoke(true)
                },
            painter = showImage,
            contentDescription = "Settings Profile Picture",
            contentScale = ContentScale.Fit
        )
        VerticalSpacer(space = AppTheme.dimens.grid_1)
        Text(text = "Saba", style = AppTheme.typography.NetflixF26W500)
        VerticalSpacer(space = AppTheme.dimens.grid_1)
        Text(text = "Edit profile", style = AppTheme.typography.NetflixF18W500)
//        Button(onClick = {
//            Log.e(MyError, "Button clicked")
//            coroutineScope.launch(Dispatchers.IO) {
//                selectVideo(context)
//            }
//        }
//        ) {
//            Text(text = "Select Video")
//        }
    }

}

@OptIn(ExperimentalTime::class)
suspend fun selectVideo(context: Context) {
    val videoList = mutableListOf<Video>()

    val collection =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Video.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL
            )
        } else {
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        }

    val projection = arrayOf(
        MediaStore.Video.Media._ID,
        MediaStore.Video.Media.DISPLAY_NAME,
        MediaStore.Video.Media.DURATION,
        MediaStore.Video.Media.SIZE
    )

// Show only videos that are at least 5 minutes in duration.
    val selection = "${MediaStore.Video.Media.DURATION} <= ?"
    val selectionArgs = arrayOf(
        DurationUnit.MILLISECONDS.toTimeUnit().convert(5, DurationUnit.MINUTES.toTimeUnit()).toString()
    )

// Display videos in alphabetical order based on their display name.
    val sortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"

    val query = context.contentResolver.query(collection, projection, selection, selectionArgs, sortOrder)
    Log.d(TAG, "query value: ${query.toString()}")
    query?.use { cursor ->
        // Cache column indices.
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
        val nameColumn =
            cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
        val durationColumn =
            cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
        val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
        Log.d(TAG, "cursor move:${cursor.moveToNext()}")
        while (cursor.moveToNext()) {
            // Get values of columns for a given video.
            val id = cursor.getLong(idColumn)
            val name = cursor.getString(nameColumn)
            val duration = cursor.getInt(durationColumn)
            val size = cursor.getInt(sizeColumn)

            val contentUri: Uri = ContentUris.withAppendedId(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                id
            )

            // Stores column values and the contentUri in a local object
            // that represents the media file.
            videoList += Video(contentUri, name, duration, size)
            if (videoList.size == 0) {
                Log.d(TAG, "There is no video")
            }
        }
        for (video in videoList) {
            Log.d(TAG, "videoName: ${video.name}  videoDuration:${video.duration}")
        }
    }
}

@Composable
fun SettingsInput(labelText: String, inputValue: String, onValueChange: (String) -> Unit) {
    Text(
        text = labelText,
        style = AppTheme.typography.NetflixF16W400,
        color = AppTheme.colors.profileSoftTextColor
    )
    VerticalSpacer(space = AppTheme.dimens.grid_1)
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        //label = { Text(text = labelText) },
        //placeholder = { Text(text = "Name Field") },
        value = inputValue, onValueChange = { onValueChange(it) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = AppTheme.colors.profileTextColor,
            cursorColor = AppTheme.colors.profileTextColor
        )
    )
}

@Composable
fun MyAppBar(text: String, navigateBackToProfile: () -> Unit) {
    TopAppBar(
        backgroundColor = Color.Transparent,
        contentPadding = PaddingValues(horizontal = 16.dp),
        elevation = 0.dp
    ) {
//        BackHandler(enabled = false) {
//            Log.d(TAG, "The system back button has been tapped")
//            navigateBackToProfile.invoke()
//        }
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    navigateBackToProfile.invoke()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "",
                    tint = AppTheme.colors.profileHighLightColor
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = text,
                style = AppTheme.typography.NetflixF20W500,
                color = AppTheme.colors.profileTextColor,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { /*TODO*/ },
                enabled = false
            ) {

            }

//            Box(
//                modifier = Modifier.size(24.dp)
//            ) {
//
//            }
        }

    }
}

@Composable
fun Rationale(
    text: String,
    //onRequestPermission: () -> Unit,
    onCancel: () -> Unit,
    isOpen: Boolean,
    permissionFullDenied: Boolean = false
) {
    if (isOpen) {
        AlertDialog(
            onDismissRequest = { onCancel() },
            dismissButton = {
                Button(onClick = { onCancel() }) {
                    Text("Cancel")
                }
            },
            title = {
                Text(text = "Permission request")
            },
            text = {
                Text(text)
            },
            confirmButton = {
//                Button(onClick = { onRequestPermission.invoke() }) {
//                    Text(if (permissionFullDenied) "Go to Settings" else "ok")
//                }
            }
        )
    }
}