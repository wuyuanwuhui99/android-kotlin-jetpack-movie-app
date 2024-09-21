package com.player.utils

import android.text.TextUtils
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Locale

object MD5 {
    /**
     * 获取字符串对应的MD5
     * @param str
     * @return
     */
    fun getStrMD5(str: String): String {
        var ret = ""
        if (!TextUtils.isEmpty(str)) {
            try {
                val md5 = MessageDigest.getInstance("MD5")
                val charArray = str.toCharArray()
                val byteArray = ByteArray(charArray.size)
                for (i in charArray.indices) {
                    byteArray[i] = charArray[i].code.toByte()
                }
                val md5Bytes = md5.digest(byteArray)
                val hexValue = StringBuilder()
                for (i in md5Bytes.indices) {
                    val `val` = md5Bytes[i].toInt() and 0xff
                    if (`val` < 16) {
                        hexValue.append("0")
                    }
                    hexValue.append(Integer.toHexString(`val`))
                }
                ret = hexValue.toString().uppercase(Locale.getDefault())
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
        }
        return ret
    }
}