package com.base444.android.taiwanlottoscanner.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
open class BaseLotto(open var date: String, open var term: String, open var isMAtch: Boolean) :Parcelable{

}