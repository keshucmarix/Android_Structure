package com.app.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("id")
    var id: String? = null,

    @SerializedName("avatar")
    var avatar: String? = null,

    @SerializedName("email")
    var email: String? = null
) : Parcelable