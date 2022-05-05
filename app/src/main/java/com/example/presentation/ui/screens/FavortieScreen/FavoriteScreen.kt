package com.example.presentation.ui.screens.FavortieScreen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.loginsignupcompose.R
import com.example.presentation.ui.theme.AppTheme
import com.example.utility.drawLine


@Composable
fun Favorite(favoriteViewModel: FavoriteViewModel, navigateChatScreen: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
    ) {
        items(listOf(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)) {
            MyListItem(navigateChatScreen)
        }
    }
}

@Composable
fun MyListItem(navigateChatScreen: () -> Unit) {
    var outerRow by remember {
        mutableStateOf<Int>(0)
    }
    var innerRow by remember {
        mutableStateOf<Int>(0)
    }

    val offsetY by derivedStateOf {
        (outerRow - innerRow) / 2
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { navigateChatScreen.invoke() }
            .height(70.dp)
            .fillMaxWidth()
            .onGloballyPositioned { layoutCoordinates ->
                outerRow = layoutCoordinates.size.height
            }
    ) {
        AvatarIcon(
            //Modifier.weight(1f)
            Modifier.padding(end = AppTheme.dimens.grid_1_5)
        )
        Row(
            modifier = Modifier
//                    .padding(bottom = 15.dp)
//                    .border(width = 5.dp, color = AppTheme.colors.profileTextColor)
                .fillMaxWidth()
                .onGloballyPositioned { layoutCoordinates ->
                    innerRow = layoutCoordinates.size.height
                }
                .drawLine(
                    color = AppTheme.colors.lineGrayColor,
                    strokeWidth = 5f,
                    offsetY = offsetY
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(3f)
            ) {
                Text(
                    text = "Jacques Webster",
                    style = AppTheme.typography.NetflixF18W500,
                    color = AppTheme.colors.profileTextColor
                )
                Text(
                    text = "Yeah I know",
                    style = AppTheme.typography.NetflixF14W400,
                    color = AppTheme.colors.profileSoftTextColor
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "11:47",
                    style = AppTheme.typography.NetflixF12W400,
                    color = AppTheme.colors.profileSoftTextColor
                )
                Surface(
                    modifier = Modifier.padding(top = AppTheme.dimens.grid_0_5),
                    shape = CircleShape,
                    color = AppTheme.colors.profileHighLightColor
                    //.widthIn(min = 14.dp)
                    //.padding(horizontal = AppTheme.dimens.grid_0_5)
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = "122",
                        textAlign = TextAlign.Center,
                        style = AppTheme.typography.NetflixF12W400,
                        color = AppTheme.colors.addFriendBackgroundColor
                    )
                }
            }
        }
    }
}

//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun MyListItem() {
//    ListItem(
//        icon = { AvatarIcon() },
//        text = {
//            Text(
//                text = "Jacques Webster",
//                style = AppTheme.typography.NetflixF18W500,
//                color = AppTheme.colors.profileTextColor
//            )
//        },
//        secondaryText = {
//            Text(
//                text = "Yeah I know",
//                style = AppTheme.typography.NetflixF14W400,
//                color = AppTheme.colors.profileSoftTextColor
//            )
//        },
////            overlineText = { Text(text = "Hello")},
//        trailing = {
//            Column(
//                horizontalAlignment = Alignment.End
//            ) {
//                Text(
//                    text = "11:47",
//                    style = AppTheme.typography.NetflixF12W400,
//                    color = AppTheme.colors.profileSoftTextColor
//                )
//                Surface(
//                    modifier = Modifier
//                        .padding(top = AppTheme.dimens.grid_0_5)
//                        .size(25.dp),
//                    shape = CircleShape,
//                    color = AppTheme.colors.profileHighLightColor
//                    //.widthIn(min = 14.dp)
//                    //.padding(horizontal = AppTheme.dimens.grid_0_5)
//                ) {
//                    Text(
//                        modifier = Modifier.wrapContentHeight(),
//                        text = "12",
//                        textAlign = TextAlign.Center,
//                        style = AppTheme.typography.NetflixF12W400,
//                        color = AppTheme.colors.addFriendBackgroundColor
//                    )
//                }
//            }
//
//        }
//    )
//
//}


@Composable
fun AvatarIcon(modifier: Modifier = Modifier) {
    Box(modifier) {
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .align(Alignment.Center),
            painter = painterResource(id = R.drawable.avatar),
            contentDescription = "Avatar Icon"
        )
    }

}