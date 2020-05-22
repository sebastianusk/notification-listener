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
    val data: List<Pair<String, Any?>>
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
                Pair(it, extras[it])
            }.toList()
            return NotificationModel(action, packageName, title, text, data)
        }
    }

    fun toNotificationEntity(): NotificationEntity {
        val extra = data.map {
            val json = JSONObject()
            json.put("key", it.first)
            json.put("value", it.second)
        }.let {
            JSONArray(it)
        }
        return NotificationEntity(
            packageName,
            title,
            text,
            extra.toString(),
            action
        )
    }
}