package com.movie.mymovie.entity

class UserEntity {
    var userId: String? = null
    var createDate: String? = null
    var updateDate: String? = null
    var username: String? = null
    var telephone: String? = null
    var email: String? = null
    var avater: String? = null
    var birthday: String? = null
    var sex: String? = null
    var role: String? = null
    var sign: String? = null
    var password: String? = null
    var region: String? = null
    override fun toString(): String {
        return "UserEntity{" +
                "userId='" + userId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", username='" + username + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", avater='" + avater + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex='" + sex + '\'' +
                ", role='" + role + '\'' +
                ", sign='" + sign + '\'' +
                ", password='" + password + '\'' +
                ", region='" + region + '\'' +
                '}'
    }
}