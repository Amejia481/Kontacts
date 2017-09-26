package com.arturomejiamarmol.contacts.contacts

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.TextView
import com.arturomejiamarmol.contacts.ContactsApp
import com.arturomejiamarmol.contacts.R
import com.arturomejiamarmol.contacts.addcontacts.AddContactActivity
import com.arturomejiamarmol.contacts.data.ContactsRepository
import com.arturomejiamarmol.contacts.data.models.Contact
import kotlinx.android.synthetic.main.activity_contacts.*
import timber.log.Timber
import javax.inject.Inject

class ContactsActivity : AppCompatActivity(), ContactsContract.View, OnActionsContact {

    @Inject
    lateinit var contactsRepository: ContactsRepository

    private lateinit var contactAdapter: ContactAdapter

    private lateinit var presenter: ContactsPresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyListMessage: TextView
    private lateinit var rvObserver: RVEmptyObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (applicationContext as ContactsApp).appComponent.inject(this)

        setContentView(R.layout.activity_contacts)
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL)

        emptyListMessage = findViewById(R.id.empty_list_message)
        presenter = ContactsPresenter(this, contactsRepository)

        recyclerView = findViewById(R.id.contacts)
        contactAdapter = ContactAdapter(listOf(), this)
        recyclerView.adapter = contactAdapter

        rvObserver = RVEmptyObserver(recyclerView, emptyListMessage)
        contactAdapter.registerAdapterDataObserver(rvObserver)

        fab.setOnClickListener {
            presenter.pressAddContact()
        }
    }

    override fun onRestart() {
        presenter.loadContacts()
        super.onRestart()
    }

    override fun onNewIntent(intent: Intent) {
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            presenter.onSearch(query)
            Timber.d(query)
        }
    }

    override fun delete(contact: Contact) {
        presenter.deleteContact(contact)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val menuItem = menu.findItem(R.id.search)
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setIconifiedByDefault(false)

        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                presenter.loadContacts()
                return true
            }
        })

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(componentName))

        return true
    }

    override fun showAddContactScreen() {
        val intent = AddContactActivity.getIntent(this)
        startActivity(intent)
    }

    override fun showContacts(contacts: List<Contact>) {
        contactAdapter.swap(contacts)

    }

    class ContactAdapter(private var contacts: List<Contact>, private val contactActionListener: OnActionsContact) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

        fun swap(newContacts: List<Contact>) {
            contacts = newContacts
            notifyDataSetChanged()

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_contact, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val contact = contacts[position]
            val firstLetter = contact.firstName[0].toString()
            holder.mTopic.text = "${contact.firstName} ${contact.lastName} "
            holder.mCount.text = firstLetter.toUpperCase()
            holder.mCount.setBackgroundColor(getRandomColor())
            holder.itemView.tag = contact

            holder.itemView.setOnLongClickListener {
                val items = arrayOf<CharSequence>("Delete")

                val builder = AlertDialog.Builder(holder.itemView.context)

                builder.setTitle("Select The Action")
                builder.setItems(items) { _, _ ->
                    contactActionListener.delete(contact)
                }
                builder.show()
                true
            }

            holder.mView.setOnClickListener {
                contactActionListener.selected(it.tag as Contact)
            }
        }

        override fun getItemCount(): Int {
            return contacts.size
        }

        inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
            val mTopic: TextView = mView.findViewById<TextView>(R.id.topic)
            val mCount: TextView = mView.findViewById<TextView>(R.id.count)

        }

    }

    inner class RVEmptyObserver(private val recyclerView: RecyclerView, private val emptyView: View?) : RecyclerView.AdapterDataObserver() {

        init {
            checkIfEmpty()
        }

        private fun checkIfEmpty() {
            if (emptyView != null && recyclerView.adapter != null) {
                val emptyViewVisible = recyclerView.adapter.itemCount == 0
                emptyView.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE
                recyclerView.visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
            }
        }

        override fun onChanged() {
            checkIfEmpty()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }
    }

    override fun deleteContact(contacts: Contact) {
        contactAdapter.notifyDataSetChanged()
    }

    override fun selected(contact: Contact) {
        presenter.showEditContactScreen(contact)
    }
    override fun showEditContactScreen(contacts: Contact) {
            val intent = AddContactActivity.getIntentForUpdateContact(this, contacts)
        startActivity(intent)
    }

    companion object {
        private var lastColor = 0
        fun getRandomColor(): Int {

            var returnColor = Color.BLACK

            val colors = arrayOf("#e84e40", "#ec407a", "#ab47bc", "#7e57c2", "#5c6bc0", "#738ffe", "#29b6f6", "#26c6da", "#26a69a", "#2baf2b", "#9ccc65", "#d4e157", "#ffee58", "#ffa726", "#ff7043")

            do {
                val index = (Math.random() * colors.size).toInt()
                returnColor = Color.parseColor(colors[index])

            } while (lastColor == returnColor)
            lastColor = returnColor

            return returnColor
        }

    }
}

interface OnActionsContact {
    fun delete(contact: Contact)
    fun selected(contact: Contact)
}
