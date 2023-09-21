package ru.aeyu.catapitestapp.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class SaveCatPicture(
    private val catUrl: String,
    fileName: String
) {
    private val fileToSave: File = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
        fileName
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    suspend fun saveFileOnAndroid29AndLater(
        contentResolver: ContentResolver
    ): Long {

        val newFileContentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileToSave.name)
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }
        val uri =
            contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, newFileContentValues)
        return if (uri != null) {
            withContext(Dispatchers.IO) {
                URL(catUrl).openStream()
            }.use { input ->
                contentResolver.openOutputStream(uri).use { output ->
                    input.copyTo(output!!, DEFAULT_BUFFER_SIZE)
                }
            }

        } else
            -1
    }

    suspend fun saveFileOnAndroidLess29(): Long {
        return withContext(Dispatchers.IO) {
            URL(catUrl).openStream()
        }.use { input ->
            FileOutputStream(fileToSave).use { output ->
                input.copyTo(output)
            }
        }
    }
}