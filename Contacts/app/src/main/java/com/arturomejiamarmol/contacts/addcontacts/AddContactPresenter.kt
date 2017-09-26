package com.arturomejiamarmol.contacts.addcontacts

import com.arturomejiamarmol.contacts.data.ContactsRepository
import com.arturomejiamarmol.contacts.data.models.Contact

class AddContactPresenter(private val view: AddContactContract.View,
                          private val contactRepository: ContactsRepository) : AddContactContract.UserActions {

    lateinit var contactToEdit: Contact

    override fun addContact() {
        if (!view.areEmptyFields()) {
            val contact = view.getNewContact()
            contactRepository.addContact(contact)
            view.showMessageContactAdded()
            view.closeScreen()
        } else {
            view.showErrorOnInvalidFields()
        }
    }

    override fun loadContactForUpdate(contact: Contact) {
        contactToEdit = contact
        view.setName(contact.firstName)
        view.setLastName(contact.lastName)
        view.setDateOfBirth(contact.birthDate)
        view.setAddresses(contact.addresses)
        view.setEmails(contact.emails)
        view.setPhones(contact.phones)
    }

    override fun updateContact() {
        val newContact = view.getNewContact()
        if (!view.areEmptyFields()) {
            contactRepository.updateContact(contactToEdit, newContact) {
                view.showMessageContactEdited()
                view.closeScreen()
            }
        } else {
            view.showErrorOnInvalidFields()
        }
    }

}
