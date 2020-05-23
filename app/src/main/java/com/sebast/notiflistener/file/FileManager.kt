package com.sebast.notiflistener.file

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import com.sebast.notiflistener.R
import com.sebast.notiflistener.model.NotificationModel
import java.io.File

object FileManager {
    private const val TAG = "FileGenerator"

    fun shareNotifications(context: Context, list: List<NotificationModel>) {
        val filename = "notifications.csv"
        val file = File(context.externalCacheDir, filename)
        if (file.isFile) {
            Log.d(TAG, "file exists")
            file.delete()
        }

        File.createTempFile(filename, null, context.externalCacheDir)

        NotificationModel.createCSV(file, list)
        val uri = try {
            FileProvider.getUriForFile(
                context,
                "com.sebast.notiflistener.provider",
                file
            )
        } catch (e: IllegalArgumentException) {
            Log.e(
                TAG,
                "The selected file can't be shared: $file"
            )
            null
        }
        uri?.let {
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, it)
                type = "text/csv"
            }
            startActivity(
                context,
                Intent.createChooser(shareIntent, context.getText(R.string.send_to)),
                null
            )
        }

    }

}