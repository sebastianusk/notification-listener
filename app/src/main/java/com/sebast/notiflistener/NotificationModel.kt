package com.sebast.notiflistener

import android.service.notification.StatusBarNotification
import com.sebast.notiflistener.storage.NotificationEntity
import org.json.JSONArray
import org.json.JSONObject

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
            return NotificationModel(action, packageName, title, text, data)
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