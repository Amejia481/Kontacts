package com.arturomejiamarmol.contacts.addcontacts

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import com.arturomejiamarmol.contacts.R


class AddressesView: CardView {

    constructor (context: Context?): super(context){
        initializeViews(context)
    }

    constructor(context: Context?, attrs: AttributeSet?
    ) : super(context,attrs) {
        initializeViews(context)
    }
    constructor(context: Context?, attrs: AttributeSet?,
                 defStyle:Int) : super(context,attrs,defStyle) {
        initializeViews(context)
    }
    private fun initializeViews(context: Context?){
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_addresses, this, true);
    }


}