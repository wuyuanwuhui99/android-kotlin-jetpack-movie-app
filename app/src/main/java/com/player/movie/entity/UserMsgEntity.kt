package com.player.movie.entity

class UserMsgEntity {
    var userAge: String = ""
    var viewRecordCount: String = ""
    var playRecordCount: String = ""
    var favoriteCount: String = ""

    fun setData(userMsgEntity: UserMsgEntity){
        userAge = userMsgEntity.userAge
        viewRecordCount = userMsgEntity.viewRecordCount
        playRecordCount = userMsgEntity.playRecordCount
        favoriteCount = userMsgEntity.favoriteCount
    }
}