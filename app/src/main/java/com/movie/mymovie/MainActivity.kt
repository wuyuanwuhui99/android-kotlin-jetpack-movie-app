package com.movie.mymovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.movie.mymovie.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MymovieTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    var bottomSelectedState by remember { mutableStateOf(0) }
                    Scaffold(
                        modifier = Modifier.background(BackgroundColor),
                        bottomBar = {
                            BottomBarWidget(bottomSelectedState, mBottomTabItems) {
                                bottomSelectedState = it
                            }
                        }
                    ) {
                        //这里是主界面
                        //根据底部导航选中的下标改变展示的页面
                        when (bottomSelectedState) {
                            0 -> Text("这是首页")
                            1 -> Text("这是电影页")
                            2 -> Text("这是电视剧")
                            3 -> Text("这是我的")
                        }
                    }
                }
            }
        }
    }
}

val mBottomTabItems =
    listOf(
        BottomItem("首页", R.mipmap.icon_home_active, R.mipmap.icon_home),
        BottomItem("电影", R.mipmap.icon_movie_active, R.mipmap.icon_movie),
        BottomItem("电视剧", R.mipmap.icon_tv_active, R.mipmap.icon_tv),
        BottomItem("我的", R.mipmap.icon_user_active, R.mipmap.icon_user)
    )

@Composable
fun BottomBarWidget(
    selectedPosition: Int,
    bottomItems: List<BottomItem>,
    onItemSelected: (position: Int) -> Unit
) {
    BottomNavigation(
        backgroundColor = Color.White,
    ) {
        bottomItems.forEachIndexed { index, item ->
            BottomNavigationItem(
                selected = selectedPosition == index,
                onClick = { onItemSelected.invoke(index) },
                icon = {
                    Image(
                        painter = painterResource(
                            id = if (selectedPosition == index) {
                                item.selectItemRes
                            } else {
                                item.unSelectItemRes
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(36.dp)
                            .padding(5.dp)
                    )
                },
                label = {
                    Text(
                        text = bottomItems[index].label,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = if (selectedPosition == index) {
                            SelectedColor
                        } else {
                            UnSelectedColor
                        }
                    )
                },
            )
        }
    }
}

data class BottomItem(val label: String, val selectItemRes: Int, val unSelectItemRes: Int)
