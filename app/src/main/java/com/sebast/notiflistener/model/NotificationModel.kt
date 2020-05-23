package com.sebast.notiflistener.model

import android.service.notification.StatusBarNotification
import com.sebast.notiflistener.storage.NotificationEntity
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileWriter

data class NotificationModel(
    val action: String,
    val packageName: String,
    val title: String?,
    val text: String?,
    val data: String?
) {
    companion object {
        private const val TITLE_KEY = "android.title"
        private const val TEXT_KEY = "android.text"
        fun create(sbn: StatusBarNotification, action: String): NotificationModel {
            val packageName = sbn.packageName
            val extras = sbn.notification.extras
            val title = extras.getString(TITLE_KEY)
            val text = extras.getString(TEXT_KEY)
            val data = extras.keySet().map {
                val json = JSONObject()
                json.put(it, extras[it])
            }.let { JSONArray(it).toString() }
            return NotificationModel(
                action,
                packageName,
                title,
                text,
                data
            )
        }

        fun createCSV(file: File, list: List<NotificationModel>) {
            val writer = FileWriter(file)

            val headers = listOf("Action", "Package Name", "Title", "Text", "Data")
            writer.append(headers.joinToString(","))
            writer.append("\n")

            list.forEach {
                writer.append("${it.action},${it.packageName},${it.title},${it.text},${it.data}\n")
            }

            writer.flush()
            writer.close()
        }

        fun fromNotificationEntity(entity: NotificationEntity): NotificationModel {
            return NotificationModel(
                entity.action,
                entity.packageName,
                entity.title,
                entity.text,
                entity.extra
            )
        }
    }

    fun toNotificationEntity(): NotificationEntity {
        return NotificationEntity(
            packageName,
            title,
            text,
            data,
            action
        )
    }
}