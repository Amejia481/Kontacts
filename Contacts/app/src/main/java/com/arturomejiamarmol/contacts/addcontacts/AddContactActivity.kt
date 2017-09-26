package com.arturomejiamarmol.contacts.addcontacts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.arturomejiamarmol.contacts.ContactsApp
import com.arturomejiamarmol.contacts.R
import com.arturomejiamarmol.contacts.data.ContactsRepository
import com.arturomejiamarmol.contacts.data.models.Address
import com.arturomejiamarmol.contacts.data.models.Contact
import com.arturomejiamarmol.contacts.data.models.Email
import com.arturomejiamarmol.contacts.data.models.Phone
import com.arturomejiamarmol.contacts.view.widgets.MultipleAddressesView
import com.arturomejiamarmol.contacts.view.widgets.MultipleEmailsView
import com.arturomejiamarmol.contacts.view.widgets.MultiplePhoneView
import getStringValue
import isEmptyWithTrim
import showToast
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class AddContactActivity : AppCompatActivity(), AddContactContract.View {

    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var dateOfBirth: EditText
    private lateinit var addresses: MultipleAddressesView
    private lateinit var phones: MultiplePhoneView
    private lateinit var emails: MultipleEmailsView
    private lateinit var buttonAddOrEdit: Button

    @Inject
    lateinit var contactsRepository: ContactsRepository
    private lateinit var presenter: AddContactPresenter
    private var personalInfoFields = mutableListOf<EditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        (applicationContext as ContactsApp).appComponent.inject(this)
        presenter = AddContactPresenter(this, contactsRepository)

        initFields()

        intent.let {
            title = when (it.action) {
                ACTION_ADD_NEW_CONTACT -> {
                    val value = getString(R.string.add_contact)

                    buttonAddOrEdit.setOnClickListener {
                        presenter.addContact()
                        Timber.v("Add Contact button Pressed")
                    }

                    value
                }

                ACTION_UPDATE_CONTACT -> {
                    val contactToEdit = it.getParcelableExtra<Contact>(KEY_CONTACT)
                    presenter.loadContactForUpdate(contactToEdit)
                    buttonAddOrEdit.text = getString(R.string.edit_contact)

                    buttonAddOrEdit.setOnClickListener {
                        presenter.updateContact()
                        Timber.v("Update Contact button Pressed")
                    }

                    getString(R.string.edit_contact)
                }

                else -> {
                    TODO("")
                }
            }

        }

    }

    private fun isValidFormat(format: String, value: String): Boolean {
        var date: Date? = null
        try {
            val sdf = SimpleDateFormat(format)
            date = sdf.parse(value)
            if (value != sdf.format(date)) {
                date = null
            }
        } catch (ex: ParseException) {
            return false
        }

        return date != null
    }

    override fun setAddresses(addresses: List<Address>) {
        this.addresses.setItems(addresses)
    }

    override fun setEmails(emails: List<Email>) {
        this.emails.setItems(emails)
    }

    override fun setPhones(phones: List<Phone>) {
        this.phones.setItems(phones)
    }

    override fun setName(firstName: String) {
        this.firstName.setText(firstName)
    }

    override fun setLastName(lastName: String) {
        this.lastName.setText(lastName)
    }

    override fun setDateOfBirth(dateOfBirth: String) {
        this.dateOfBirth.setText(dateOfBirth)
    }

    private fun initFields() {
        firstName = findViewById(R.id.first_name)
        lastName = findViewById(R.id.last_name)
        dateOfBirth = findViewById(R.id.date_of_birth)
        addresses = findViewById(R.id.addresses)
        phones = findViewById(R.id.phones)
        emails = findViewById(R.id.emails)
        buttonAddOrEdit = findViewById(R.id.add_new_contact)
        personalInfoFields.add(firstName)
        personalInfoFields.add(lastName)
        personalInfoFields.add(dateOfBirth)
    }

    private fun areAnyEmptyFieldInPersonalInfoSection(): Boolean {
        return personalInfoFields.any { it.isEmptyWithTrim() }
    }

    override fun areEmptyFields(): Boolean {
        Timber.d("PersonalInfo ${areAnyEmptyFieldInPersonalInfoSection()}," +
                "addresses ${addresses.areAnyEmptyFields()} ," +
                "emails ${emails.areAnyEmptyFields()}," +
                " phones ${phones.areAnyEmptyFields()}")

        return areAnyEmptyFieldInPersonalInfoSection() ||
                addresses.areAnyEmptyFields() ||
                emails.areAnyEmptyFields() ||
                phones.areAnyEmptyFields() ||
                !isValidFormat("dd/MM/yyyy", dateOfBirth.text.toString())

    }

    override fun showErrorOnInvalidFields() {
        showErrorOnInvalidFieldsOnPersonalInfoSection()
        addresses.showErrorOnInvalidFields()
        emails.showErrorOnInvalidFields()
        phones.showErrorOnInvalidFields()

        Timber.d("showErrorOnInvalidFields")
    }

    private fun showErrorOnInvalidFieldsOnPersonalInfoSection() {
        personalInfoFields.filter {
            it.isEmptyWithTrim()
        }.forEach {
            it.error = getString(R.string.not_allow)
        }
        if (!isValidFormat("dd/MM/yyyy", dateOfBirth.text.toString())) {
            dateOfBirth.error = getString(R.string.message_invalid_format)

        }
    }

    override fun getNewContact(): Contact {
        val newContact = Contact(0, firstName.getStringValue(),
                lastName.getStringValue(),
                dateOfBirth.getStringValue(),
                addresses.getItems(), emails.getItems(), phones.getItems()
        )
        Timber.d("New contact Add $newContact")
        return newContact

    }

    override fun showMessageContactAdded() {
        this.showToast(getString(R.string.message_contact_added))
    }

    override fun showMessageContactEdited() {
        this.showToast(getString(R.string.message_contact_edited))
    }

    override fun closeScreen() {
        finish()
    }

    companion object {
        private const val ACTION_ADD_NEW_CONTACT = "ACTION_ADD_NEW_CONTACT"
        private const val ACTION_UPDATE_CONTACT = "ACTION_UPDATE_CONTACT"
        private const val KEY_CONTACT = "KEY_CONTACT"

        fun getIntent(context: Context): Intent {
            return Intent(context, AddContactActivity::class.java).apply {
                action = ACTION_ADD_NEW_CONTACT
            }
        }

        fun getIntentForUpdateContact(context: Context, contact: Contact): Intent {
            return Intent(context, AddContactActivity::class.java).apply {
                action = ACTION_UPDATE_CONTACT
                putExtra(KEY_CONTACT, contact)
            }
        }
    }
}
