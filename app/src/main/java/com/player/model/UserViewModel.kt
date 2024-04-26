package com.player.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.player.movie.entity.UserEntity

class UserViewModel : ViewModel() {
    val userId  = mutableStateOf("")
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
    val password  = mutableStateOf("")
    val region  = mutableStateOf("")

    @JvmName("setUserEntity1")
    fun setUserEntity(userEntity: UserEntity){
        userId.value = userEntity.userId.toString();
        createDate.value = userEntity.createDate.toString()
        updateDate.value = userEntity.updateDate.toString()
        username.value = userEntity.username.toString()
        telephone.value = userEntity.telephone.toString()
        email.value = userEntity.email.toString()
        avater.value = userEntity.avater.toString()
        birthday.value = userEntity.birthday.toString()
        sex.value = userEntity.sex.toString()
        role.value = userEntity.role.toString()
        sign.value = userEntity.sign.toString()
        password.value = userEntity.password.toString()
        region.value = userEntity.region.toString()
    }
}