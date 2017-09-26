package com.arturomejiamarmol.contacts.addcontacts

import com.arturomejiamarmol.contacts.data.models.Address
import com.arturomejiamarmol.contacts.data.models.Contact
import com.arturomejiamarmol.contacts.data.models.Email
import com.arturomejiamarmol.contacts.data.models.Phone

interface AddContactContract {
    interface View {
        fun areEmptyFields(): Boolean
        fun getNewContact(): Contact
        fun showErrorOnInvalidFields()
        fun showMessageContactAdded()
        fun showMessageContactEdited()
        fun closeScreen()
        fun setName(firstName: String)
        fun setLastName(lastName: String)
        fun setDateOfBirth(dateOfBirth: String)
        fun setAddresses(addresses: List<Address>)
        fun setEmails(emails: List<Email>)
        fun setPhones(phones: List<Phone>)

    }

    interface UserActions {
        fun addContact()
        fun updateContact()
        fun loadContactForUpdate(contact: Contact)

    }

}