package com.movie.mymovie.entity

class EditEntity {
    var title: String? = null
    var field: String? = null
    var value: String? = null
    var require: Boolean? = null

    constructor() {}
    constructor(title: String?, field: String?, value: String?, require: Boolean?) {
        this.title = title
        this.field = field
        this.value = value
        this.require = require
    }

    fun setField(key: String?, value: String?) {
//         = value;
    }
}