package com.sebast.notiflistener.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sebast.notiflistener.model.NotificationModel
import com.sebast.notiflistener.R

class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.notif_title)
    private val text: TextView = view.findViewById(R.id.notif_text)
    private val packageName: TextView = view.findViewById(R.id.notif_package_name)

    fun bind(notificationModel: NotificationModel) {
        title.text = notificationModel.title
        text.text = notificationModel.text
        packageName.text = notificationModel.packageName
    }
}