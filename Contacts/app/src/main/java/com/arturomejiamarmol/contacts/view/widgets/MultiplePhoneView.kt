package com.arturomejiamarmol.contacts.view.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Spinner
import com.arturomejiamarmol.contacts.R
import com.arturomejiamarmol.contacts.data.models.Phone

class MultiplePhoneView : MultipleView<Phone> {

    private lateinit var phoneTypes: Array<String>

    override fun getTextValueFromItem(item: Phone): String {
        return item.phone

    }

    constructor(context: Context) : super(context)

    constructor(context: Context,
                attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun inflateItemContainerView(): View? = View.inflate(context, R.layout.layout_new_phone, null)

    override fun setItems(items: List<Phone>) {
        items.forEach { phone ->
            val itemContainer = addNewItem()
            val editTextItem = getTextItem(itemContainer as View)
            val phoneType = itemContainer?.findViewById<Spinner>(R.id.phone_type)

            val selectedIndex = getIndexPhoneType(phone.type)

            phoneType.setSelection(selectedIndex)
            editTextItem?.setText(getTextValueFromItem(phone))
        }

    }
    override fun onFinishInflate() {
        super.onFinishInflate()
        phoneTypes = resources.getStringArray(R.array.phoneTypes)
    }

    private fun getIndexPhoneType(phoneType: String): Int {

        return phoneTypes.indexOf(phoneType)
    }
    override fun getItems(): List<Phone> {
        val items = mutableListOf<Phone>()
        forEachNotEmptyItem { item ->
            val parent = item.parent as View
            val phoneType = parent.findViewById<Spinner>(R.id.phone_type)
            items += Phone(0, item.text.toString(), phoneType.selectedItem.toString())
        }
        return items
    }

    override fun createObject(item: String): Phone {
        return Phone(0, "", "")
    }

}