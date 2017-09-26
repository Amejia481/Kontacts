package com.arturomejiamarmol.contacts.data.models

import android.os.Parcel
import android.os.Parcelable

data class Contact(var id: Int = 0, val firstName: String, val lastName: String,
                   val birthDate: String, val addresses: List<Address>,
                   val emails: List<Email>,
                   val phones: List<Phone>
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.createTypedArrayList(Address),
            parcel.createTypedArrayList(Email),
            parcel.createTypedArrayList(Phone)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(birthDate)
        parcel.writeTypedList(addresses)
        parcel.writeTypedList(emails)
        parcel.writeTypedList(phones)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }
}