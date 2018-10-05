package com.example.marlon.theguardiannews

import android.os.Parcel
import android.os.Parcelable

data class New(
        var sectionName: String,
        var headline: String,
        var url: String,
        var thumbnail: String,
        var bodyText: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(sectionName)
        parcel.writeString(headline)
        parcel.writeString(url)
        parcel.writeString(thumbnail)
        parcel.writeString(bodyText)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<New> {
        override fun createFromParcel(parcel: Parcel): New {
            return New(parcel)
        }

        override fun newArray(size: Int): Array<New?> {
            return arrayOfNulls(size)
        }
    }


}