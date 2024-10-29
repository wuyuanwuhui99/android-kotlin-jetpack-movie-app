package com.player.movie.screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.accompanist.flowlayout.FlowRow
import com.player.BaseApplication
import com.player.R
import com.player.model.UserViewModel
import com.player.movie.component.TitleComponent
import com.player.movie.database.AppSqliteDataBase
import com.player.movie.database.MovieSearchHistoryDatabase
import com.player.movie.entity.MovieSearchHistoryEntity
import com.player.movie.model.SearchHistoryViewModel
import com.player.theme.MymovieTheme
import com.player.theme.ThemeColor
import com.player.theme.ThemeSize
import com.player.theme.ThemeStyle

//val searchHistoryViewModel by viewModels<SearchHistoryViewModel>(factoryProducer = {
//    object : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            return SearchHistoryViewModel(
//                BaseApplication.SHDB
//            ) as T
//        }
//    }
//})

@Composable
fun MovieSearchScreen( navController: NavHostController,userViewModel: UserViewModel,keyword:String) {
    MymovieTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val inputValue = remember{ mutableStateOf(keyword) }
                val context = LocalContext.current
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = ThemeSize.containerPadding,
                            end = ThemeSize.containerPadding
                        )
                        .scrollable(
                            state = rememberScrollState(0),
                            orientation = Orientation.Vertical
                        )
                ) {
                    item {
                        SearchInput(inputValue)
                    }
                    item {
                        Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
                        SearchHistory(context)
                    }
                }

            }
        }
    }
}

@Composable
fun SearchInput(inputValue: MutableState<String>){
    Row(
        modifier = ThemeStyle.boxDecoration
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(1f)
                .height(ThemeSize.inputHeight)
                .padding(ThemeSize.smallMargin, 0.dp, 0.dp, 0.dp)
                .background(ThemeColor.colorBg, RoundedCornerShape(ThemeSize.middleAvater))
        ) {
            TextField(
                value = inputValue.value,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = ThemeColor.transparent,
                    focusedIndicatorColor=ThemeColor.transparent
                ),
                modifier = Modifier
                    .clip(RoundedCornerShape(ThemeSize.middleRadius))
                    .height(ThemeSize.inputHeight)
                    .weight(1f)
                    .border(
                        ThemeSize.borderWidth,
                        ThemeColor.colorBg,
                    )
                    .weight(1f),
                onValueChange = {
                    inputValue.value = it
                }
            )
            if(inputValue.value != ""){
                Image(
                    painter = painterResource(id = R.mipmap.icon_clear),
                    modifier = Modifier
                        .size(ThemeSize.middleIcon)
                        .clickable {
                            inputValue.value = ""
                        },
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
        }
        Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
        Button(
            shape = RoundedCornerShape(ThemeSize.superRadius),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ThemeColor.activeColor
            ),
            modifier = Modifier
                .height(ThemeSize.inputHeight)
                .width(ThemeSize.middleBtnWidth)
            ,
            onClick = {

            }) {
            Text(text = "搜索",style = TextStyle(color = ThemeColor.colorWhite))
        }
    }
}

@Composable
fun SearchHistory(context: Context){
    Column(
        modifier = ThemeStyle.boxDecoration
    ) {
        TitleComponent("搜索历史")
        Spacer(modifier = Modifier.height(ThemeSize.containerPadding))
        FlowRow(
            modifier = Modifier
                .fillMaxSize()
            ,
            mainAxisSpacing = ThemeSize.containerPadding,
            crossAxisSpacing = ThemeSize.containerPadding
        ) {
            LaunchedEffect(Unit){
                val database: AppSqliteDataBase = MovieSearchHistoryDatabase.init(context)
                val searchWordList: MutableList<MovieSearchHistoryEntity> = database.searchHistoryDao().getAllHistory()
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(ThemeSize.middleAvater)
                    .background(ThemeColor.colorBg, RoundedCornerShape(ThemeSize.middleAvater))
            ) {
                Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
                Text(
                    text = "毒液：最后一舞",
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(ThemeSize.middleAvater)
                    .background(ThemeColor.colorBg, RoundedCornerShape(ThemeSize.middleAvater))
            ) {
                Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
                Text(
                    text = "变形金刚：起源",
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(ThemeSize.middleAvater)
                    .background(ThemeColor.colorBg, RoundedCornerShape(ThemeSize.middleAvater))
            ) {
                Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
                Text(
                    text = "变形金刚：起源",
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.width(ThemeSize.containerPadding))
            }
        }
    }
}
