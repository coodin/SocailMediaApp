package com.example.presentation.ui.screens.ProfileScreen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.hardware.camera2.CameraCharacteristics
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.activity.compose.BackHandler
import androidx.camera.camera2.interop.Camera2CameraInfo
import androidx.camera.core.*
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import com.example.loginsignupcompose.R
import com.example.presentation.ui.screens.Components.VerticalSpacer
import com.example.presentation.ui.theme.AppTheme
import com.example.utility.*
import com.google.accompanist.permissions.*
import kotlinx.coroutines.launch
import java.io.File


@Composable
fun ProfileScreen(viewModel: ProfileViewModel, navigateProfileComposable: () -> Unit) {
    val focusManager = LocalFocusManager.current
    var isPasswordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val listState = rememberLazyListState()


    Scaffold() {
        Surface(modifier = Modifier.padding(AppTheme.dimens.grid_2)) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.grid_0_5), state = listState) {
                item { Profile(navigateProfileComposable) }
                items(listOf(1, 2, 3, 4, 5)) {
                    Status()
                }
            }
        }
    }
}

@Composable
fun Profile(navigateSettingsComposable: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Avatar(
            Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        )
        Column(modifier = Modifier.weight(3f), horizontalAlignment = Alignment.End) {
            Info()
            MessageAndFollow()
        }
        IconButton(
            modifier = Modifier
                .offset { IntOffset(x = 0, y = -10) }
                .align(Alignment.Top),
            onClick = {
                navigateSettingsComposable.invoke()
            },
        ) {
            Icon(
                imageVector = Icons.Rounded.Settings,
                tint = AppTheme.colors.profileSoftTextColor,
                contentDescription = ""
            )
        }
    }
    Description(modifier = Modifier.padding(top = AppTheme.dimens.grid_0_5))
}


@Composable
fun Avatar(modifier: Modifier) {
    Surface(modifier = modifier) {
        Image(
            modifier = Modifier
                .size(width = 80.dp, height = 92.dp)
                .clip(RectangleShape),
            painter = painterResource(id = R.drawable.profile_picture),
            contentScale = ContentScale.Fit,
            contentDescription = "Profile Picture"
        )
    }
}


@Composable
fun Info() {
    Row(
        //modifier = Modifier.padding(start = AppTheme.dimens.grid_3_5),
        //.padding(start = AppTheme.dimens.grid_3_5),
    )
    {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = "142",
                style = AppTheme.typography.NetflixF18W500
            )
            Text(
                text = "posts",
                style = AppTheme.typography.NetflixF14W400,
                color = AppTheme.colors.profileSoftTextColor
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(text = "7.4M", style = AppTheme.typography.NetflixF18W500)
            Text(
                text = "Followers", style = AppTheme.typography.NetflixF14W400,
                color = AppTheme.colors.profileSoftTextColor
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(text = "117", style = AppTheme.typography.NetflixF18W500)
            Text(
                text = "Following", style = AppTheme.typography.NetflixF14W400,
                color = AppTheme.colors.profileSoftTextColor
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageAndFollow() {
    Row(
        modifier = Modifier
            .padding(
                top = AppTheme.dimens.grid_1
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier.width(width = 134.dp),
            colors = ButtonDefaults
                .buttonColors(
                    backgroundColor = AppTheme.colors.profileHighLightColor,
                    contentColor = AppTheme.colors.onProfileButtonColor
                ),
            shape = RoundedCornerShape(size = 40.dp),
            onClick = {
                // navigateProfileComposable.invoke()
            }) {
            Text(text = "Message", style = AppTheme.typography.NetflixF16W500)
        }

        // TODO change the IconButton to IconToggleButton to alter specified icon
        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            Surface(
                modifier = Modifier
                    .padding(start = AppTheme.dimens.grid_1_5)
                    .height(24.dp)
                    .width(50.dp),
                //border = BorderStroke(1.dp, color = Color("#F2F2F2".toColorInt())),
                shape = RoundedCornerShape(20.dp),
                onClick = { /*TODO*/ }
            ) {
                Image(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_follow),
                    contentDescription = "",
                    alignment = Alignment.Center
                )
            }
//            IconButton(
//                modifier = Modifier
//                    .padding(start = AppTheme.dimens.grid_1_5)
//                    .background(Color.Black)
//                    .then(
//                        Modifier
//                            .height(24.dp)
//                            .width(50.dp)
//                    )
//                    .border(
//                        width = 1.dp, color = Color("#F2F2F2".toColorInt()),
//                        shape = RoundedCornerShape(40.dp)
//                    )
//                    .clip(RoundedCornerShape(40.dp)),
//                onClick = { /*TODO*/ }
//            ) {
//                Image(painter = painterResource(id = R.drawable.ic_follow), contentDescription = "")
//            }

        }
    }
}

@Composable
fun Description(modifier: Modifier) {
    Column(modifier) {
        Text(
            text = "Saba", style = AppTheme.typography.NetflixF20W500,
            color = AppTheme.colors.profileTextColor
        )
        Text(
            text = "Band/Music",
            style = AppTheme.typography.NetflixF16W400,
            color = AppTheme.colors.profileSoftTextColor
        )
        Text(
            text = "PIVOTGANG  \uD83C\uDFC0\n" +
                    "CARE FOR ME TOUR OUT NOW \uD83C\uDF99\n" +
                    "#CHI-TOWN\n" +
                    "This remind me of before we had insomnia\n" +
                    "Sleepin' peacefully, never needed a pile of drugs",
            modifier = Modifier.padding(top = AppTheme.dimens.grid_1),
            style = AppTheme.typography.NetflixF14W400,
            lineHeight = 1.5.em,
            color = AppTheme.colors.profileTextColor,

            )
        Text(
            text = "pivotgang.com",
            modifier = Modifier.padding(top = AppTheme.dimens.grid_1),
            style = AppTheme.typography.NetflixF14W500,
            color = AppTheme.colors.profileHighLightColor
        )
    }

}

@Composable
fun Status() {
    Surface(
        modifier = Modifier
            .padding(top = AppTheme.dimens.grid_4),
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier
                .padding(AppTheme.dimens.grid_2)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.ellipse),
                    contentDescription = "Status Picture"
                )
                Column(modifier = Modifier.padding(start = AppTheme.dimens.grid_1_5)) {
                    Text(
                        text = "Imam Farrhouk",
                        style = AppTheme.typography.NetflixF18W500
                    )
                    Text(
                        text = "4 min ago",
                        style = AppTheme.typography.NetflixF14W400,
                        color = AppTheme.colors.profileSoftTextColor
                    )
                }
            }
            Text(
                modifier = Modifier.padding(vertical = AppTheme.dimens.grid_2),
                text = "We are facing a serious business dilemma," +
                        " with Facebook taking away a good chunk of traffic to news and content sites," +
                        " and ad blockers eating into what’s left of it while slashing ad revenues. ",
                style = AppTheme.typography.NetflixF18W400,
                lineHeight = 1.5.em,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Likes(Modifier.weight(1f))
                CommentAndShared(Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun Likes(modifier: Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        val sizeOfImage = 32.dp
        Box {
            Image(
                modifier = Modifier
                    .size(sizeOfImage)
                    .border(
                        BorderStroke(2.dp, color = AppTheme.colors.addFriendBackgroundColor),
                        shape = RoundedCornerShape(50)
                    )
                    .clip
                        (CircleShape),
                painter = painterResource(id = R.drawable.likes_one), contentDescription = "likes"
            )
        }
        Box(modifier = Modifier.offset { IntOffset(x = -36, y = 0) }) {
            Image(
                modifier = Modifier
                    .size(sizeOfImage)
                    .border(
                        BorderStroke(2.dp, color = AppTheme.colors.addFriendBackgroundColor),
                        shape = RoundedCornerShape(50)
                    )
                    .clip
                        (CircleShape),
                painter = painterResource(id = R.drawable.likes_two), contentDescription = "likes"
            )
        }
        Box(modifier = Modifier.offset { IntOffset(x = -72, y = 0) }) {
            Image(
                modifier = Modifier
                    .size(sizeOfImage)
                    .border(
                        BorderStroke(2.dp, color = AppTheme.colors.addFriendBackgroundColor),
                        shape = RoundedCornerShape(50)
                    )
                    .clip
                        (CircleShape),
                painter = painterResource(id = R.drawable.likes_three), contentDescription = "likes"
            )
        }
        Text(
            modifier = Modifier.offset { IntOffset(x = -36, y = 0) },
            color = AppTheme.colors.profileSoftTextColor,
            text = "47 likes", style = AppTheme.typography.NetflixF14W400
        )
    }
}

@Composable
fun CommentAndShared(modifier: Modifier) {
    Row(modifier = modifier) {
        Text(
            text = "10 Comments",
            style = AppTheme.typography.NetflixF14W400,
            color = AppTheme.colors.profileSoftTextColor
        )
        Text(
            modifier = Modifier.padding(start = AppTheme.dimens.grid_1_5),
            text = "2 shared",
            style = AppTheme.typography.NetflixF14W400,
            color = AppTheme.colors.profileSoftTextColor
        )
    }
}


@Composable
fun ProfileSettings(viewModel: ProfileViewModel) {
    val focusManager = LocalFocusManager.current
    var isPasswordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        VerticalSpacer(space = AppTheme.dimens.grid_4)
        ImagePickerView(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            lastSelectedImage = viewModel.pickedImage,
            onSelection = {
                viewModel.pickedImage = it
            }
        )
        VerticalSpacer(space = AppTheme.dimens.grid_4)
        AppTextField(
            text = viewModel.firstName,
            placeholder = "First Name",
            onChange = { text ->
                viewModel.firstName = text
            },
            imeAction = ImeAction.Next,//Show next as IME button
            keyboardType = KeyboardType.Text,
            keyBoardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )

        VerticalSpacer(space = AppTheme.dimens.grid_2)

        AppTextField(
            text = viewModel.password,
            placeholder = "Password",
            onChange = { text ->
                viewModel.password = text
            },
            leadingIcon = {
                IconButton(onClick = {
                    isPasswordVisible = !isPasswordVisible
                }) {
                    Icon(
                        imageVector = if (isPasswordVisible)
                            Icons.Filled.Visibility
                        else
                            Icons.Filled.VisibilityOff,
                        contentDescription = "Password Visibility"
                    )
                }
            },
            imeAction = ImeAction.Next,//Show next as IME button
            keyboardType = KeyboardType.Password,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyBoardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )

        VerticalSpacer(space = AppTheme.dimens.grid_2)

        AppTextField(
            modifier = Modifier.padding(vertical = 8.dp),
            leadingIcon = {
                CountryPickerView(
                    countries = viewModel.countriesList,
                    selectedCountry = viewModel.mobileCountry,
                    onSelection = { selectedCountry ->
                        viewModel.mobileCountry = selectedCountry
                        viewModel.mobileCountryCode = selectedCountry.code
                    },
                )
            },
            text = viewModel.mobileNumber,
            onChange = { number ->
                viewModel.mobileNumber = number
            },
            placeholder = "Mobile Number",
            imeAction = ImeAction.Next,//Show next as IME button
            keyboardType = KeyboardType.Phone,
            keyBoardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )

        VerticalSpacer(space = AppTheme.dimens.grid_2)

        AppTextField(
            modifier = Modifier.clickable {
                viewModel.showDatePickerDialog(context)
            },
            text = viewModel.dateOfBirth,
            placeholder = "Birthdate",
            onChange = {
                viewModel.dateOfBirth = it
            },
            isEnabled = false
        )
    }
}


@Composable
fun CountryPickerView(
    selectedCountry: Country,
    onSelection: (Country) -> Unit,
    countries: List<Country>
) {
    var showDialog by remember { mutableStateOf(false) }
    Text(
        modifier = Modifier
            .clickable {
                showDialog = true
            }
            .padding(start = 20.dp, end = 5.dp),
        text = "${getFlagEmojiFor(selectedCountry.nameCode)} +${selectedCountry.code}",
        fontSize = 18.sp
    )

    if (showDialog)
        CountryCodePickerDialog(countries, onSelection) {
            showDialog = false
        }
}


@Composable
fun CountryCodePickerDialog(
    countries: List<Country>,
    onSelection: (Country) -> Unit,
    dismiss: () -> Unit,
) {
    Dialog(onDismissRequest = dismiss) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 40.dp)
                    .background(shape = RoundedCornerShape(20.dp), color = Color.White)
            ) {
                for (country in countries) {
                    item {
                        Text(
                            modifier = Modifier
                                .clickable {
                                    onSelection(country)
                                    dismiss()
                                }
                                .fillMaxWidth()
                                .padding(10.dp),
                            text = "${getFlagEmojiFor(country.nameCode)} ${country.fullName}"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    onChange: (String) -> Unit = {},
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyBoardActions: KeyboardActions = KeyboardActions(),
    isEnabled: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = onChange,
        leadingIcon = leadingIcon,
        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
        keyboardActions = keyBoardActions,
        visualTransformation = visualTransformation,
        enabled = isEnabled,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Gray,
            disabledBorderColor = Color.Gray,
            disabledTextColor = Color.Black
        ),
        placeholder = {
            Text(text = placeholder, style = TextStyle(fontSize = 18.sp, color = Color.LightGray))
        }
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ImagePickerView(
    modifier: Modifier = Modifier,
    lastSelectedImage: Bitmap?,
    onSelection: (Bitmap?) -> Unit
) {
//    val painter = rememberAsyncImagePainter(lastSelectedImage)
//    val galleryLauncher = rememberLauncherForActivityResult(
//        ActivityResultContracts.GetContent()
//    ) {
//        onSelection(it)
//    }

    val context = LocalContext.current
//    Permission(
//        permission = Manifest.permission.CAMERA,
//        permissionNotAvailableContent = {
//            Column(modifier = Modifier.fillMaxSize()) {
//                Text(
//                    "Camera permission required for this feature to be available. \" +\n" +
//                            "                            \"Please grant the permission!"
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                Button(onClick = {
//                    context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
//                        data = Uri.fromParts("package", context.packageName, null)
//                    })
//                }) {
//                    Text("Open Settings")
//                }
//            }
//        },
//    )


//val context = LocalContext.current
//    val cameraPermission = rememberPermissionState(Manifest.permission.CAMERA)
//    var isPermissionRequested by rememberSaveable { mutableStateOf(false) }
//
//
//    val cameraLauncher =
//        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
//            onSelection(it.takeIf { it != null })
//        }
//
//    if (isPermissionRequested && cameraPermission.status.isGranted) {
//        cameraLauncher.launch()
//        isPermissionRequested = false
//    }
//
//
//    Image(
//        modifier = modifier
//            .size(100.dp)
//            .clip(CircleShape)
//            .background(Color.LightGray)
//            .clickable {
//                if (!cameraPermission.status.isGranted) {
//                    cameraPermission.launchPermissionRequest()
//                    isPermissionRequested = true
//                } else
//                    cameraLauncher.launch()
//            },
//        painter = rememberAsyncImagePainter(lastSelectedImage),
//        contentDescription = "Profile Picture",
//        contentScale = ContentScale.Crop
//    )

//    Image(
//        painter = painter,
//        contentScale = ContentScale.Crop,
//        contentDescription = "Profile Picture",
//        modifier = modifier
//            .size(100.dp)
//            .clip(CircleShape)
//            .background(Color.LightGray)
//            .clickable {
//                galleryLauncher.launch("image/*")
//            },
//    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Permission(
    modifier: Modifier = Modifier,
    permissions: List<String> = listOf(Manifest.permission.CAMERA),
    rationale: String = "This permission is important for this app. Please grant the permission.",
    isOpen: Boolean,
    onActivateAlert: (Boolean) -> Unit,
    onImageFile: (File) -> Unit = { }
) {
//    var isCameraOpen by rememberSaveable() {
//        mutableStateOf(true)
//    }
    val context = LocalContext.current
    val sendToSettings = {
        context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        })
    }
//    val permissionsState = rememberMultiplePermissionsState(permissions)
//    permissionsState.permissions.forEach { permissionState ->
//        when (permissionState.permission) {
//            Manifest.permission.CAMERA -> {
//                when {
//                    permissionState.status.isGranted -> {
//                        //Text(text = "Camera permission accepted")
//                        //onActivateAlert.invoke(false)
//                        return@forEach
//                    }
//                    permissionState.status.shouldShowRationale -> {
//                        Rationale(
//                            text = "This permission enable to accept to camera to take picture",
//                            onRequestPermission = {
//                                permissionsState.launchMultiplePermissionRequest()
//                            },
//                            isOpen = isOpen,
//                            onCancel = { onActivateAlert.invoke(false) }
//                        )
//                    }
//                    permissionState.status.isPermanentlyDenied() -> {
//                        Rationale(
//                            text = "Camera permission was permanently" +
//                                    "denied. You can enable it in the app" +
//                                    "settings.",
//                            onRequestPermission = {
//                                sendToSettings.invoke()
//                            },
//                            onCancel = { onActivateAlert.invoke(false) },
//                            isOpen = isOpen,
//                            permissionFullDenied = true
//                        )
////                        Text(
////                            text = "Camera permission was permanently" +
////                                    "denied. You can enable it in the app" +
////                                    "settings."
////                        )
//                    }
//                }
//            }
//            Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
//                when {
//                    permissionState.status.isGranted -> {
//                        //Text(text = "Camera permission accepted")
//                        //onActivateAlert.invoke(false)
//                        return@forEach
//                    }
//                    permissionState.status.shouldShowRationale -> {
//                        Rationale(
//                            "This permission is needed to save  pictures you took to external storage ",
//                            onRequestPermission = {
//                                permissionsState.launchMultiplePermissionRequest()
//                            },
//                            isOpen = isOpen,
//                            onCancel = { onActivateAlert.invoke(false) }
//                        )
//                    }
//                    permissionState.status.isPermanentlyDenied() -> {
//                        Rationale(
//                            text = "Write External permission was permanently" +
//                                    "denied. You can enable it in the app" +
//                                    "settings.",
//                            onRequestPermission = { sendToSettings.invoke() },
//                            onCancel = { onActivateAlert.invoke(false) },
//                            isOpen = isOpen,
//                            permissionFullDenied = true
//                        )
////                        Text(
////                            text = "Camera permission was permanently" +
////                                    "denied. You can enable it in the app" +
////                                    "settings."
////                        )
//                    }
//                }
//            }
//        }
//    }
//    if (permissionsState.allPermissionsGranted) {
//        if (isOpen) {
//            CameraCapture(
//                //isOpen = isOpen,
//                closeCameraHandler = onActivateAlert,
//                modifier = Modifier,
//                onImageFile = onImageFile
//            )
//        }
//    }
//        permissionsState.shouldShowRationale
//    if (permissionsState.shouldShowRationale) {
//        Rationale(
//            rationale,
//            onRequestPermission = {
//                permissionsState.launchMultiplePermissionRequest()
//            },
//            isOpen = isOpen,
//            onCancel = { onActivateAlert.invoke(false) }
//        )
//    }
//    if (permissionsState.allPermissionsGranted) {
//        if (isOpen) {
//            CameraCapture(
//                //isOpen = isOpen,
//                closeCameraHandler = onActivateAlert,
//                modifier = Modifier,
//                onImageFile = onImageFile
//            )
//        }
//    } else {
//        Rationale(
//            "Camera permission required for this feature to be available.Please grant the permission",
//            onRequestPermission = {
//                Log.d(TAG,"Pressed")
//                permissionsState.launchMultiplePermissionRequest()
//            },
//            isOpen = isOpen,
//            onCancel = { onActivateAlert.invoke(false) },
//        )
//    }

//    val permissionState = rememberPermissionState(permission = )
//    when{
//        permissionState.status.isGranted -> {
//            content()
//        }
//        permissionState.status.shouldShowRationale -> {
//            Rationale(
//                rationale,
//                onRequestPermission = { permissionState.launchPermissionRequest() }
//            )
//        }
//        !permissionState.status.isGranted && !permissionState.status.shouldShowRationale -> {
//            Text(text = "Permission fully denied. Go to settings to enable")
//        }
//    }
//    when (permissionState.status) {
//        // If the camera permission is granted, then show screen with the feature enabled
//        is PermissionStatus.Granted -> {
//            // Text("Camera permission Granted")
//            // content()
//            //onDrawableChange(R.drawable.ellipse)
//            if (isOpen) {
//                CameraCapture(
//                    //isOpen = isOpen,
//                    closeCameraHandler = onActivateAlert,
//                    modifier = Modifier,
//                    onImageFile = onImageFile
//                )
//            }
//        }
//        is PermissionStatus.Denied -> {
//            if ((permissionState.status as PermissionStatus.Denied).shouldShowRationale) {
//                // If the user has denied the permission but the rationale can be shown,
//                // then gently explain why the app requires this permission
//                Rationale(
//                    rationale,
//                    onRequestPermission = {
//                        permissionState.launchMultiplePermissionRequest()
//                    },
//                    isOpen = isOpen,
//                    onCancel = { onActivateAlert.invoke(false) }
//                )
//            } else {
//                // If it's the first time the user lands on this feature, or the user
//                // doesn't want to be asked again for this permission, explain that the
//                // permission is required
//                // permissionNotAvailableContent()
//                Rationale(
//                    "Camera permission required for this feature to be available.Please grant the permission",
//                    onRequestPermission = {
//                        permissionState.launchMultiplePermissionRequest()
//                    },
//                    isOpen = isOpen,
//                    onCancel = { onActivateAlert.invoke(false) },
//                )
//            }
//        }
//    }
}


@Composable
fun CameraCapture(
    // isOpen: Boolean,
    closeCameraHandler: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    onImageFile: (Uri?) -> Unit = { }
    //onChangeImageCapture: (ImageCapture) -> Unit,
    //imageCapture: ImageCapture,
    //cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    var previewUseCase by remember { mutableStateOf<UseCase>(Preview.Builder().build()) }
    var cameraProvider by remember {
        mutableStateOf<ProcessCameraProvider?>(null)
    }
    val imageCaptureUseCase by remember {
        mutableStateOf(
            ImageCapture.Builder()
                .setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY)
                .build()
        )
    }
    // if (isOpen) {
    LaunchedEffect(previewUseCase) {
        cameraProvider = context.getCameraProvider()
        try {
            // Must unbind the use-cases before rebinding them.
            cameraProvider?.unbindAll()
            cameraProvider?.bindToLifecycle(
                lifecycleOwner, cameraSelector, previewUseCase, imageCaptureUseCase
            )
        } catch (ex: Exception) {
            Log.e("CameraCapture", "Failed to bind camera use cases", ex)
        }
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            CameraPreview(
                closeCameraHandler,
                onUseCase = {
                    previewUseCase = it
                },
                cameraProvider = cameraProvider
            )
            CapturePictureButton(
                onClick = {
                    coroutineScope.launch {
                        onImageFile.invoke(imageCaptureUseCase.takePicture(context.executor, context))
                        cameraProvider?.unbindAll()
                        closeCameraHandler.invoke(false)
                    }
                },
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.BottomCenter),
                content = { Text(text = "Take Photo") }
            )
        }
    }
    // }
}

@Composable
fun CapturePictureButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val color = if (isPressed) Color.Blue else Color.Black
    val contentPadding = PaddingValues(if (isPressed) 8.dp else 12.dp)
    OutlinedButton(
        modifier = modifier,
        shape = CircleShape,
        border = BorderStroke(2.dp, Color.Black),
        contentPadding = contentPadding,
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
        onClick = onClick,
        enabled = false
    ) {
        Button(
            modifier = Modifier
                .fillMaxSize(),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = color
            ),
            interactionSource = interactionSource,
            onClick = onClick
        ) {
            content.invoke()
        }
    }
}

@Composable
fun CameraPreview(
    closeCameraHandler: (Boolean) -> Unit,
    onUseCase: (UseCase) -> Unit = { },
    cameraProvider: ProcessCameraProvider? = null
) {
    //val context = LocalContext.current
    //val outputFileOptions = ImageCapture.OutputFileOptions.Builder(File()).build()
//    var imageCapture: ImageCapture? by remember {
//        mutableStateOf(null)
//    }
    //val lifecycle = LocalLifecycleOwner.current
//    var cameraProvider: ProcessCameraProvider? by remember {
//        mutableStateOf(null)
//    }
    //val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
//
    BackHandler {
        cameraProvider?.unbindAll()
        closeCameraHandler.invoke(false)
    }
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            PreviewView(it).apply {
                setBackgroundColor(Color.Black.toArgb())
                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                scaleType = PreviewView.ScaleType.FILL_CENTER
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }
        },
        update = { previewView ->
            onUseCase(
                Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
            )
        })

}

@SuppressLint("UnsafeOptInUsageError")
private fun selectExternalOrBestCamera(provider: ProcessCameraProvider): CameraSelector? {
    val cam2Infos = provider.availableCameraInfos.map {
        Camera2CameraInfo.from(it)
    }.sortedByDescending {
        // HARDWARE_LEVEL is Int type, with the order of:
        // LEGACY < LIMITED < FULL < LEVEL_3 < EXTERNAL
        it.getCameraCharacteristic(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)
    }

    return when {
        cam2Infos.isNotEmpty() -> {
            Log.d(TAG, "The camera has been restricted")
            CameraSelector.Builder()
                .addCameraFilter {
                    it.filter { camInfo ->
                        // cam2Infos[0] is either EXTERNAL or best built-in camera
                        val thisCamId = Camera2CameraInfo.from(camInfo).cameraId
                        thisCamId == cam2Infos[0].cameraId
                    }
                }.build()
        }
        else -> null
    }
}