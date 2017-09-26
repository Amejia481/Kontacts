package com.arturomejiamarmol.contacts.contacts

import com.arturomejiamarmol.contacts.data.ContactsRepository
import com.arturomejiamarmol.contacts.data.models.Contact

class ContactsPresenter(private val view: ContactsContract.View, private val contactsRepository: ContactsRepository) : ContactsContract.UserActions {

    override fun loadContacts() {
        contactsRepository.getContacts {
            view.showContacts(it)
        }
    }

    override fun pressAddContact() {
        view.showAddContactScreen()
    }

    override fun onSearch(pattern: String) {
        if (pattern.trim().isNotEmpty()) {
            contactsRepository.findContact(pattern) {
                view.showContacts(it)
            }
        } else {
            loadContacts()
        }

    }

    override fun deleteContact(contact: Contact) {
        contactsRepository.removeContact(contact) {
            view.deleteContact(contact)
        }
    }

    override fun showEditContactScreen(contact: Contact) {
        view.showEditContactScreen(contact)
    }

}