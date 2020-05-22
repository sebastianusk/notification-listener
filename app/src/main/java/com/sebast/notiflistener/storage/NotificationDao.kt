package com.sebast.notiflistener.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notifications")
    fun getAllNotification(): List<NotificationEntity>

    @Insert
    fun insertAll(vararg notificationEntities: NotificationEntity)

    @Query("DELETE FROM notifications")
    fun clearNotification()
}