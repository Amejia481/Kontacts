package com.arturomejiamarmol.contacts.data

import com.arturomejiamarmol.contacts.data.models.Contact

interface ContactsRepository {
    fun addContact(contact: Contact)
    fun getContacts(callback: (contacts: List<Contact>) -> Unit)
    fun findContact(pattern: String, callback: (contacts: List<Contact>) -> Unit)
    fun removeContact(contact: Contact, callback: (itemDeleted: Boolean) -> Unit)
    fun updateContact(oldContact: Contact, newContact: Contact, callback: (itemUpdated: Boolean) -> Unit)
}