package com.sebast.notiflistener.storage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationEntity(
    @ColumnInfo(name = "package_name") val packageName: String,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "text") val text: String?,
    @ColumnInfo(name = "extra") val extra: String?,
    @ColumnInfo(name = "action") val action: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}