package com.arturomejiamarmol.contacts

import com.arturomejiamarmol.contacts.addcontacts.AddContactActivity
import com.arturomejiamarmol.contacts.contacts.ContactsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(target: ContactsApp)
    fun inject(target: ContactsActivity)
    fun inject(target: AddContactActivity)

}