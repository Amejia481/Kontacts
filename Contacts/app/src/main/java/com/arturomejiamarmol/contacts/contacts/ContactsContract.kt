package com.arturomejiamarmol.contacts.contacts

import com.arturomejiamarmol.contacts.data.models.Contact

interface ContactsContract {

    interface View {
        fun showAddContactScreen()
        fun showContacts(contacts: List<Contact>)
        fun deleteContact(contacts: Contact)
        fun showEditContactScreen(contact: Contact)
    }
    interface UserActions {
        fun loadContacts()
        fun pressAddContact()
        fun onSearch(pattern: String)
        fun deleteContact(contact: Contact)
        fun showEditContactScreen(contacts: Contact)

    }
}