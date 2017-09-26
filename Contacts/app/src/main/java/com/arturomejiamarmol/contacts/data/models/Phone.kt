package com.arturomejiamarmol.contacts.data.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Arturo Mejia on 9/13/17.
 */
data class Phone(var id: Int, var phone: String, var type: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(phone)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Phone> {
        override fun createFromParcel(parcel: Parcel): Phone {
            return Phone(parcel)
        }

        override fun newArray(size: Int): Array<Phone?> {
            return arrayOfNulls(size)
        }
    }
}