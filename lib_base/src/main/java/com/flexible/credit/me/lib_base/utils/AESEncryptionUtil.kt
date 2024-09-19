package com.flexible.credit.me.lib_base.utils

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import java.nio.charset.Charset

object AESEncryptionUtil {

    const val AESKEY = "t7AEZXj6cW8kNNTK"  // 必须是16字节的密钥

    // AES/ECB/PKCS5Padding 加密
    fun encrypt(data: String, key: String = AESKEY): String {
        return try {
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            val secretKey = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "AES")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)

            val encryptedData = cipher.doFinal(data.toByteArray(Charsets.UTF_8))

            // 转换为 HEX 格式输出并大写
            encryptedData.toHex()
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    // AES/ECB/PKCS5Padding 解密
    fun decrypt(data: String, key: String = AESKEY): String {
        return try {
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            val secretKey = SecretKeySpec(key.toByteArray(Charsets.UTF_8), "AES")
            cipher.init(Cipher.DECRYPT_MODE, secretKey)

            val decodedData = data.hexToByteArray()
            val decryptedData = cipher.doFinal(decodedData)

            String(decryptedData, Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    // 将字节数组转换为 HEX 字符串并返回大写
    private fun ByteArray.toHex(): String {
        return joinToString("") { "%02X".format(it) }  // 大写 HEX
    }

    // 将 HEX 字符串转换为字节数组
    private fun String.hexToByteArray(): ByteArray {
        val len = this.length
        val result = ByteArray(len / 2)
        for (i in 0 until len step 2) {
            result[i / 2] = ((this[i].toString().toInt(16) shl 4) + this[i + 1].toString().toInt(16)).toByte()
        }
        return result
    }
}
