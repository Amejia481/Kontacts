package com.arturomejiamarmol.contacts.view.widgets

import android.content.Context
import android.util.AttributeSet
import com.arturomejiamarmol.contacts.data.models.Address

class MultipleAddressesView : MultipleView<Address> {

    constructor(context: Context) : super(context)

    constructor(context: Context,
                attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun createObject(item: String): Address {
        return Address(0, item)
    }

    override fun getItems(): List<Address> {
        return super.getItems()
    }

    override fun getTextValueFromItem(item: Address): String {
        return item.address
    }
}