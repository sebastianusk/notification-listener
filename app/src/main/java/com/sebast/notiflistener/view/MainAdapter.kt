package com.sebast.notiflistener.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sebast.notiflistener.model.NotificationModel
import com.sebast.notiflistener.R

class MainAdapter() :
    RecyclerView.Adapter<MainViewHolder>() {
    var notifications: List<NotificationModel>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.main_list_view, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notifications?.size ?: 0
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        notifications?.get(position)?.let { holder.bind(it) }
    }
}
