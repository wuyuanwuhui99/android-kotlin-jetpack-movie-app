package com.movie.mymovie.utils

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences的一个工具类，调用setParam就能保存String, Integer, Boolean, Float, Long类型的参数
 * 同样调用getParam就能获取到保存在手机里面的数据
 * @author xiaanming
 */
object SharedPreferencesUtils {
    /**
     * 保存在手机里面的文件名
     */
    private const val FILE_NAME = "share_date"

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param key
     * @param `object`
     */
    fun setParam(context: Context, key: String?, `object`: Any) {
        val type = `object`.javaClass.simpleName
        val sp:SharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        val editor = sp.edit()
        if ("String" == type) {
            editor.putString(key, `object` as String)
        } else if ("Integer" == type) {
            editor.putInt(key, (`object` as Int))
        } else if ("Boolean" == type) {
            editor.putBoolean(key, (`object` as Boolean))
        } else if ("Float" == type) {
            editor.putFloat(key, (`object` as Float))
        } else if ("Long" == type) {
            editor.putLong(key, (`object` as Long))
        }
        editor.commit()
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param key
     * @param defaultObject
     * @return
     */
    fun getParam(context: Context, key: String?, defaultObject: Any): Any? {
        val type = defaultObject.javaClass.simpleName
        val sp:SharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        if ("String" == type) {
            return sp.getString(key, defaultObject as String)
        } else if ("Integer" == type) {
            return sp.getInt(key, (defaultObject as Int))
        } else if ("Boolean" == type) {
            return sp.getBoolean(key, (defaultObject as Boolean))
        } else if ("Float" == type) {
            return sp.getFloat(key, (defaultObject as Float))
        } else if ("Long" == type) {
            return sp.getLong(key, (defaultObject as Long))
        }
        return null
    }
}