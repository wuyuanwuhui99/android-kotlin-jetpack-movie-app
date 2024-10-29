package com.player.movie.model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.player.movie.database.AppSqliteDataBase
import com.player.movie.entity.MovieSearchHistoryEntity
import com.player.movie.repository.SearchHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchHistoryViewModel(db: AppSqliteDataBase) : ViewModel() {
    private val TAG = "SearchHistoryViewModel"
    var searchHistoryRepo: SearchHistoryRepository = SearchHistoryRepository(db = db)
    var searchHistoryStateList = mutableStateListOf<SearchHistoryState>()

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadHistoryList() {
        Log.d(TAG, "loadHistoryList")

        viewModelScope.launch(Dispatchers.IO) {
            searchHistoryRepo.getSearchHistoryList().forEach { searchHistory: MovieSearchHistoryEntity ->
                Log.d(TAG,"loadHistoryList: $searchHistory")
                val searchHistoryState = SearchHistoryState(
                    history_id = searchHistory.id,
                    history_name = searchHistory.movieName,
                    history_classify = searchHistory.classify,
                    createTime = searchHistory.createTime,
                )

                searchHistoryStateList.add(searchHistoryState)
            }

        }
    }

    fun deleteHistory(searchHistoryState: SearchHistoryState) {
        Log.d(TAG, "deleteHistory: $searchHistoryState")
        viewModelScope.launch(Dispatchers.IO) {
            searchHistoryStateList.remove(searchHistoryState)
            searchHistoryStateList.sortBy { it.createTime }
            searchHistoryRepo.deleteById(searchHistoryState.history_id)
        }
    }

    fun addHistory(searchHistoryState: SearchHistoryState) {
        Log.d(TAG, "deleteHistory: $searchHistoryState")
        if(searchHistoryStateList.size == 10){
            searchHistoryStateList.removeLast()
        }

        viewModelScope.launch(Dispatchers.IO) {
            searchHistoryStateList.add(searchHistoryState)
            searchHistoryStateList.sortBy { it.createTime }
            val searchHistory = MovieSearchHistoryEntity(
                movieName = searchHistoryState.history_name,
                classify = searchHistoryState.history_classify,
                createTime = searchHistoryState.createTime
            )
            searchHistoryRepo.insertHistory(searchHistory)
        }
    }

    fun clearAllHistory() {
        Log.d(TAG, "clearAllHistory")
        searchHistoryStateList.clear()
        viewModelScope.launch {
            searchHistoryRepo.clearAllHistory()
        }
    }

    fun updateHistory(searchHistoryState: SearchHistoryState){
        viewModelScope.launch {
            val searchHistory = MovieSearchHistoryEntity(
                movieName = searchHistoryState.history_name,
                classify = searchHistoryState.history_classify,
                createTime = searchHistoryState.createTime
            )
            searchHistoryRepo.updateHistory(searchHistory)
        }
    }
}

@Stable
data class SearchHistoryState(
    var history_id: Long = -1L,
    var history_name: String = "",
    var history_classify:  String = "",
    var createTime: Long = System.currentTimeMillis(),
)