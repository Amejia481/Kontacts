package com.arturomejiamarmol.contacts.data

import com.arturomejiamarmol.contacts.data.models.Contact

class OnMemoryContactRepository : ContactsRepository {

    private val contacts = mutableListOf<Contact>()
    private var lastContactId = 1

    override fun addContact(contact: Contact) {
        ++lastContactId
        contact.id = lastContactId
        contacts += contact
    }

    override fun getContacts(callback: (contacts: List<Contact>) -> Unit) {
        callback(contacts)
    }

    override fun findContact(pattern: String, callback: (contacts: List<Contact>) -> Unit) {
        val findContacts = contacts.filter {
            it.firstName.contains(pattern, ignoreCase = true)
        }
        callback(findContacts)
    }

    override fun removeContact(contact: Contact, callback: (itemDeleted: Boolean) -> Unit) {
        callback(contacts.remove(contact))

    }

    override fun updateContact(oldContact: Contact, newContact: Contact, callback: (itemUpdated: Boolean) -> Unit) {
        removeContact(oldContact) {
            addContact(newContact)
            callback(true)
        }
    }

}