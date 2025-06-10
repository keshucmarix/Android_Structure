package com.app.data.request

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("password")
    var password: String? = null,
    @SerializedName("first_name")
    var firstName: String? = null,
    @SerializedName("last_name")
    var lastName: String? = null,
    @SerializedName("mobile_number")
    var mobileNumber: String? = null,
    @SerializedName("confirm_password")
    var confirmPassword: String? = null,
    @SerializedName("platform")
    var platform: String? = null,
)
