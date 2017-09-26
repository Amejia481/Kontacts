package com.arturomejiamarmol.contacts

import android.app.Application
import com.arturomejiamarmol.contacts.data.logging.ReleaseTree
import timber.log.Timber

class ContactsApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {

        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        appComponent.inject(this)



    }
}