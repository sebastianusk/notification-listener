package com.sebast.notiflistener.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sebast.notiflistener.NotificationModel
import com.sebast.notiflistener.R

class MainAdapter(private val notifications: List<NotificationModel>) :
    RecyclerView.Adapter<MainViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.main_list_view, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(notifications[position])
    }
}
