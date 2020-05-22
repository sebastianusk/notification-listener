package com.sebast.notiflistener.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sebast.notiflistener.MyNotificationListenerService
import com.sebast.notiflistener.NotificationModel
import com.sebast.notiflistener.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val ACTION_NOTIFICATION_LISTENER_SETTINGS =
            "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_list.adapter = MainAdapter(
            listOf(
                NotificationModel(
                    "post",
                    "com.sebast.test",
                    "test title",
                    "test text",
                    listOf()
                )
            )
        )
        main_list.layoutManager = LinearLayoutManager(this)

    }

    override fun onResume() {
        super.onResume()
        val permissionEnabled =
            MyNotificationListenerService.checkIfNotificationServiceEnabled(
                this
            )

        if (permissionEnabled) {
            permission_button.visibility = View.GONE
            permission_text.visibility = View.GONE
        } else {
            permission_button.visibility = View.VISIBLE
            permission_button.setOnClickListener {
                startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
            }
        }
    }
}
