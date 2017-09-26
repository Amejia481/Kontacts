package com.arturomejiamarmol.contacts

import com.arturomejiamarmol.contacts.data.ContactsRepository
import com.arturomejiamarmol.contacts.data.OnMemoryContactRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: ContactsApp) {

    @Provides
    @Singleton
    fun provideApp() = app

    @Provides
    @Singleton
    fun provideContext() = app

    @Provides
    @Singleton
    fun provideContactRepository(): ContactsRepository {
        return OnMemoryContactRepository()
    }
}