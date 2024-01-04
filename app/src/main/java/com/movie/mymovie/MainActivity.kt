package com.movie.mymovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.gson.Gson
import com.movie.mymovie.entity.UserEntity
import com.movie.mymovie.http.RequestUtils
import com.movie.mymovie.http.ResultEntity
import com.movie.mymovie.pages.MovieHomePage
import com.movie.mymovie.pages.MoviePage
import com.movie.mymovie.pages.MyPage
import com.movie.mymovie.pages.TVPage
import com.movie.mymovie.ui.theme.Color;
import com.movie.mymovie.ui.theme.MymovieTheme
import com.movie.mymovie.ui.theme.Size;
import com.movie.mymovie.utils.SharedPreferencesUtils
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    val isInit: MutableState<Boolean> = mutableStateOf(false)

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getUserData()
        setContent {
            if(isInit.value){
                MymovieTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        var bottomSelectedState by remember { mutableStateOf(0) }
                        Column {
                            //根据底部导航选中的下标改变展示的页面
                            val pagerState = rememberPagerState(
                                pageCount = 4,
                                initialPage = bottomSelectedState,
                                initialOffscreenLimit = 3
                            )
                            HorizontalPager(
                                state = pagerState,
                                dragEnabled = false,
                                modifier = Modifier.weight(1f).background(Color.colorBg)
                            ) { page ->
                                when (page) {
                                    0 -> MovieHomePage()
                                    1 -> MoviePage()
                                    2 -> TVPage()
                                    3 -> MyPage()
                                }
                            }
                            BottomBarWidget(bottomSelectedState, mBottomTabItems,pagerState) {
                                bottomSelectedState = it
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @author: wuwenqiang
     * @description: 获取用户信息
     * @date: 2021-12-04 15:59
     */
    private fun getUserData() {
        val userData: Call<ResultEntity> = RequestUtils.instance.userData
        userData.enqueue(object : Callback<ResultEntity> {
            override fun onResponse(call: Call<ResultEntity>, response: Response<ResultEntity>) {
                val gson = Gson()
                val body: ResultEntity? = response.body()
                if (body != null) {
                    BaseApplication.getInstance().token = body.token
                }
                if (body != null) {
                    BaseApplication.getInstance().userEntity=
                        gson.fromJson(
                            gson.toJson(body.data),
                            UserEntity::class.java
                        )
                    SharedPreferencesUtils.setParam(
                        this@MainActivity,
                        "token",
                        body.token
                    )
                }
                isInit.value = true;
            }

            override fun onFailure(call: Call<ResultEntity>, t: Throwable) {
                println("错误")
            }
        })
    }
}

val mBottomTabItems =
    listOf(
        BottomItem("首页", R.mipmap.icon_home_active, R.mipmap.icon_home),
        BottomItem("电影", R.mipmap.icon_movie_active, R.mipmap.icon_movie),
        BottomItem("电视剧", R.mipmap.icon_tv_active, R.mipmap.icon_tv),
        BottomItem("我的", R.mipmap.icon_user_active, R.mipmap.icon_user)
    )

@ExperimentalPagerApi
@Composable
fun BottomBarWidget(
    selectedPosition: Int,
    bottomItems: List<BottomItem>,
    pagerState: PagerState,
    onItemSelected: (position: Int) -> Unit,
) {
    val scope = rememberCoroutineScope()
    BottomNavigation(
        backgroundColor = Color.colorWhite,
    ) {
        bottomItems.forEachIndexed { index, item ->
            BottomNavigationItem(
                selected = selectedPosition == index,
                onClick = {
                    onItemSelected.invoke(index)
                    scope.launch {
                        pagerState.scrollToPage(index)
                    }
                },
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
                            .size(Size.middleIcon)
                            .padding(Size.miniMargin)
                    )
                },
                label = {
                    Text(
                        text = bottomItems[index].label,
                        fontWeight = FontWeight.Normal,
                        fontSize = Size.normalFontSize,
                        color = if (selectedPosition == index) {
                            Color.selectedColor
                        } else {
                            Color.normalColor
                        }
                    )
                },
            )
        }
    }
}

data class BottomItem(val label: String, val selectItemRes: Int, val unSelectItemRes: Int)
