package com.arturomejiamarmol.contacts.view.widgets

import android.content.Context
import android.util.AttributeSet
import com.arturomejiamarmol.contacts.data.models.Email

class MultipleEmailsView : MultipleView<Email> {

    override fun getTextValueFromItem(item: Email): String {
        return item.email
    }

    constructor(context: Context) : super(context)

    constructor(context: Context,
                attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    override fun createObject(item: String): Email {
        return Email(0, item)
    }

    override fun getItems(): List<Email> {
        return super.getItems()
    }
}