package com.kyant.pixelmusic.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.*

class DataStore(
    override val context: Context,
    override val name: String
) : BaseDataStore(context, context.noBackupFilesDir.path, name) {
    init {
        file.mkdirs()
    }
}

class CacheDataStore(
    override val context: Context,
    override val name: String
) : BaseDataStore(context, context.cacheDir.path, name) {
    init {
        file.mkdirs()
    }
}

open class BaseDataStore(
    open val context: Context,
    val path: String,
    open val name: String
) {
    inline val file
        get() = File(listOf(path, name).joinToString(File.separator))
    inline val String.coordinated: String
        get() = replace(File.separator.toRegex(), "|")
    inline val String.path: String
        get() = listOf(this@BaseDataStore.path, name, this).joinToString(File.separator)

    inline fun <reified T> String.stream(): T {
        return ObjectInputStream(FileInputStream(File(this.path))).readObject() as T
    }

    fun <T> write(key: String, content: T) {
        try {
            val file = File(key.coordinated.path).apply {
                delete()
                createNewFile()
            }
            ObjectOutputStream(FileOutputStream(file)).apply {
                writeObject(content)
                close()
            }
        } catch (e: IOException) {
            error(e)
        }
    }

    fun <T> writeWhileNotExist(key: String, content: T) {
        if (!contains(key)) {
            write(key, content)
        }
    }

    fun writeBitmap(key: String, bitmap: Bitmap) {
        try {
            FileOutputStream(File(key.coordinated.path)).apply {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
                flush()
                close()
            }
        } catch (e: IOException) {
            error(e)
        }
    }

    inline fun <reified T> getOrNull(key: String): T? {
        return try {
            key.coordinated.stream()
        } catch (e: IOException) {
            null
        } catch (e: ClassNotFoundException) {
            null
        }
    }

    fun getBitmapOrNull(key: String): Bitmap? {
        return if (contains(key)) BitmapFactory.decodeFile(key.coordinated.path) ?: null else null
    }

    fun requirePath(key: String): String = key.coordinated.path

    fun contains(key: String): Boolean = File(requirePath(key)).exists()

    fun clear(key: String? = null) {
        if (key == null) {
            file.deleteOnExit()
        } else {
            File(key.coordinated.path).delete()
        }
    }
}