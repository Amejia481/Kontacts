package com.arturomejiamarmol.contacts.view.widgets

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import children
import com.arturomejiamarmol.contacts.R
import isEmptyWithTrim
import minusAssign
import plusAssign

abstract class MultipleView<T> : CardView {

    protected lateinit var itemsContainer: LinearLayout
    private var headingTitle = "Title"
    private var itemHint = "Item"

    constructor(context: Context) : super(context) {
        initializeViews(context)

    }

    constructor(context: Context,
                attrs: AttributeSet) : super(context, attrs) {
        initAttrs(attrs)
        initializeViews(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initAttrs(attrs)
        initializeViews(context)
    }

    private fun initAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.mutiple_items)
        headingTitle = typedArray.getString(R.styleable.mutiple_items_heading_title)
        itemHint = typedArray.getString(R.styleable.mutiple_items_item_hint)
        typedArray.recycle()
    }

    private fun initializeViews(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.widget_multiple, this, true)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        itemsContainer = findViewById(R.id.new_items_container)
        val titleView = findViewById<TextView>(R.id.label_title_multiple)
        titleView.text = headingTitle

        findViewById<View>(R.id.add_more).setOnClickListener {
            addNewItem()
        }
    }

    abstract fun createObject(item: String): T
    abstract fun getTextValueFromItem(item: T): String

    open fun setItems(items: List<T>) {
        items.forEach {
            val itemContainer = addNewItem()
            val editTextItem = getTextItem(itemContainer as View)
            editTextItem?.setText(getTextValueFromItem(it))
        }

    }

    protected fun addNewItem(): View? {
        val newItemView = inflateItemContainerView()
        if (newItemView != null) {
            getTextItem(newItemView)?.hint = itemHint
            newItemView?.findViewById<View>(R.id.delete_multiple_item).setOnClickListener {
                itemsContainer -= (it.parent as LinearLayout)
            }
            itemsContainer += newItemView
        }
        return newItemView
    }

    open fun getItems(): List<T> {
        val items = mutableListOf<T>()

        forEachNotEmptyItem {
            items += createObject(it.text.toString())
        }

        return items
    }

    fun areAnyEmptyFields(): Boolean {
        var areFieldsNotEmpty = false

        forEachEmptyItem {
            areFieldsNotEmpty = true
        }

        return areFieldsNotEmpty
    }

    fun showErrorOnInvalidFields() {
        forEachEmptyItem {
            it.error = context.getString(R.string.not_allow)
        }
    }

    protected inline fun forEachItem(function: (editText: EditText) -> Unit) {
        itemsContainer.children().forEach {
            val editText = getTextItem(it)
            if (editText != null) {
                function(editText)
            }
        }
    }

    protected inline fun forEachNotEmptyItem(function: (editText: EditText) -> Unit) {
        forEachItem {
            if (!it.isEmptyWithTrim()) {
                function(it)

            }
        }
    }

    protected inline fun forEachEmptyItem(function: (editText: EditText) -> Unit) {
        forEachItem {
            if (it.isEmptyWithTrim()) {
                function(it)

            }
        }
    }

    protected fun getTextItem(view: View): EditText? =
            view.findViewById(R.id.multiple_text_item)

    open fun inflateItemContainerView(): View? = View.inflate(context, R.layout.layout_multple_item, null)
}