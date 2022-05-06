package com.example.utility


import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.LifecycleOwner
import com.example.presentation.ui.screens.HomeScreen.HomeViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlinx.coroutines.InternalCoroutinesApi

@Composable
fun TransformableSample() {
    // set up all transformation states
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }
    Box(
        Modifier
            // apply other transformations like rotation and zoom
            // on the pizza slice emoji
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationZ = rotation,
                translationX = offset.x,
                translationY = offset.y
            )
            // add transformable to listen to multitouch transformation events
            // after offset
            .transformable(state = state)
            .background(Color.Blue)
            .fillMaxSize()
    )
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableSample() {
    val width = 96.dp
    val squareSize = 48.dp

    val swipeableState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states

    Box(
        modifier = Modifier
            .width(width)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
            .background(Color.LightGray)
    ) {
        Box(
            Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .size(squareSize)
                .background(Color.DarkGray)
        )
    }
}

@Composable
fun NestedScrolling() {
    val gradient = Brush.verticalGradient(0f to Color.Gray, 1000f to Color.White)
    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .verticalScroll(rememberScrollState())
            .padding(32.dp)
    ) {
        Column {
            repeat(6) {
                Box(
                    modifier = Modifier
                        .height(128.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        "Scroll here",
                        modifier = Modifier
                            .border(12.dp, Color.DarkGray)
                            .background(brush = gradient)
                            .padding(24.dp)
                            .height(150.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ScrollableSample() {
    Box(modifier = Modifier.fillMaxSize()) {
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        Box(
            Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .background(Color.Blue)
                .size(50.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
        )
    }
}

@Composable
fun PasswordTextField() {
    var password by rememberSaveable { mutableStateOf("") }

    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Enter password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@OptIn(ExperimentalFoundationApi::class, InternalCoroutinesApi::class)
@Composable
fun VerticalGrid(photos: List<String>) {
    val listState = rememberLazyGridState()
    // Remember a CoroutineScope to be able to launch
    val coroutineScope = rememberCoroutineScope()

    LazyVerticalGrid(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        columns = GridCells.Fixed(2)
    ) {
        items(photos) { photo ->
            UserCard(photo)
        }
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth()
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                ) {
                    Text(text = "Go to Up")
                }
            }
        }
    }
}

@Composable
fun UserCard(photo: String) {

    val annotatedText = buildAnnotatedString {

        withStyle(style = SpanStyle(color = Color.Gray, fontSize = 24.sp)) {
            append("Click ")
        }

        // We attach this *URL* annotation to the following content
        // until `pop()` is called
        pushStringAnnotation(
            tag = "URL",
            annotation = "https://developer.android.com"
        )
        withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
            append("here")
        }
        pop()
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(MaterialTheme.colors.secondary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ClickableText(
            text = annotatedText,
            onClick = { offset ->
                annotatedText.getStringAnnotations(tag = "URL", start = offset, end = offset)
                    .firstOrNull()?.let { annotatedText ->
                        Log.d(TAG, "${annotatedText.item} -th character is clicked.")
                    }
            }
        )
    }
}

@Composable
fun RippleEffect() {
    Surface(
        elevation = 50.dp,
        color = MaterialTheme.colors.surface, // color will be adjusted for elevation
    ) {
        CompositionLocalProvider(LocalRippleTheme provides SecondaryRippleTheme) {
            Button(
                onClick = { /* ... */ },
                // Custom colors for different states
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    disabledBackgroundColor = MaterialTheme.colors.onBackground
                        .copy(alpha = 0.2f)
                        .compositeOver(MaterialTheme.colors.background)
                    // Also contentColor and disabledContentColor
                ),
                // Custom elevation for different states
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 8.dp,
                    disabledElevation = 2.dp,
                    // Also pressedElevation
                )
            ) { Text(text = "Hello Everybody") }
        }
    }
}

@Immutable
private object SecondaryRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = RippleTheme.defaultRippleColor(
        contentColor = MaterialTheme.colors.secondary,
        lightTheme = MaterialTheme.colors.isLight
    )

    @Composable
    override fun rippleAlpha() = RippleTheme.defaultRippleAlpha(
        contentColor = MaterialTheme.colors.secondary,
        lightTheme = MaterialTheme.colors.isLight
    )
}

@Composable
fun TwoTexts(
    text1: String = "hello",
    text2: String = "Ogün",
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start),
            text = text1
        )
        Divider(
            color = Color.Black,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End),
            text = text2
        )
    }
}


@Composable
fun WithConstraintsComposable() {
    BoxWithConstraints {
        Text("My minHeight is $minHeight while my maxWidth is $maxWidth")
    }
}

@Composable
fun MoveBoxWhereTapped() {
    // Creates an `Animatable` to animate Offset and `remember` it.
    val animatedOffset = remember {
        Animatable(Offset(0f, 0f), Offset.VectorConverter)
    }

    Box(
        // The pointerInput modifier takes a suspend block of code
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                // Create a new CoroutineScope to be able to create new
                // coroutines inside a suspend function
                coroutineScope {
                    while (true) {
                        // Wait for the user to tap on the screen
                        val offset = awaitPointerEventScope {
                            awaitFirstDown().position
                        }
                        // Launch a new coroutine to asynchronously animate to where
                        // the user tapped on the screen
                        launch {
                            // Animate to the pressed position
                            animatedOffset.animateTo(offset)
                        }
                    }
                }
            }
    ) {
        Text("Tap anywhere", Modifier.align(Alignment.Center))
        Box(
            Modifier
                .offset {
                    // Use the animated offset as the offset of this Box
                    IntOffset(
                        animatedOffset.value.x.roundToInt(),
                        animatedOffset.value.y.roundToInt()
                    )
                }
                .size(40.dp)
                .background(Color(0xff3c1361), CircleShape)
        )
    }
}

@Composable
fun backPressHandler(onBackPressed: () -> Unit, enabled: Boolean = true) {
    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher


    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        }
    }

    DisposableEffect(dispatcher) { // dispose/relaunch if dispatcher changes
        dispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove() // avoid leaks!
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BackdropScaffoldComposable() {
    val scaffoldState = rememberBackdropScaffoldState(
        BackdropValue.Concealed
    )
    val scope = rememberCoroutineScope()
    BackdropScaffold(
        scaffoldState = scaffoldState,
        appBar = {
            TopAppBar(
                title = { Text("Backdrop") },
                navigationIcon = {
                    if (scaffoldState.isConcealed) {
                        IconButton(
                            onClick = {
                                Log.d(TAG, "Menu item has been pressed")
                                scope.launch { scaffoldState.reveal() }
                            }
                        ) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {
                                scope.launch { scaffoldState.conceal() }
                            }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close"
                            )
                        }
                    }
                },
                elevation = 0.dp,
                backgroundColor = Color.Transparent
            )
        },
        backLayerContent = {
            repeat(10) {
                Text(text = "Back Layer Content")
            }
        },
        frontLayerContent = {
            Text(text = "Front Layer Content")
        },
        // Defaults to BackdropScaffoldDefaults.PeekHeight
        peekHeight = 40.dp,
        // Defaults to BackdropScaffoldDefaults.HeaderHeight
        headerHeight = 60.dp,
        // Defaults to true
        gesturesEnabled = false
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetComposable() {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Text(text = "Hello")
            Text(text = "everybody")
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Expand or collapse sheet") },
                onClick = {
                    scope.launch {
                        scaffoldState.bottomSheetState.apply {
                            if (isCollapsed) expand() else collapse()
                        }
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column() {
                Text(text = "Bottom")
                Text(text = "Sheet")
            }
        }
    }
}


@Composable
fun Greeting(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: HomeViewModel,
    navigateSecondScreen: () -> Unit,
) {
    // Creates a CoroutineScope bound to the MoviesScreen's lifecycle
    val scope = rememberCoroutineScope()
    //val scrollState = rememberScrollState()

    // If `lifecycleOwner` changes, dispose and reset the effect
//    DisposableEffect(lifecycleOwner) {
//        // Create an observer that triggers our remembered callbacks
//        // for sending analytics events
//        val observer = LifecycleEventObserver { _, event ->
//            if (event == Lifecycle.Event.ON_STOP) {
//                viewModel.onStop()
//            } else if (event == Lifecycle.Event.ON_START) {
//                viewModel.onStart()
//            }
//        }
//
//        // Add the observer to the lifecycle
//        lifecycleOwner.lifecycle.addObserver(observer)
//
//        // When the effect leaves the Composition, remove the observer
//        onDispose {
//            Log.d(TAG, "Effect left from the  composition ")
//            lifecycleOwner.lifecycle.removeObserver(observer)
//        }
//    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            Text("Drawer title", modifier = Modifier.padding(16.dp))
            Divider()
            // Drawer items
        },
        topBar = {
//            TopAppBar {
//                IconButton(onClick = {
//                    scope.launch {
//                        scaffoldState.drawerState.apply {
//                            if (isClosed) open() else close()
//                        }
//                    }
//                }) {
//                    Icon(
//                        imageVector = Icons.Default.Menu,
//                        contentDescription = "Home button",
//                        tint = MaterialTheme.colors.onSurface
//                    )
//                }
//            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                scope.launch {
                    navigateSecondScreen()
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription =
                    "Favorite Icon"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
            ) {
                when (val dataSet = viewModel.userState) {
                    is State.Failed -> {
                        Text(
                            dataSet.message,
                        )
                    }
                    is State.Success -> {
                        val dataset = dataSet.data
                        LazyColumn(
                            modifier = Modifier
                                .wrapContentWidth()
                                .background(Color.DarkGray)
                        ) {
                            item{
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    CompositionLocalProvider(
                                        LocalContentColor provides Color.White,
                                        LocalTextStyle provides MaterialTheme.typography.body1
                                    ) {
                                        Text(dataset?.email ?: "")
                                        //Text(it?.country ?: "")
                                        //Text("${it?.population}")
                                    }
                                }
                            }
                        }
                    }
                    else -> {}
                }
            }
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .background(MaterialTheme.colors.background)
                    .align(Alignment.BottomCenter)
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = { /*viewModel.addCity()*/ },
                        colors = ButtonDefaults
                            .buttonColors(
                                contentColor = Color.White,
                                backgroundColor = MaterialTheme.colors.secondary
                            )
                    ) {
                        Text(text = "Add City")
                    }
                    Button(
                        onClick = { /*viewModel.updateCity()*/ },
                        colors = ButtonDefaults
                            .buttonColors(
                                contentColor = Color.White,
                                backgroundColor = MaterialTheme.colors.secondary
                            )
                    ) {
                        Text(text = "Update City")
                    }
                    Button(
                        onClick = { /*viewModel.updateTimeStamp() */ }, colors = ButtonDefaults
                            .buttonColors(
                                contentColor = Color.White,
                                backgroundColor = MaterialTheme.colors.secondary
                            )
                    ) {
                        Text(
                            text = "Update TimeStamp",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = { /*viewModel.deleteDocument()*/ }, colors = ButtonDefaults
                            .buttonColors(
                                contentColor = Color.White,
                                backgroundColor = MaterialTheme.colors.secondary
                            )
                    ) {
                        Text(
                            text = "Delete Document",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Button(
                        onClick = {
                            // viewModel.getDocument()
                        }, colors =
                        ButtonDefaults
                            .buttonColors(
                                contentColor = Color.White,
                                backgroundColor = MaterialTheme.colors.secondary
                            )
                    ) {
                        Text(
                            text = "Get Document",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    when (val documentState = viewModel.documentState) {
                        is State.Success -> {
                            Text(text = documentState.data?.name ?: "")
                            Text(text = documentState.data?.country ?: "")
                            Text(text = documentState.data?.population.toString())
                        }
                        is State.Loading -> {
                            CircularProgressIndicator()
                        }
                        is State.Failed -> {
                            Text(documentState.message)
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}