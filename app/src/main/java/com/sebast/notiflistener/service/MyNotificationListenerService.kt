package com.sebast.notiflistener.service

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.provider.Settings
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.text.TextUtils
import com.sebast.notiflistener.MainApplication
import com.sebast.notiflistener.model.NotificationModel

@SuppressLint("OverrideAbstract")
class MyNotificationListenerService : NotificationListenerService() {

    companion object {
        const val TAG = "NotifServ"
        private const val ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners"
        fun checkIfNotificationServiceEnabled(context: Context): Boolean {
            val flat =
                Settings.Secure.getString(context.contentResolver,
                    ENABLED_NOTIFICATION_LISTENERS
                )
            val packageName = context.packageName
            if (!TextUtils.isEmpty(flat)) {
                val names = flat.split(":");
                names.forEach {
                    val cn = ComponentName.unflattenFromString(it)
                    if (cn != null) {
                        if (TextUtils.equals(packageName, cn.packageName)) {
                            return true
                        }
                    }
                }
            }
            return false
        }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
        if (sbn != null) {
            val model = NotificationModel.create(sbn, "post")
            MainApplication.publish(
                this,
                model
            )
        }

    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        if (sbn != null) {
            val model = NotificationModel.create(sbn, "remove")
            MainApplication.publish(
                this,
                model
            )
        }
    }
}