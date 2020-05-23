package com.sebast.notiflistener.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sebast.notiflistener.MainApplication
import com.sebast.notiflistener.service.MyNotificationListenerService
import com.sebast.notiflistener.model.NotificationModel
import com.sebast.notiflistener.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val ACTION_NOTIFICATION_LISTENER_SETTINGS =
            "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
    }

    private val adapter by lazy { MainAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layoutManager = LinearLayoutManager(this)
        val divider = DividerItemDecoration(this, layoutManager.orientation)
        main_list.adapter = adapter
        main_list.layoutManager = layoutManager
        main_list.setHasFixedSize(true)
        main_list.addItemDecoration(divider)
    }

    override fun onResume() {
        super.onResume()
        val permissionEnabled =
            MyNotificationListenerService.checkIfNotificationServiceEnabled(
                this
            )

        if (permissionEnabled) {
            main_list.visibility = View.VISIBLE
            refresh_button.visibility = View.VISIBLE
            permission_button.visibility = View.GONE
            permission_text.visibility = View.GONE
            fetchData()
            refresh_button.setOnClickListener { fetchData() }
            clear_button.setOnClickListener { clearData() }
        } else {
            main_list.visibility = View.GONE
            refresh_button.visibility = View.GONE
            permission_text.visibility = View.VISIBLE
            permission_button.visibility = View.VISIBLE
            permission_button.setOnClickListener {
                startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
            }
        }
    }

    private fun clearData() {
        val db = MainApplication.getDb(this)
        Observable.just(db)
            .map {
                db.notificationDao().clearNotification()
                db
            }
            .map { db.notificationDao().getAllNotification() }
            .map { list ->
                list.map { NotificationModel.fromNotificationEntity(it) }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapter.notifications = it
                adapter.notifyDataSetChanged()
            }
    }

    private fun fetchData() {
        val db = MainApplication.getDb(this)
        Observable.just(db)
            .map { db.notificationDao().getAllNotification() }
            .map { list ->
                list.map { NotificationModel.fromNotificationEntity(it) }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                adapter.notifications = it
                adapter.notifyDataSetChanged()
            }

    }
}
