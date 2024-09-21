package com.player.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.player.movie.entity.UserEntity

class UserViewModel : ViewModel() {
    val userId  = mutableStateOf("吴时吴刻")
    val createDate  = mutableStateOf("")
    val updateDate  = mutableStateOf("")
    val username  = mutableStateOf("")
    val telephone  = mutableStateOf("")
    val email  = mutableStateOf("")
    val avater  = mutableStateOf("")
    val birthday  = mutableStateOf("")
    val sex  = mutableStateOf("")
    val role  = mutableStateOf("")
    val sign  = mutableStateOf("")
    val password  = mutableStateOf("123456")
    val region  = mutableStateOf("")

    fun setUserEntity(userEntity: UserEntity){
        userId.value = userEntity.userId;
        createDate.value = userEntity.createDate
        updateDate.value = userEntity.updateDate
        username.value = userEntity.username
        telephone.value = userEntity.telephone
        email.value = userEntity.email
        avater.value = userEntity.avater
        birthday.value = userEntity.birthday
        sex.value = userEntity.sex
        role.value = userEntity.role
        sign.value = userEntity.sign
        password.value = userEntity.password
        region.value = userEntity.region
    }
}