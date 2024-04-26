package com.player.movie.entity

class UserEntity {
    var userId: String = ""
    var createDate: String = ""
    var updateDate: String = ""
    var username: String = ""
    var telephone: String = ""
    var email: String = ""
    var avater: String = ""
    var birthday: String = ""
    var sex: String = ""
    var role: String = ""
    var sign: String = ""
    var password: String = ""
    var region: String = ""
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