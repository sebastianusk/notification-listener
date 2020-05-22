package com.sebast.notiflistener

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sebast.notiflistener.storage.AppDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import java.lang.Exception

class MainApplication : Application() {
    companion object {
        private fun asMainApplication(
            context: Context
        ): MainApplication {
            if (context.applicationContext is MainApplication) {
                return (context.applicationContext as MainApplication)
            } else {
                throw Exception("the application is not MainApplication")
            }
        }

        fun publish(context: Context, content: NotificationModel) {
            val application = asMainApplication(context)
            application.publish(content)
        }

        fun getDb(context: Context): AppDatabase {
            val application = asMainApplication(context)
            return application.db
        }
    }

    private val bus by lazy { PublishSubject.create<NotificationModel>() }
    private val db by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "notification-database").build()
    }

    override fun onCreate() {
        super.onCreate()
        bus
            .observeOn(Schedulers.io())
            .subscribe {
                db.notificationDao().insertAll(
                    it.toNotificationEntity()
                )
            }
    }

    private fun publish(content: NotificationModel) {
        bus.onNext(content)
    }
}
